package com.grayzlp.ggithub.core.module.user;

import android.support.annotation.NonNull;

import com.google.common.base.Preconditions;
import com.grayzlp.ggithub.data.repo.people.PeopleRepository;
import com.grayzlp.ggithub.di.ActivityScoped;
import com.grayzlp.ggithub.util.LogUtils;
import com.grayzlp.ggithub.util.scheduler.BaseSchedulerProvider;
import com.grayzlp.ggithub.util.scheduler.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@ActivityScoped
public class UserPresenter implements UserContract.Presenter {

    private static final String TAG = LogUtils.makeLogTag(UserPresenter.class);

    private PeopleRepository peopleRepository;
    private UserContract.View userView;

    @NonNull
    private CompositeDisposable compositeDisposable;
    private final BaseSchedulerProvider scheduleProvider;

    @Inject
    public UserPresenter(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;

        scheduleProvider = SchedulerProvider.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void takeView(UserContract.View view) {
        userView = Preconditions.checkNotNull(view);
    }

    @Override
    public void dropView() {
        userView = null;
        compositeDisposable.dispose();
    }

    @Override
    public void loadUser(String username, boolean forceUpdate) {
        if (forceUpdate) {
            peopleRepository.refresh();
        }

        Disposable disposable = peopleRepository
                .getUser(username)
                .subscribeOn(scheduleProvider.io())
                .observeOn(scheduleProvider.ui())
                .subscribe(user -> userView.showUser(user),
                        error -> {
                            LogUtils.LOGW(TAG, "load error : ", error);
                            userView.showLoadError();
                        });
        compositeDisposable.add(disposable);
    }
}

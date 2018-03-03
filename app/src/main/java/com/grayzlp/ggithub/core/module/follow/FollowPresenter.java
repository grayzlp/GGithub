package com.grayzlp.ggithub.core.module.follow;


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
public class FollowPresenter implements FollowContract.Presenter {

    private static final String TAG =
            LogUtils.makeLogTag(FollowPresenter.class);

    private PeopleRepository peopleRepository;
    private FollowContract.View userView;

    @NonNull
    private CompositeDisposable compositeDisposable;
    private final BaseSchedulerProvider scheduleProvider;

    @Inject
    public FollowPresenter(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;

        scheduleProvider = SchedulerProvider.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void takeView(FollowContract.View view) {
        userView = Preconditions.checkNotNull(view);
    }

    @Override
    public void dropView() {
        userView = null;
        compositeDisposable.dispose();
    }

    @Override
    public void loadFollowingOfUser(String username, boolean forceUpdate) {
        if (forceUpdate) {
            peopleRepository.refresh();
        }

        Disposable disposable = peopleRepository
                .getUserFollowing(username)
                .subscribeOn(scheduleProvider.io())
                .observeOn(scheduleProvider.ui())
                .subscribe(users -> userView.showUsers(users),
                        error -> {
                            LogUtils.LOGW(TAG, "load error : ", error);
                            userView.showLoadError();
                        });
        compositeDisposable.add(disposable);
    }

    @Override
    public void loadFollowerOfUser(String username, boolean forceUpdate) {
        if (forceUpdate) {
            peopleRepository.refresh();
        }

        Disposable disposable = peopleRepository
                .getUserFollowers(username)
                .subscribeOn(scheduleProvider.io())
                .observeOn(scheduleProvider.ui())
                .subscribe(users -> userView.showUsers(users),
                        error -> {
                            LogUtils.LOGW(TAG, "load error : ", error);
                            userView.showLoadError();
                        });
        compositeDisposable.add(disposable);

    }
}


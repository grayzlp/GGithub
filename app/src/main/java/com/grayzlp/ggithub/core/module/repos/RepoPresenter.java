package com.grayzlp.ggithub.core.module.repos;

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
public class RepoPresenter implements RepoContract.Presenter {

    private static final String TAG = LogUtils.makeLogTag(RepoPresenter.class);

    private PeopleRepository peopleRepository;
    private RepoContract.View repoView;

    @NonNull
    private CompositeDisposable compositeDisposable;
    private final BaseSchedulerProvider scheduleProvider;

    @Inject
    public RepoPresenter(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;

        scheduleProvider = SchedulerProvider.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void takeView(RepoContract.View view) {
        repoView = Preconditions.checkNotNull(view);
    }

    @Override
    public void dropView() {
        repoView = null;
        compositeDisposable.dispose();
    }

    @Override
    public void loadUserRepos(String username, boolean forceUpdate) {
        if (forceUpdate) {
            peopleRepository.refresh();
        }

        Disposable disposable = peopleRepository
                .getRepositories(username)
                .subscribeOn(scheduleProvider.io())
                .observeOn(scheduleProvider.ui())
                .subscribe(repos -> repoView.showRepos(repos),
                        error -> {
                            LogUtils.LOGW(TAG, "load error : ", error);
                            repoView.showLoadError();
                        });
        compositeDisposable.add(disposable);
    }
}

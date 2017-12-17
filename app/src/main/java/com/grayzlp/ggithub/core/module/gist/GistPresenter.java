package com.grayzlp.ggithub.core.module.gist;

import android.util.Log;

import com.google.common.base.Preconditions;
import com.grayzlp.ggithub.data.repo.gist.GistsRepository;
import com.grayzlp.ggithub.di.ActivityScoped;
import com.grayzlp.ggithub.util.scheduler.SchedulerProvider;

import java.util.Arrays;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@ActivityScoped
public class GistPresenter implements GistContract.Presenter {

    private GistsRepository mRepo;

    private CompositeDisposable mCompositeDisposable;
    private SchedulerProvider mSchedulerProvider;

    private GistContract.View mView;

    private boolean mFirstLoad = true;

    @Inject
    public GistPresenter(GistsRepository repository) {
        mRepo = repository;
        mCompositeDisposable = new CompositeDisposable();
        mSchedulerProvider = SchedulerProvider.getInstance();
    }

    @Override
    public void takeView(GistContract.View view) {
        mView = Preconditions.checkNotNull(view);
        loadGists(false);
    }

    @Override
    public void dropView() {
        mView = null;
        mCompositeDisposable.clear();
    }

    @Override
    public void loadGists(boolean forceUpdate) {
        mView.showLoadingIndicator(true);
        if (mFirstLoad || forceUpdate) {
            mFirstLoad = false;
            mRepo.refresh();
        }
        mCompositeDisposable.clear();
        Disposable disposable = mRepo
                .getGists()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(
                        gists -> {
                            mView.showLoadingIndicator(false);
                            if (gists.isEmpty()) {
                                mView.showNoData();
                            } else {
                                mView.showGists(gists);
                            }
                        },
                        error -> {
                            mView.showLoadingIndicator(false);
                            mView.showLoadingError();
                        }
                );
        mCompositeDisposable.add(disposable);
    }
}

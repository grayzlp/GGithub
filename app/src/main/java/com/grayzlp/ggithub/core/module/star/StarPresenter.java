package com.grayzlp.ggithub.core.module.star;


import com.google.common.base.Preconditions;
import com.grayzlp.ggithub.data.model.repo.Starred;
import com.grayzlp.ggithub.data.repo.star.StarsRepository;
import com.grayzlp.ggithub.di.ActivityScoped;
import com.grayzlp.ggithub.util.scheduler.BaseSchedulerProvider;
import com.grayzlp.ggithub.util.scheduler.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@ActivityScoped
public class StarPresenter implements StarContract.Presenter {

    private boolean mFirstLoad;

    private StarContract.View mStarView;
    private final StarsRepository mRepository;

    private CompositeDisposable mCompositeDisposable;

    private final BaseSchedulerProvider mSchedulerProvider;

    @Inject
    public StarPresenter(StarsRepository starsRepository) {
        mRepository = starsRepository;

        mSchedulerProvider = SchedulerProvider.getInstance();
        mCompositeDisposable = new CompositeDisposable();
    }



    @Override
    public void takeView(StarContract.View view) {
        mStarView = Preconditions.checkNotNull(view);
        loadStars(true);
    }

    @Override
    public void dropView() {
        mStarView = null;
        mCompositeDisposable.clear();
    }

    @Override
    public void loadStars(boolean forceUpdate) {
        mStarView.showLoadingIndicator(true);
        if (forceUpdate || mFirstLoad) {
            mRepository.refresh();
            mFirstLoad = true;
        }

        mCompositeDisposable.clear();
        Disposable disposable = mRepository
                .getStarreds()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(
                        starreds -> {
                            mStarView.showStar(starreds);
                            mStarView.showLoadingIndicator(false);
                        },
                        error -> {
                            mStarView.showLoadingError();
                            mStarView.showLoadingIndicator(false);
                        }
                );
        mCompositeDisposable.add(disposable);

    }

    @Override
    public void unstar(Starred starred) {

    }

    @Override
    public void openRepo(Starred starred) {

    }
}

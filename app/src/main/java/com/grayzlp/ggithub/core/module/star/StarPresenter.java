package com.grayzlp.ggithub.core.module.star;


import com.grayzlp.ggithub.data.model.repo.Starred;
import com.grayzlp.ggithub.data.repo.star.StarsRepository;
import com.grayzlp.ggithub.di.ActivityScoped;
import com.grayzlp.ggithub.util.scheduler.BaseSchedulerProvider;
import com.grayzlp.ggithub.util.scheduler.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

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

    }

    @Override
    public void dropView() {

    }

    @Override
    public void loadStars(boolean forceUpdate) {

    }

    @Override
    public void unstar(Starred starred) {

    }

    @Override
    public void openRepo(Starred starred) {

    }
}

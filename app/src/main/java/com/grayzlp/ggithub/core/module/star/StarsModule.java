package com.grayzlp.ggithub.core.module.star;

import com.grayzlp.ggithub.di.ActivityScoped;
import com.grayzlp.ggithub.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class StarsModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract StarsFragment starsFragment();

    @ActivityScoped
    @Binds
    abstract StarContract.Presenter starPresenter(StarPresenter starPresenter);

}

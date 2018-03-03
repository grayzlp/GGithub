package com.grayzlp.ggithub.core.module.follow;

import com.grayzlp.ggithub.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class FollowModule {

    @ActivityScoped
    @Binds
    abstract FollowContract.Presenter followPresenter(FollowPresenter presenter);
}

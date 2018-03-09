package com.grayzlp.ggithub.core.module.repo;

import com.grayzlp.ggithub.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepoModule {

    @ActivityScoped
    @Binds
    abstract RepoContract.Presenter repoPresenter(RepoPresenter presenter);
}

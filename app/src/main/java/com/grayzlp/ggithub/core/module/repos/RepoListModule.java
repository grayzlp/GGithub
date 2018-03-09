package com.grayzlp.ggithub.core.module.repos;

import com.grayzlp.ggithub.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepoListModule {

    @ActivityScoped
    @Binds
    abstract RepoListContract.Presenter repoListPresenter(RepoListPresenter presenter);
}

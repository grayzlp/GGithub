package com.grayzlp.ggithub.core.module.gist;


import com.grayzlp.ggithub.core.module.people.PeopleContract;
import com.grayzlp.ggithub.core.module.people.PeoplePresenter;
import com.grayzlp.ggithub.di.ActivityScoped;
import com.grayzlp.ggithub.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class GistModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract GistFragment gistFragment();


    @ActivityScoped
    @Binds
    abstract GistContract.Presenter provideGistPresenter(GistPresenter presenter);

}

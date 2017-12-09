package com.grayzlp.ggithub.core.module.people;

import com.grayzlp.ggithub.di.ActivityScoped;
import com.grayzlp.ggithub.di.FragmentScoped;

import dagger.Binds;
import dagger.BindsInstance;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class PeopleModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract PeopleFragment peopleFragment();

    @ActivityScoped
    @Binds
    abstract PeopleContract.Presenter providePeoplePresenter(PeoplePresenter presenter);
}

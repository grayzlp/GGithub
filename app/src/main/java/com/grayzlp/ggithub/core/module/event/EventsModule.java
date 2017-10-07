package com.grayzlp.ggithub.core.module.event;

import com.grayzlp.ggithub.di.ActivityScoped;
import com.grayzlp.ggithub.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class EventsModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract EventsFragment eventsFragment();

    @ActivityScoped
    @Binds abstract EventContract.Presenter eventsPresenter(EventsPresenter presenter);

}

package com.grayzlp.ggithub.di;

import com.grayzlp.ggithub.core.activity.HomeActivity;
import com.grayzlp.ggithub.core.module.event.EventsModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = {EventsModule.class})
    abstract HomeActivity homeActivity();

}

package com.grayzlp.ggithub.di;

import com.grayzlp.ggithub.core.activity.HomeActivity;
import com.grayzlp.ggithub.core.module.event.EventModule;
import com.grayzlp.ggithub.core.module.star.StarModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = {
            EventModule.class,
            StarModule.class})
    abstract HomeActivity bindHomeActivity();

}

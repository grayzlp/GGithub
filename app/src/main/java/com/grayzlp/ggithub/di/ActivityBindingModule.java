package com.grayzlp.ggithub.di;

import com.grayzlp.ggithub.core.activity.HomeActivity;
import com.grayzlp.ggithub.core.module.event.EventsModule;
import com.grayzlp.ggithub.core.module.star.StarsModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = {
            EventsModule.class,
            StarsModule.class})
    abstract HomeActivity bindHomeActivity();

}

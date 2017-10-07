package com.grayzlp.ggithub;

import com.grayzlp.ggithub.data.repo.event.EventsRepository;
import com.grayzlp.ggithub.di.AppComponent;
import com.grayzlp.ggithub.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;


public class GGithubApplication extends DaggerApplication {

    @Inject
    EventsRepository eventsRepository;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this)
                .build();
        appComponent.inject(this);
        return appComponent;
    }

    public EventsRepository getEventsRepository() {
        return eventsRepository;
    }
}

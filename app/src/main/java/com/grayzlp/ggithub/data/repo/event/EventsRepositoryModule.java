package com.grayzlp.ggithub.data.repo.event;

import com.grayzlp.ggithub.data.Local;
import com.grayzlp.ggithub.data.Remote;
import com.grayzlp.ggithub.data.local.events.EventsLocalDataSource;
import com.grayzlp.ggithub.data.remote.event.EventsRemoteDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class EventsRepositoryModule {

    @Singleton
    @Binds
    @Local
    abstract EventsDataSource provideEventsLocalDataResource(
            EventsLocalDataSource dataSource);

    @Singleton
    @Binds
    @Remote
    abstract EventsDataSource provideEventsRemoteDataResource(
            EventsRemoteDataSource dataSource);
}

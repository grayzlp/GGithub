package com.grayzlp.ggithub.data.repo.people;

import com.grayzlp.ggithub.data.Remote;
import com.grayzlp.ggithub.data.remote.people.PeopleRemoteDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class PeopleRepositoryModule {

    @Singleton
    @Binds
    @Remote
    abstract PeopleDataSource providePeopleRemoteDataResource(
            PeopleRemoteDataSource dataSource);
}

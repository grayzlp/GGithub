package com.grayzlp.ggithub.data.repo.star;

import com.grayzlp.ggithub.data.Remote;
import com.grayzlp.ggithub.data.remote.star.StarsRemoteDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class StarsRepositoryModule {

    @Binds
    @Remote
    @Singleton
    abstract StarsDataSource provideRemoteStarsDataSource(StarsRemoteDataSource remote);

}

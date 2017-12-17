package com.grayzlp.ggithub.data.repo.gist;


import com.grayzlp.ggithub.data.Remote;
import com.grayzlp.ggithub.data.remote.gist.GistsRemoteDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class GistsRepositoryModule {

    @Singleton
    @Binds
    @Remote
    abstract GistsDataSource provideGistsRemoteDataSource(GistsRemoteDataSource remote);
}

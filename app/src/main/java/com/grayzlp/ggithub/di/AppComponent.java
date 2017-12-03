package com.grayzlp.ggithub.di;

import android.app.Application;

import com.grayzlp.ggithub.GGithubApplication;
import com.grayzlp.ggithub.data.remote.people.PeopleRemoteDataSource;
import com.grayzlp.ggithub.data.repo.event.EventsRepository;
import com.grayzlp.ggithub.data.repo.event.EventsRepositoryModule;
import com.grayzlp.ggithub.data.repo.people.PeopleDataSource;
import com.grayzlp.ggithub.data.repo.star.StarsRepositoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;


@Singleton
@Component(modules = {
        ApplicationModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class,
        EventsRepositoryModule.class,
        StarsRepositoryModule.class,
        PeopleRemoteDataSource.class})
public abstract class AppComponent implements AndroidInjector<DaggerApplication> {

    @Override
    public abstract void inject(DaggerApplication instance);

    @Component.Builder
    public interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}

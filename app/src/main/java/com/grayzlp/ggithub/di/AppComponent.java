package com.grayzlp.ggithub.di;

import android.app.Application;

import com.grayzlp.ggithub.GGithubApplication;
import com.grayzlp.ggithub.data.repo.event.EventsRepository;
import com.grayzlp.ggithub.data.repo.event.EventsRepositoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;


@Singleton
@Component(modules = {
        EventsRepositoryModule.class,
        ApplicationModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<DaggerApplication> {

    void inject(GGithubApplication application);

    EventsRepository getEventsRepository();

    @Override
    void inject(DaggerApplication instance);

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}

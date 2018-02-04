package com.grayzlp.ggithub.core.module.user;

import com.grayzlp.ggithub.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class UserModule {

    @ActivityScoped
    @Binds
    abstract UserContract.Presenter userPresenter(UserPresenter presenter);
}

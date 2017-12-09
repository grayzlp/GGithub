package com.grayzlp.ggithub.core.module.gist;


import com.grayzlp.ggithub.di.FragmentScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class GistModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract GistFragment gistFragment();

}

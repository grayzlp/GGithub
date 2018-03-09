package com.grayzlp.ggithub.di;

import com.grayzlp.ggithub.core.activity.FollowListActivity;
import com.grayzlp.ggithub.core.activity.HomeActivity;
import com.grayzlp.ggithub.core.activity.RepositoriesListActivity;
import com.grayzlp.ggithub.core.activity.RepositoryActivity;
import com.grayzlp.ggithub.core.activity.UserActivity;
import com.grayzlp.ggithub.core.module.event.EventModule;
import com.grayzlp.ggithub.core.module.follow.FollowModule;
import com.grayzlp.ggithub.core.module.gist.GistModule;
import com.grayzlp.ggithub.core.module.people.PeopleModule;
import com.grayzlp.ggithub.core.module.repo.RepoModule;
import com.grayzlp.ggithub.core.module.repos.RepoListModule;
import com.grayzlp.ggithub.core.module.star.StarModule;
import com.grayzlp.ggithub.core.module.user.UserModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = {
            EventModule.class,
            StarModule.class,
            PeopleModule.class,
            GistModule.class})
    abstract HomeActivity bindHomeActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {
            UserModule.class
    })
    abstract UserActivity bindUserActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {
            RepoListModule.class})
    abstract RepositoriesListActivity bindReposActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {
            FollowModule.class})
    abstract FollowListActivity bindFollowListActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {
            RepoModule.class
    })
    abstract RepositoryActivity bindRepositoryActivity();
}

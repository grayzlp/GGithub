package com.grayzlp.ggithub.data.remote.people;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;

import com.grayzlp.ggithub.data.model.repo.Repository;
import com.grayzlp.ggithub.data.model.user.SimpleUser;
import com.grayzlp.ggithub.data.model.user.User;
import com.grayzlp.ggithub.data.prefs.GithubPrefs;
import com.grayzlp.ggithub.data.repo.people.PeopleDataSource;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class PeopleRemoteDataSource implements PeopleDataSource {

    private GithubPrefs mPrefs;

    @Inject
    PeopleRemoteDataSource(@Nonnull Context context) {
        mPrefs = GithubPrefs.get(context);
    }

    @Override
    public Flowable<List<SimpleUser>> getFollowers() {
        return mPrefs.getApi().listCurrentUserFollowers();
    }

    @Override
    public Flowable<List<SimpleUser>> getFollowing() {
        return mPrefs.getApi().listCurrentUserFollowing();
    }

    @Override
    public Flowable<List<SimpleUser>> getUserFollowers(String userName) {
        return mPrefs.getApi().listUsersFollowers(userName);
    }

    @Override
    public Flowable<List<SimpleUser>> getUserFollowing(String userName) {
        return mPrefs.getApi().listUsersFollowing(userName);
    }

    @Override
    public Flowable<User> getUser(String userName) {
        return mPrefs.getApi().getUser(userName);
    }

    @Override
    public Flowable<List<Repository>> getRepositories(String userName) {
        return mPrefs.getApi().listUserRepos(userName);
    }

    @Override
    public Flowable<Repository> getRepository(String repo) {
        String owner = repo.split("/")[0];
        String repoName = repo.split("/")[1];
        return mPrefs.getApi().getRepository(owner, repoName);
    }

    @Override
    public void refresh() {
        // no-op
    }
}

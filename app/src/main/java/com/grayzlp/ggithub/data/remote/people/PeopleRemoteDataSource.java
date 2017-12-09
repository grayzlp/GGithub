package com.grayzlp.ggithub.data.remote.people;

import android.content.Context;

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
    public void refresh() {
        // no-op
    }
}

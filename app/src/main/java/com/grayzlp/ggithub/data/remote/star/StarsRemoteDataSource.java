package com.grayzlp.ggithub.data.remote.star;

import android.content.Context;

import com.grayzlp.ggithub.data.model.repo.Starred;
import com.grayzlp.ggithub.data.prefs.GithubPrefs;
import com.grayzlp.ggithub.data.repo.star.StarsDataSource;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class StarsRemoteDataSource implements StarsDataSource {

    private GithubPrefs mPrefs;

    @Inject
    public StarsRemoteDataSource(@Nonnull Context context) {
        mPrefs = GithubPrefs.get(context);
    }

    @Override
    public Flowable<List<Starred>> getStarreds() {
        return mPrefs.getApi().listCurrentUserStarred();
    }

    @Override
    public void starRepo(String owner, String repo) {
        mPrefs.getApi().starRepo(owner, repo);
    }

    @Override
    public void unstarRepo(String owner, String repo) {
        mPrefs.getApi().unstarRepo(owner, repo);
    }

    @Override
    public void refresh() {
        // no-op
    }
}

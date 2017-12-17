package com.grayzlp.ggithub.data.remote.gist;

import android.content.Context;

import com.grayzlp.ggithub.data.model.gist.Gist;
import com.grayzlp.ggithub.data.prefs.GithubPrefs;
import com.grayzlp.ggithub.data.repo.gist.GistsDataSource;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class GistsRemoteDataSource implements GistsDataSource {

    private GithubPrefs mPrefs;

    @Inject
    public GistsRemoteDataSource(@Nonnull Context context) {
        mPrefs = GithubPrefs.get(context);
    }

    @Override
    public Flowable<List<Gist>> getGists() {
        return mPrefs.getApi().listCurrentGists();
    }

    @Override
    public void refresh() {
        // no-op
    }
}

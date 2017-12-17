package com.grayzlp.ggithub.data.repo.gist;

import com.grayzlp.ggithub.data.Remote;
import com.grayzlp.ggithub.data.model.gist.Gist;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;


@Singleton
public class GistsRepository implements GistsDataSource {

    private GistsDataSource mRemote;

    private List<Gist> mCache;
    private boolean mCacheIsDirty;

    @Inject
    public GistsRepository(@Remote GistsDataSource remote) {
        mRemote = remote;
    }

    @Override
    public Flowable<List<Gist>> getGists() {
        if (mCache != null && !mCacheIsDirty) {
            return Flowable.just(mCache);
        }
        return mRemote.getGists()
                .doOnNext((gists) -> mCache = gists)
                .doOnComplete(() -> mCacheIsDirty = false);
    }

    @Override
    public void refresh() {
        mCacheIsDirty = true;
    }
}

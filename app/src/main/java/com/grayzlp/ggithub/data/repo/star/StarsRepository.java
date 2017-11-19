package com.grayzlp.ggithub.data.repo.star;


import com.grayzlp.ggithub.data.Remote;
import com.grayzlp.ggithub.data.model.repo.Starred;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class StarsRepository implements StarsDataSource {

    private StarsDataSource mStarsRemoteDataSource;

    private List<Starred> mCaches;

    private boolean mCacheIsDirty;

    @Inject
    public StarsRepository(@Remote StarsDataSource remoteDataSource) {
        mStarsRemoteDataSource = remoteDataSource;
    }

    @Override
    public Flowable<List<Starred>> getStarreds() {
        if (mCaches != null && !mCacheIsDirty) {
            return Flowable.just(mCaches);
        }
        return mStarsRemoteDataSource.getStarreds()
                .doOnNext(stars -> mCaches = stars)
                .doOnComplete(() -> mCacheIsDirty = false);

    }

    @Override
    public void starRepo(String owner, String repo) {
        mStarsRemoteDataSource.starRepo(owner, repo);
        mCacheIsDirty = true;
    }

    @Override
    public void unstarRepo(String owner, String repo) {
        mStarsRemoteDataSource.unstarRepo(owner, repo);
        mCacheIsDirty = true;
    }

    @Override
    public void refresh() {
        mCacheIsDirty = true;
    }
}

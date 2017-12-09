package com.grayzlp.ggithub.data.repo.people;


import com.grayzlp.ggithub.data.Remote;
import com.grayzlp.ggithub.data.model.user.SimpleUser;
import com.grayzlp.ggithub.data.model.user.User;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class PeopleRepository implements PeopleDataSource {

    private PeopleDataSource mRemoteDataSource;

    private boolean mFollowerCacheIsDirty;
    private boolean mFollowingCacheIsDirty;
    private List<SimpleUser> mFollowerCaches;
    private List<SimpleUser> mFollowingCaches;

    @Inject
    PeopleRepository(@Remote PeopleDataSource remote) {
        mRemoteDataSource = remote;
    }


    @Override
    public Flowable<List<SimpleUser>> getFollowers() {
        if (mFollowerCaches != null && !mFollowerCacheIsDirty) {
            return Flowable.just(mFollowerCaches);
        }

        return mRemoteDataSource.getFollowers()
                .doOnNext(followers -> mFollowerCaches = followers)
                .doOnComplete(() -> mFollowerCacheIsDirty = false);
    }

    @Override
    public Flowable<List<SimpleUser>> getFollowing() {
        if (mFollowingCaches != null && !mFollowingCacheIsDirty) {
            return Flowable.just(mFollowingCaches);
        }

        return mRemoteDataSource.getFollowing()
                .doOnNext(following -> mFollowingCaches = following)
                .doOnComplete(() -> mFollowingCacheIsDirty = false);
    }

    @Override
    public void refresh() {
        mFollowerCacheIsDirty = true;
        mFollowingCacheIsDirty = true;
    }
}

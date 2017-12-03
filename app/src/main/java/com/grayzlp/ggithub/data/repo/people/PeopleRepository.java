package com.grayzlp.ggithub.data.repo.people;


import com.grayzlp.ggithub.data.Remote;
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
    private List<User> mFollowerCaches;
    private List<User> mFollowingCaches;

    @Inject
    PeopleRepository(@Remote PeopleDataSource remote) {
        mRemoteDataSource = remote;
    }


    @Override
    public Flowable<List<User>> getFollowers() {
        if (mFollowerCaches != null && !mFollowerCacheIsDirty) {
            return Flowable.just(mFollowerCaches);
        }

        return mRemoteDataSource.getFollowers()
                .doOnNext(followers -> mFollowerCaches = followers)
                .doOnComplete(() -> mFollowerCacheIsDirty = false);
    }

    @Override
    public Flowable<List<User>> getFollowing() {
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

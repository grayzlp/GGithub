package com.grayzlp.ggithub.data.repo.people;


import android.text.TextUtils;

import com.grayzlp.ggithub.data.Remote;
import com.grayzlp.ggithub.data.model.user.SimpleUser;
import com.grayzlp.ggithub.data.model.user.User;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class PeopleRepository implements PeopleDataSource {

    private PeopleDataSource remoteDataSource;

    private boolean followerCacheIsDirty;
    private boolean followingCacheIsDirty;
    private List<SimpleUser> followerCaches;
    private List<SimpleUser> followingCaches;
    private User userCache;
    private String userNameCache;

    @Inject
    PeopleRepository(@Remote PeopleDataSource remote) {
        remoteDataSource = remote;
    }


    @Override
    public Flowable<List<SimpleUser>> getFollowers() {
        if (followerCaches != null && !followerCacheIsDirty) {
            return Flowable.just(followerCaches);
        }

        return remoteDataSource.getFollowers()
                .doOnNext(followers -> followerCaches = followers)
                .doOnComplete(() -> followerCacheIsDirty = false);
    }

    @Override
    public Flowable<List<SimpleUser>> getFollowing() {
        if (followingCaches != null && !followingCacheIsDirty) {
            return Flowable.just(followingCaches);
        }

        return remoteDataSource.getFollowing()
                .doOnNext(following -> followingCaches = following)
                .doOnComplete(() -> followingCacheIsDirty = false);
    }

    @Override
    public Flowable<User> getUser(final String userName) {
        if (TextUtils.equals(userNameCache, userName) && userCache != null) {
            return Flowable.fromArray(userCache);
        }
        return remoteDataSource.getUser(userName)
                .doOnNext(user -> {
                    userCache = user;
                    userNameCache = userName;
                });
    }

    @Override
    public void refresh() {
        followerCacheIsDirty = true;
        followingCacheIsDirty = true;
        userCache = null;
    }
}

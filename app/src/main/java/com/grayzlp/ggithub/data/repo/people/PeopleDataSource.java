package com.grayzlp.ggithub.data.repo.people;

import com.grayzlp.ggithub.data.model.user.SimpleUser;
import com.grayzlp.ggithub.data.model.user.User;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Main entry point for access of follower data.
 */

public interface PeopleDataSource {

    Flowable<List<SimpleUser>> getFollowers();

    Flowable<List<SimpleUser>> getFollowing();

    Flowable<User> getUser(String userName);

    void refresh();
}

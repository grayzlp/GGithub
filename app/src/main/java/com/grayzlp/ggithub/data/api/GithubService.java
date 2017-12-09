package com.grayzlp.ggithub.data.api;

import com.grayzlp.ggithub.data.model.event.BaseEvent;
import com.grayzlp.ggithub.data.model.feed.Feeds;
import com.grayzlp.ggithub.data.model.repo.Starred;
import com.grayzlp.ggithub.data.model.user.SimpleUser;
import com.grayzlp.ggithub.data.model.user.User;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Github API - https://developer.github.com/v3/
 */

public interface GithubService {

    String ENDPOINT = "https://api.github.com/";
    String DATE_FORMAT = "yyyy/MM/dd'T'HH:mm:ssZ";
    int PER_PAGE_MAX = 100;
    int PER_PAGE_DEFAULT = 30;

    @GET("user/{username}")
    Call<User> getUser(@Path("username") String username);

    @GET("user")
    Call<User> getAuthenticatedUser();

    @GET("feeds")
    Call<Feeds> getFeeds();

    /** Event api begin **/
    @GET("events")
    Call<BaseEvent[]> listPublicEvents();

    @GET("repos/{owner}/{:repo}/event")
    Call<BaseEvent[]> listRepositoryEvents(@Path("owner") String owner,
                                         @Path("repo") String repo);

    @GET("orgs/:org/events")
    Call<BaseEvent[]> listOrganizationEvents(@Path("org") String org);

    @GET("users/{username}/received_events/public")
    Flowable<List<BaseEvent>> listReceivedEvents(@Path("username") String username);

    @GET("users/{username}/events")
    Call<BaseEvent[]> listPerformedEvents(@Path("username") String username);

    @GET("users/{username}/events/public")
    Call<BaseEvent[]> listPerformedPublicEvents(@Path("username") String username);

    @GET("users/{username}/events/orgs/{org}")
    Call<BaseEvent[]> listOrganizationEventByUser(@Path("username") String username,
                                                  @Path("org") String org);

    @GET("user/starred")
    Flowable<List<Starred>> listCurrentUserStarred();

    @POST("user/starred/{owner}/{repo}")
    Flowable<Void> starRepo(@Path("owner") String owner,
                            @Path("repo") String repo);

    @DELETE("user/starred/{owner}/{repo}")
    Flowable<Void> unstarRepo(@Path("owner") String owner,
                            @Path("repo") String repo);

    @GET("/users/{username}/followers")
    Flowable<List<SimpleUser>> listUsersFollowers(@Path("username") String username);

    @GET("/user/followers")
    Flowable<List<SimpleUser>> listCurrentUserFollowers();

    @GET("/users/{username}/following")
    Flowable<List<SimpleUser>> listUsersFollowing(@Path("username") String username);

    @GET("/user/following")
    Flowable<List<SimpleUser>> listCurrentUserFollowing();

    @PUT("/user/following/{username}")
    Flowable<Void> followUser(@Path("username") String username);

    @DELETE("/user/following/{username}")
    Flowable<Void> unfollowUser(@Path("username") String username);


}

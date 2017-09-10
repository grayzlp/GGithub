package com.grayzlp.ggithub.data.api.github;

import com.grayzlp.ggithub.data.api.github.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
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

}

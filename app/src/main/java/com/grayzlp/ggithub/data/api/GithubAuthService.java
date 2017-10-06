package com.grayzlp.ggithub.data.api;

import com.grayzlp.ggithub.data.model.AccessToken;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Github Auth API
 */

public interface GithubAuthService {

    String ENDPOINT = "https://github.com/";

    @POST("/login/oauth/access_token")
    Call<AccessToken> getAccessToken(@Query("client_id") String client_id,
                                     @Query("client_secret") String client_secret,
                                     @Query("code") String code);

}

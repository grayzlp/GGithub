package com.grayzlp.ggithub.data.api;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A RequestInterceptor that adds an auth token to requests.
 */

public class AuthInterceptor implements Interceptor {

    private final String accessToken;

    public AuthInterceptor(@NonNull String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request request = chain.request().newBuilder()
                .addHeader("Authorization", "token " + accessToken)
                .build();
        return chain.proceed(request);
    }
}

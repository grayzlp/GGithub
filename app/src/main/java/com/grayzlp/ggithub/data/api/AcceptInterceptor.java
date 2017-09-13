package com.grayzlp.ggithub.data.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A Interceptor that adds on desire content type and api version.
 */

public class AcceptInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request request = chain.request().newBuilder()
                .addHeader("Accept", "application/json")
                .build();
        return chain.proceed(request);
    }
}

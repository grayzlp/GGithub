package com.grayzlp.ggithub.data.prefs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grayzlp.ggithub.BuildConfig;
import com.grayzlp.ggithub.data.api.AuthInterceptor;
import com.grayzlp.ggithub.data.api.github.GithubService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Storing github user state
 */

public class GithubPrefs {

    public static final String SIGN_IN_CALLBACK = "ggithub-auth-callback";
    public static final String SIGN_INT_URL =
            "https://api.github.com/authorizations/clients/?client_id="
                    + BuildConfig.GITHUB_CLIENT_ID
                    + "&redirect_uri=ggithub%3A%2F%2F"
                    + SIGN_IN_CALLBACK;

    private static final String GITHUB_PREF = "GITHUB_PREF";
    private static final String KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN";

    private static volatile GithubPrefs singleton;
    private final SharedPreferences prefs;


    private String accessToken;
    private boolean isSignedIn;

    private GithubService api;
    private List<GithubSignInStatusListener> signInStatusListeners;

    public static GithubPrefs get(Context context) {
        if (singleton == null) {
            synchronized (GithubPrefs.class) {
                if (singleton == null) {
                    singleton = new GithubPrefs(context);
                }
            }
        }
        return singleton;
    }

    private GithubPrefs(Context context) {
        prefs = context.getApplicationContext()
                .getSharedPreferences(GITHUB_PREF, Context.MODE_PRIVATE);
        accessToken = prefs.getString(KEY_ACCESS_TOKEN, null);
        isSignedIn = !TextUtils.isEmpty(accessToken);
        if (isSignedIn) {
            // get prop
        }

    }

    public interface GithubSignInStatusListener {
        void onGithubSignIn();

        void onGithubSignOut();
    }

    public boolean isSignedIn() {
        return isSignedIn;
    }

    public void setAccessToken(String accessToken) {
        if (!TextUtils.isEmpty(accessToken)) {
            this.accessToken = accessToken;
            isSignedIn = true;
            prefs.edit().putString(KEY_ACCESS_TOKEN, accessToken).apply();
            createApi();
            dispatchSignInEvent();
        }
    }

    public GithubService getApi() {
        if (api == null) {
            createApi();
        }
        return api;
    }

    public void signOut() {
        // TODO
    }

    public void signIn(Context context) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(SIGN_INT_URL)));
    }

    public void addSignInStatusListener(GithubSignInStatusListener listener) {
        if (signInStatusListeners == null) {
            signInStatusListeners = new ArrayList<>();
        }
        signInStatusListeners.add(listener);
    }

    public void removeSignInStatusListener(GithubSignInStatusListener listener) {
        if (signInStatusListeners != null) {
            signInStatusListeners.remove(listener);
        }
    }


    private void dispatchSignInEvent() {
        if (signInStatusListeners != null && !signInStatusListeners.isEmpty()) {
            for (GithubSignInStatusListener listener : signInStatusListeners) {
                listener.onGithubSignIn();
            }
        }
    }

    private void dispatchSignOutEvent() {
        if (signInStatusListeners != null && !signInStatusListeners.isEmpty()) {
            for (GithubSignInStatusListener listener : signInStatusListeners) {
                listener.onGithubSignOut();
            }
        }
    }

    private void createApi() {
        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(getAccessToken()))
                .build();
        final Gson gson = new GsonBuilder()
                .setDateFormat(GithubService.DATE_FORMAT)
                .create();
        api = new Retrofit.Builder()
                .baseUrl(GithubService.ENDPOINT)
                .client(client)
                .build()
                .create(GithubService.class);
    }

    private String getAccessToken() {
        return accessToken;
    }

}

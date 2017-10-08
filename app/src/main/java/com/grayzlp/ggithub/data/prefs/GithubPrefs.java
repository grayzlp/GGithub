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
import com.grayzlp.ggithub.data.api.GithubService;
import com.grayzlp.ggithub.data.model.event.BaseEvent;
import com.grayzlp.ggithub.data.model.event.inheritance.BaseEventDeserializer;
import com.grayzlp.ggithub.data.model.user.User;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Storing github user state
 */

public class GithubPrefs {

    public static final String SIGN_IN_CALLBACK = "ggithub-auth-callback";
    public static final String SIGN_IN_URL =
            "https://github.com/login/oauth/authorize/?client_id="
                    + BuildConfig.GITHUB_CLIENT_ID
                    + "&redirect_uri="
                    + BuildConfig.GITHUB_CLIENT_CALLBACK
                    + "&scope=user repo";

    private static final String GITHUB_PREF = "GITHUB_PREF";
    private static final String KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN";
    private static final String KEY_USER_ID = "KEY_UESE_ID";
    private static final String KEY_USER_NAME = "KEY_USER_NAME";
    private static final String KEY_USER_USERNAME = "KEY_USER_USERNAME";
    private static final String KEY_USER_AVATAR = "KEY_USER_AVATAR";
    private static final String KEY_USER_EMAIL = "KEY_USER_EMAIL";


    private static volatile GithubPrefs singleton;
    private final SharedPreferences prefs;


    private String accessToken;
    private boolean isSignedIn;
    private long userId;
    private String userName;
    private String userUsername;
    private String userAvatar;
    private String userEmail;

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
            userId = prefs.getLong(KEY_USER_ID, 0l);
            userName = prefs.getString(KEY_USER_NAME, null);
            userUsername = prefs.getString(KEY_USER_USERNAME, null);
            userAvatar = prefs.getString(KEY_USER_AVATAR, null);
            userEmail = prefs.getString(KEY_USER_EMAIL, null);
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

    public void setSignedInUser(User user) {
        if (user != null) {
            userName = user.login;
            userUsername = user.name;
            userId = user.id;
            userAvatar = user.avatar_url;
            userEmail = user.email;

            SharedPreferences.Editor editor = prefs.edit();
            editor.putLong(KEY_USER_ID, userId);
            editor.putString(KEY_USER_NAME, userName);
            editor.putString(KEY_USER_USERNAME, userUsername);
            editor.putString(KEY_USER_AVATAR, userAvatar);
            editor.putString(KEY_USER_EMAIL, userEmail);
            editor.apply();
        }
    }

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public User getUser() {
        return new User.Builder()
                .setId(userId)
                .setLogin(userName)
                .setName(userUsername)
                .setAvatar_url(userAvatar)
                .setEmail(userEmail)
                .build();
    }

    public GithubService getApi() {
        if (api == null) {
            createApi();
        }
        return api;
    }

    public void signOut() {
        isSignedIn = false;
        accessToken = null;
        userId = 0L;
        userName = null;
        userUsername = null;
        userAvatar = null;
        userEmail = null;

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_ACCESS_TOKEN, null);
        editor.putLong(KEY_USER_ID, 0L);
        editor.putString(KEY_USER_NAME, null);
        editor.putString(KEY_USER_AVATAR, null);
        editor.putString(KEY_USER_EMAIL, null);
        editor.apply();

        createApi();
        dispatchSignOutEvent();
    }

    public void signIn(Context context) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(SIGN_IN_URL)));
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
                .registerTypeAdapter(BaseEvent.class, new BaseEventDeserializer())
                .create();
        api = new Retrofit.Builder()
                .baseUrl(GithubService.ENDPOINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GithubService.class);
    }

    private String getAccessToken() {
        return accessToken;
    }

}

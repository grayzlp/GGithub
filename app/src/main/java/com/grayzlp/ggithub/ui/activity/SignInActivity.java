package com.grayzlp.ggithub.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.grayzlp.ggithub.BuildConfig;
import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.data.api.github.AcceptInterceptor;
import com.grayzlp.ggithub.data.api.github.GithubAuthService;
import com.grayzlp.ggithub.data.api.github.model.AccessToken;
import com.grayzlp.ggithub.data.prefs.GithubPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends Activity {

    boolean isLoginFailed = false;

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.sign_in)
    Button signIn;

    GithubPrefs githubPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        githubPrefs = GithubPrefs.get(this);

        checkAuthCallback(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        checkAuthCallback(intent);
    }

    private void checkAuthCallback(Intent intent) {
        if (intent != null
                && intent.getExtras() != null
                && !TextUtils.isEmpty(intent.getData().getAuthority())
                && GithubPrefs.SIGN_IN_CALLBACK.equals(intent.getData().getAuthority())) {
            showLoading();
            getAccessToken(intent.getData().getQueryParameter("code"));
        }
    }

    private void getAccessToken(String code) {
        final GithubAuthService githubAuthServiceApi = createGithubAuthService();

        final Call<AccessToken> accessTokenCall = githubAuthServiceApi.getAccessToken(
                BuildConfig.GITHUB_CLIENT_ID,
                BuildConfig.GITHUB_CLIENT_SECRET,
                code);
        accessTokenCall.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.body() == null) {
                    showSignInFail();
                    return;
                }
                isLoginFailed = false;
                githubPrefs.setAccessToken(response.body().access_token);
                showSignIn();
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                showSignInFail();
            }
        });
    }


    private GithubAuthService createGithubAuthService(){
        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AcceptInterceptor())
                .build();
        return new Retrofit.Builder()
                .baseUrl(GithubAuthService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create((GithubAuthService.class));
    }


    @OnClick(R.id.use_oauth)
    public void signInWithOauth() {
        githubPrefs.signIn(this);
    }


    private void showLoading() {

    }

    private void showSignIn() {
        Toast.makeText(this, "sign in", Toast.LENGTH_LONG).show();
    }


    private void showSignInFail() {
        Toast.makeText(this, "sign in fail", Toast.LENGTH_LONG).show();
    }


    @OnClick(R.id.sign_in)
    public void signIn() {
        //
    }

    boolean isLoginValid() {
        return username.length() > 0 && password.length() > 0;
    }

}

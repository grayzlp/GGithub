package com.grayzlp.ggithub.core.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;

import com.afollestad.materialdialogs.MaterialDialog;
import com.grayzlp.ggithub.BuildConfig;
import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.data.api.AcceptInterceptor;
import com.grayzlp.ggithub.data.api.GithubAuthService;
import com.grayzlp.ggithub.data.api.GithubService;
import com.grayzlp.ggithub.data.model.AccessToken;
import com.grayzlp.ggithub.data.model.user.User;
import com.grayzlp.ggithub.data.prefs.GithubPrefs;
import com.grayzlp.ggithub.util.LogUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = LogUtils.makeLogTag("SignInActivity");

    boolean isLoginFailed = false;

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.sign_in)
    Button signIn;

    Dialog loading;

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
        if (loading == null) {
            loading = new MaterialDialog.Builder(this)
                    .title(R.string.loading)
                    .content(R.string.please_wait)
                    .progress(true, 0)
                    .build();
        }
        loading.show();
    }

    private void showSignIn() {
        final GithubService githubService = GithubPrefs.get(this).getApi();
        final Call<User> userCall = githubService.getAuthenticatedUser();
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    showSignInFail();
                    return;
                }
                githubPrefs.setSignedInUser(response.body());
                startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showSignInFail();
            }
        });
    }


    private void showSignInFail() {
        if (loading.isShowing()) {
            loading.cancel();
        }
        Snackbar.make(signIn, R.string.sign_in_fail, Snackbar.LENGTH_LONG);
    }


    // TODO Fix base authenticate flow
    @OnClick(R.id.sign_in)
    public void signIn() {
        // Fix the compatibility of oauth and base authenticated flow in future. Only use oauth now.
    }

    boolean isLoginValid() {
        return username.length() > 0 && password.length() > 0;
    }
}

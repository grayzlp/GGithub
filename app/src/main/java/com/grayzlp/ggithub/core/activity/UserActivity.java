package com.grayzlp.ggithub.core.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.core.module.user.UserContract;
import com.grayzlp.ggithub.data.model.user.User;
import com.grayzlp.ggithub.di.ActivityScoped;
import com.grayzlp.ggithub.util.glide.GlideApp;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.BindsInstance;
import dagger.android.support.DaggerAppCompatActivity;

public class UserActivity extends DaggerAppCompatActivity implements UserContract.View{

    private static final String EXTRA_USER_NAME = "EXTRA_USER_NAME";

    @BindView(R.id.user_avatar) ImageView avatar;
    @BindView(R.id.user_username_expanded) TextView expandedUserName;
    @BindView(R.id.user_toolbar_title) TextView toolbarUsername;

    public static void launch(Activity launching, String userName) {
        Intent intent = new Intent(launching, UserActivity.class);
        intent.putExtra(EXTRA_USER_NAME, userName);
        launching.startActivity(intent);
    }

    @Inject
    UserContract.Presenter userPresenter;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        refreshFromIntent();

    }

    private void refreshFromIntent() {
        if (!getIntent().hasExtra(EXTRA_USER_NAME)) {
            throw new IllegalArgumentException("UserActivity should receive user name");
        }
        userName = getIntent().getStringExtra(EXTRA_USER_NAME);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        refreshFromIntent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        userPresenter.takeView(this);
        userPresenter.loadUser(userName, false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        userPresenter.dropView();
    }

    @Override
    public void showUser(User user) {
        GlideApp.with(this)
                .load(user.avatar_url)
                .placeholder(R.drawable.avatar_place_holder)
                .into(avatar);
        expandedUserName.setText(user.login);
        toolbarUsername.setText(user.name);
    }

    @Override
    public void showLoadError() {

    }
}

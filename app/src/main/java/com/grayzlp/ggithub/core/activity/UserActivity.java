package com.grayzlp.ggithub.core.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.core.module.user.UserContract;
import com.grayzlp.ggithub.data.model.user.User;
import com.grayzlp.ggithub.util.glide.GlideApp;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class UserActivity extends DaggerAppCompatActivity implements UserContract.View{

    private static final String EXTRA_USER_NAME = "EXTRA_USER_NAME";

    @BindView(R.id.user_avatar) ImageView avatar;
    @BindView(R.id.user_avatar_background) ImageView avatarBackground;
    @BindView(R.id.user_username_expanded) TextView expandedUserName;
    @BindView(R.id.user_toolbar_title) TextView toolbarUsername;
    @BindView(R.id.player_bio) TextView userBio;
    @BindView(R.id.repos_count) TextView reposCount;
    @BindView(R.id.gists_count) TextView gistsCount;
    @BindView(R.id.follows_count) TextView followsCount;
    @BindView(R.id.followings_count) TextView followingsCount;
    @BindView(R.id.user_detail_name) TextView userDetailName;
    @BindView(R.id.user_location) TextView userLocation;
    @BindView(R.id.user_email) TextView userEmail;
    @BindView(R.id.user_url) TextView userUrl;
    @BindView(R.id.user_joined) TextView userJoined;
    @BindView(R.id.user_contribution_chart) ImageView userContributionChart;

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
        bindListener();
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
        GlideApp.with(this)
                .load(user.avatar_url)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation()))
                .into(avatarBackground);
        expandedUserName.setText(user.login);
        toolbarUsername.setText(user.login);
        reposCount.setText(String.valueOf(user.public_repos));
        gistsCount.setText(String.valueOf(user.public_gists));
        followsCount.setText(String.valueOf(user.followers));
        followingsCount.setText(String.valueOf(user.following));
        userDetailName.setText(user.name);
        userLocation.setText(user.location);
        userEmail.setText(user.email);
        userUrl.setText(user.url);
        userJoined.setText(
                DateUtils.getRelativeTimeSpanString(user.created_at.getTime(),
                        System.currentTimeMillis(),
                        DateUtils.SECOND_IN_MILLIS)
                        .toString().toLowerCase());
        if (TextUtils.isEmpty(user.bio)) {
            userBio.setVisibility(View.GONE);
        } else {
            userBio.setVisibility(View.VISIBLE);
            userBio.setText(user.bio);
        }
    }

    private void bindListener() {
        findViewById(R.id.repos_count_container).setOnClickListener(v -> {
            RepositoriesListActivity.launch(this, userName);
        });
        findViewById(R.id.follow_count_container)
                .setOnClickListener(v ->
                        FollowListActivity.launch(this, userName, true));
        findViewById(R.id.following_count_container).
                setOnClickListener(v ->
                        FollowListActivity.launch(this, userName, false));
    }

    @Override
    public void showLoadError() {

    }
}

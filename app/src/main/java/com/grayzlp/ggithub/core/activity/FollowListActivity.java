package com.grayzlp.ggithub.core.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.core.module.follow.FollowContract;
import com.grayzlp.ggithub.core.module.follow.FollowPresenter;
import com.grayzlp.ggithub.data.model.repo.Repository;
import com.grayzlp.ggithub.data.model.user.SimpleUser;
import com.grayzlp.ggithub.util.glide.GlideApp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class FollowListActivity extends DaggerAppCompatActivity implements FollowContract.View {

    public static final String EXTRA_USER_NAME = "EXTRA_USER_NAME";
    public static final String EXTRA_IS_FOLLOWER = "EXTRA_IS_FOLLOWER";

    private String userName;

    public static void launch(Activity launching, String userName, boolean isFollowers) {
        Intent intent = new Intent(launching, FollowListActivity.class);
        intent.putExtra(EXTRA_USER_NAME, userName);
        intent.putExtra(EXTRA_IS_FOLLOWER, isFollowers);
        launching.startActivity(intent);
    }


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.people_list)
    RecyclerView peopleList;


    @Inject
    FollowPresenter presenter;
    boolean isFollowers;
    private FollowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_list);
        ButterKnife.bind(this);
        refreshFromIntent();
        configureToolbar();
        configureRecyclerView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void configureRecyclerView() {
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        peopleList.setLayoutManager(manager);
        adapter = new FollowAdapter(this);
        peopleList.setAdapter(adapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        refreshFromIntent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.takeView(this);
        if (isFollowers) {
            presenter.loadFollowerOfUser(userName, true);
        } else {
            presenter.loadFollowingOfUser(userName, false);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.dropView();
    }

    private void refreshFromIntent() {
        if (!getIntent().hasExtra(EXTRA_USER_NAME)) {
            throw new IllegalArgumentException("UserActivity should receive user name");
        }
        if (!getIntent().hasExtra(EXTRA_IS_FOLLOWER)) {
            throw new IllegalArgumentException("UserActivity should receive user name");
        }
        userName = getIntent().getStringExtra(EXTRA_USER_NAME);
        isFollowers = getIntent().getBooleanExtra(EXTRA_IS_FOLLOWER, true);
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(isFollowers ? "Followers" : "Followings");
    }

    @Override
    public void showUsers(List<SimpleUser> user) {
        adapter.swapItems(user);
    }

    @Override
    public void showLoadError() {

    }

    private class FollowAdapter extends RecyclerView.Adapter<FollowViewHolder> {

        private List<SimpleUser> items;
        private LayoutInflater inflater;

        private FollowAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            items = new ArrayList<>();
            setHasStableIds(true);
        }

        public void swapItems(List<SimpleUser> items) {
            if (items == null) {
                items = Collections.emptyList();
            }
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public FollowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FollowViewHolder(
                    inflater.inflate(R.layout.people_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(FollowViewHolder holder, int position) {
            SimpleUser user = items.get(position);
            GlideApp.with(FollowListActivity.this)
                    .load(user.avatar_url)
                    .into(holder.avatar);
            holder.textView.setText(user.login);
            holder.itemView.setOnClickListener(
                    v -> UserActivity.launch(FollowListActivity.this, user.login));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    static class FollowViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.more_action)
        ImageView moreAction;
        @BindView(R.id.username)
        TextView textView;

        public FollowViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

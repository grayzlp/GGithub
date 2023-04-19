package com.grayzlp.ggithub.core.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.core.module.repos.RepoListContract;
import com.grayzlp.ggithub.core.module.repos.RepoListPresenter;
import com.grayzlp.ggithub.data.model.repo.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class RepositoriesListActivity extends DaggerAppCompatActivity implements RepoListContract.View{

    public static final String EXTRA_USER_NAME = "EXTRA_USER_NAME";

    private String userName;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.repo_list)
    RecyclerView repoList;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    public static void launch(Activity launching, String userName) {
        Intent intent = new Intent(launching, RepositoriesListActivity.class);
        intent.putExtra(EXTRA_USER_NAME, userName);
        launching.startActivity(intent);
    }

    @Inject
    RepoListPresenter repoListPresenter;
    private RepositoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories_list);
        ButterKnife.bind(this);
        refreshFromIntent();
        configureToolbar();
        configureRecyclerView();
    }

    private void configureRecyclerView() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        repoList.setLayoutManager(manager);
        adapter = new RepositoryAdapter(this);
        repoList.setAdapter(adapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        refreshFromIntent();
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

    @Override
    protected void onStart() {
        super.onStart();
        repoListPresenter.takeView(this);
        repoListPresenter.loadUserRepos(userName, true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        repoListPresenter.dropView();
    }

    private void refreshFromIntent() {
        if (!getIntent().hasExtra(EXTRA_USER_NAME)) {
            throw new IllegalArgumentException("UserActivity should receive user name");
        }
        userName = getIntent().getStringExtra(EXTRA_USER_NAME);
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(userName);
    }

    @Override
    public void showRepos(List<Repository> repos) {
        adapter.swapItems(repos);
    }

    @Override
    public void showLoadError() {

    }


    private class RepositoryAdapter extends RecyclerView.Adapter<RepositoryViewHolder> {

        private List<Repository> items;
        private LayoutInflater inflater;

        private RepositoryAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            items = new ArrayList<>();
            setHasStableIds(true);
        }

        public void swapItems(List<Repository> items) {
            if (items == null) {
                items = Collections.emptyList();
            }
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RepositoryViewHolder(
                    inflater.inflate(R.layout.repo_item, parent, false));
        }

        @Override
        public void onBindViewHolder(RepositoryViewHolder holder, int position) {
            Repository repository = items.get(position);
            holder.repoTitle.setText(repository.name);
            holder.description.setText(repository.description);
            holder.repoTag.setText(repository.language);
            holder.forksCount.setText(String.valueOf(repository.forks_count));
            holder.stargazersCount.setText(String.valueOf(repository.stargazers_count));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    static class RepositoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.repo_title) TextView repoTitle;
        @BindView(R.id.description) TextView description;
        @BindView(R.id.repo_tag) TextView repoTag;
        @BindView(R.id.stargazers_count) TextView stargazersCount;
        @BindView(R.id.forks_count) TextView forksCount;

        public RepositoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

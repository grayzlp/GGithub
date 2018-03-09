package com.grayzlp.ggithub.core.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.core.module.repo.RepoContract;
import com.grayzlp.ggithub.data.model.repo.Repository;
import com.grayzlp.ggithub.util.LogUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class RepositoryActivity extends DaggerAppCompatActivity implements RepoContract.View {

    private static final String KEY_REPO_NAME = "KEY_REPO_NAME";


    public static void launch(Activity launching, String repoName) {
        Intent intent = new Intent(launching, RepositoryActivity.class);
        intent.putExtra(KEY_REPO_NAME, repoName);
        launching.startActivity(intent);
    }

    @BindView(R.id.content)
    TextView textView;

    @Inject
    RepoContract.Presenter presenter;

    private String repoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);
        ButterKnife.bind(this);
        fromIntent(getIntent());
    }

    private void fromIntent(Intent intent) {
        repoName = intent.getStringExtra(KEY_REPO_NAME);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        fromIntent(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.takeView(this);
        presenter.loadRepos(repoName, true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.dropView();
    }

    @Override
    public void showRepo(Repository repository) {
        textView.setText(repository.name + "\n\n"
            + repository.owner.login + "\n\n"
            + repository.description );
    }

    @Override
    public void showLoadError() {

    }
}

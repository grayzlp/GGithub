package com.grayzlp.ggithub.core.module.gist;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.data.model.gist.Gist;
import com.grayzlp.ggithub.di.ActivityScoped;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

@ActivityScoped
public class GistFragment extends DaggerFragment implements GistContract.View{

    @Inject
    public GistContract.Presenter mPresenter;
    private LayoutInflater mInflater;
    private GistAdapter mAdapter;

    @BindView(R.id.refresh) SwipeRefreshLayout mRefresh;
    @BindView(R.id.gist_list) RecyclerView mGistList;
    @BindView(R.id.error) ImageView mErrorView;
    @BindView(R.id.empty) View mEmptyView;

    @Inject
    public GistFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        View root = inflater.inflate(R.layout.fragment_gist, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRefresh.setOnRefreshListener(() -> mPresenter.loadGists(true));
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.takeView(this);
    }

    @Override
    public void onStop() {
        mPresenter.dropView();
        super.onStop();
    }

    @Override
    public void showGists(List<Gist> gists) {
        if (mAdapter != null) {
            mAdapter.swapItem(gists);
        } else {
            mAdapter = new GistAdapter(getActivity(), gists, mInflater);


            mGistList.setHasFixedSize(true);
            mGistList.setAdapter(mAdapter);
            mGistList.setLayoutManager(new LinearLayoutManager(getContext()));
            DividerItemDecoration dividerItemDecoration =
                    new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            mGistList.addItemDecoration(dividerItemDecoration);
        }


        mGistList.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingIndicator(boolean active) {
        mRefresh.setRefreshing(active);
    }

    @Override
    public void showLoadingError() {
        mGistList.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);

        AnimatedVectorDrawable avd = (AnimatedVectorDrawable)
                getContext().getDrawable(R.drawable.avd_no_connection);
        if (avd != null) {
            mErrorView.setImageDrawable(avd);
            avd.start();
        }
    }

    @Override
    public void showNoData() {
        mGistList.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
    }
}

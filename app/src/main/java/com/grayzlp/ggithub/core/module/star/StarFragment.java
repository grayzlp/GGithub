package com.grayzlp.ggithub.core.module.star;


import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.data.model.repo.Starred;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

public class StarFragment extends DaggerFragment implements StarContract.View{

    @Inject
    StarContract.Presenter mPresenter;

    @BindView(R.id.refresh) SwipeRefreshLayout mRefresh;
    @BindView(R.id.stars_list) RecyclerView mStarsList;
    @BindView(R.id.error) ImageView mErrorView;
    @BindView(R.id.empty) View mEmptyView;

    private LayoutInflater mInflater;

    private StarAdapter mAdapter;

    @Inject
    public StarFragment() {
        // no-op
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        final View root = inflater.inflate(R.layout.fragment_star, container, false);
        ButterKnife.bind(this, root);

        configureRecyclerView();
        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRefresh.setOnRefreshListener(() -> mPresenter.loadStars(true));
    }

    private void configureRecyclerView() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                mStarsList.getContext(),
                DividerItemDecoration.VERTICAL);
        mStarsList.addItemDecoration(dividerItemDecoration);
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
    public void showLoadingIndicator(boolean active) {
        mRefresh.setRefreshing(active);
    }

    @Override
    public void showLoadingError() {
        mStarsList.setVisibility(View.GONE);
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
    public void showNoStar() {
        mStarsList.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showStar(List<Starred> starreds) {
        mErrorView.setVisibility(View.GONE);
        mStarsList.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);

        if (mAdapter == null) {
            mAdapter = new StarAdapter(getActivity(), starreds, mInflater);
            mStarsList.setAdapter(mAdapter);
            mStarsList.setHasFixedSize(true);
            mStarsList.setLayoutManager(new LinearLayoutManager(getContext()));
            runLayoutAnimation();
        } else {
            mAdapter.swapItem(starreds);
        }
    }

    public void runLayoutAnimation() {
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_list_enter);

        mStarsList.setLayoutAnimation(controller);
        mStarsList.getAdapter().notifyDataSetChanged();
        mStarsList.scheduleLayoutAnimation();
    }

    @Override
    public void showRepo(Starred starred) {

    }
}

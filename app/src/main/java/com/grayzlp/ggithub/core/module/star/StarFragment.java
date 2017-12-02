package com.grayzlp.ggithub.core.module.star;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

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

    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.stars_list)
    RecyclerView mStarsList;
    @BindView(R.id.loading)
    ProgressBar mLoading;
    @BindView(R.id.error)
    ImageView mErrorView;
    @BindView(R.id.empty)
    View mEmptyView;

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

        return root;

    }

    @Override
    public void showLoadingIndicator(boolean active) {

    }

    @Override
    public void showLoadingError() {

    }

    @Override
    public void showNoStar() {

    }

    @Override
    public void showStar(List<Starred> starreds) {

    }

    @Override
    public void showRepo(Starred starred) {

    }
}

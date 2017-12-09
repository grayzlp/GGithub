package com.grayzlp.ggithub.core.module.people;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.data.model.user.SimpleUser;
import com.grayzlp.ggithub.data.model.user.User;
import com.grayzlp.ggithub.di.ActivityScoped;
import com.grayzlp.ggithub.util.LogUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

@ActivityScoped
public class PeopleFragment extends DaggerFragment
        implements PeopleContract.View {

    private static final String TAG = LogUtils.makeLogTag("PeopleFragment");

    @Inject
    PeopleContract.Presenter mPresenter;

    private LayoutInflater mInflater;

    private PeopleAdapter mAdapter;

    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.people_list)
    RecyclerView mPeopleList;
    @BindView(R.id.loading)
    ProgressBar mLoading;
    @BindView(R.id.error)
    ImageView mErrorView;
    @BindView(R.id.empty)
    View mEmptyView;

    @Inject
    public PeopleFragment() {
        // no-op
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        View root = mInflater.inflate(R.layout.fragment_people, container, false);
        ButterKnife.bind(this, root);
        return root;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRefresh.setOnRefreshListener(() -> mPresenter.load(true));
    }

    @Override
    public void showPeople(List<SimpleUser> users) {
        if (mAdapter == null) {
            mAdapter = new PeopleAdapter(getActivity(), users, mInflater);
            mPeopleList.setAdapter(mAdapter);

            mPeopleList.setHasFixedSize(true);
            mPeopleList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            mAdapter.swapItem(users);
        }

    }

    @Override
    public void showLoadingIndicator(boolean active) {
        mRefresh.setRefreshing(active);
    }

    @Override
    public void showLoadingError() {
        LogUtils.LOGD(TAG, "showLoadingError");
        // TODO
    }
}

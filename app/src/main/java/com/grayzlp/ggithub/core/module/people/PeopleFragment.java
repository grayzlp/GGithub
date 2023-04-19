package com.grayzlp.ggithub.core.module.people;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.TransitionRes;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.data.model.user.SimpleUser;
import com.grayzlp.ggithub.di.ActivityScoped;
import com.grayzlp.ggithub.util.LogUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerFragment;

@ActivityScoped
public class PeopleFragment extends DaggerFragment
        implements PeopleContract.View {

    private static final String TAG = LogUtils.makeLogTag("PeopleFragment");

    @Inject
    PeopleContract.Presenter mPresenter;
    private LayoutInflater mInflater;
    private PeopleAdapter mAdapter;

    private SparseArray<Transition> transitionCache = new SparseArray<>();

    @BindView(R.id.container)
    FrameLayout mContainer;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.people_list)
    RecyclerView mPeopleList;
    @BindView(R.id.error)
    ImageView mErrorView;
    @BindView(R.id.empty)
    View mEmptyView;
    @BindView(R.id.fab_switch)
    ImageButton mSwitchFab;
    @BindView(R.id.confirm_filter_container)
    FrameLayout mConfirmContainer;
    @BindView(R.id.results_scrim)
    View mResultsScrim;
    @BindView(R.id.show_followers)
    CheckedTextView mShowFollowers;
    @BindView(R.id.show_followings)
    CheckedTextView mSHowFOllowings;

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


            TransitionManager.beginDelayedTransition(mContainer,
                    getTransition(R.transition.show_people));
            mPeopleList.setVisibility(View.VISIBLE);
            mErrorView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.GONE);
            mSwitchFab.setVisibility(View.VISIBLE);
            mResultsScrim.setVisibility(View.GONE);
            mConfirmContainer.setVisibility(View.GONE);
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
        TransitionManager.beginDelayedTransition(mContainer, getTransition(R.transition.auto));
        mPeopleList.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
        mSwitchFab.setVisibility(View.GONE);
        mResultsScrim.setVisibility(View.GONE);
        mConfirmContainer.setVisibility(View.GONE);

        AnimatedVectorDrawable avd = (AnimatedVectorDrawable)
                getContext().getDrawable(R.drawable.avd_no_connection);
        if (avd != null) {
            mErrorView.setImageDrawable(avd);
            avd.start();
        }
    }

    @Override
    public void showNoData() {
        TransitionManager.beginDelayedTransition(mContainer, getTransition(R.transition.auto));
        mPeopleList.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
        mSwitchFab.setVisibility(View.VISIBLE);
        mResultsScrim.setVisibility(View.GONE);
        mConfirmContainer.setVisibility(View.GONE);
    }

    @OnClick(R.id.fab_switch)
    protected void switchType() {
        TransitionManager.beginDelayedTransition(mContainer,
                getTransition(R.transition.people_show_switch));
        mSwitchFab.setVisibility(View.INVISIBLE);
        mConfirmContainer.setVisibility(View.VISIBLE);
        mResultsScrim.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.show_followings, R.id.show_followers})
    protected void doSwitch(CheckedTextView checkedTextView) {
        if (checkedTextView.equals(mShowFollowers)) {
            mShowFollowers.setChecked(true);
            mSHowFOllowings.setChecked(false);
            mPresenter.setPeopleType(PeopleType.FOLLOWER);
        } else if (checkedTextView.equals(mSHowFOllowings)) {
            mShowFollowers.setChecked(false);
            mSHowFOllowings.setChecked(true);
            mPresenter.setPeopleType(PeopleType.FOLLOWING);
        }
    }


    @OnClick({R.id.results_scrim, R.id.confirmed})
    protected void hideSwitchConfirmation() {
        if (mConfirmContainer.getVisibility() == View.VISIBLE) {
            TransitionManager.beginDelayedTransition(
                    mContainer, getTransition(R.transition.people_hide_switch));
            mConfirmContainer.setVisibility(View.GONE);
            mResultsScrim.setVisibility(View.GONE);
            mSwitchFab.setVisibility(View.VISIBLE);
        }
    }


    Transition getTransition(@TransitionRes int transitionId) {
        Transition transition = transitionCache.get(transitionId);
        if (transition == null) {
            transition = TransitionInflater.from(getContext()).inflateTransition(transitionId);
            transitionCache.append(transitionId, transition);
        }
        return transition;
    }
}

package com.grayzlp.ggithub.core.module.event;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.data.model.event.BaseEvent;
import com.grayzlp.ggithub.util.LogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

public class EventFragment extends Fragment implements EventContract.View{

    private static final String TAG = LogUtils.makeLogTag("EventFragment");

    EventContract.Presenter mPresenter;

    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.events_list)
    RecyclerView mEventsList;
    @BindView(R.id.loading)
    ProgressBar mLoading;
    @BindView(R.id.error)
    ImageView mErrorView;
    @BindView(R.id.empty)
    View mEmptyView;


    private boolean mRefreshing;
    private LayoutInflater mInflater;

    EventAdapter mAdapter;


    public EventFragment(){
        // no-op
    }

    public static EventFragment newInstance(){
        return new EventFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        View root = inflater.inflate(R.layout.fragment_event, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadEvents(true);
            }
        });
        mPresenter.loadEvents(false);
    }

    @Override
    public void setPresenter(@NonNull EventContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showLoadingIndicator(boolean active) {
        mRefresh.setRefreshing(active);
    }

    @Override
    public void showLoadingError() {
        mLoading.setVisibility(View.GONE);
        mEventsList.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);

        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_no_connection);
        if (avd != null) {
            mErrorView.setImageDrawable(avd);
            avd.start();
        }
    }

    @Override
    public void showNoEvent() {
        mLoading.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEvents(List<BaseEvent> events) {
        mLoading.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mEventsList.setVisibility(View.VISIBLE);

        if (mAdapter == null) {
            mAdapter = new EventAdapter(getActivity(), events, mInflater);
            mEventsList.setAdapter(mAdapter);
            mEventsList.setHasFixedSize(true);
            mEventsList.setLayoutManager(new LinearLayoutManager(getContext()));
            runLayoutAnimation();
        } else {
            mAdapter.swapItem(events);
        }

    }

    public void runLayoutAnimation() {
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_list_enter);

        mEventsList.setLayoutAnimation(controller);
        mEventsList.getAdapter().notifyDataSetChanged();
        mEventsList.scheduleLayoutAnimation();
    }


    @Override
    public void showEventActor() {

    }

    @Override
    public void showEventDetail() {

    }
}

package com.grayzlp.ggithub.core.event;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.data.api.model.event.BaseEvent;
import com.grayzlp.ggithub.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

public class EventFragment extends Fragment implements EventContract.View{

    private static final String TAG = LogUtils.makeLogTag("EventFragment");

    EventContract.Presenter mPresenter;

    @BindView(R.id.events_list)
    RecyclerView mEventsList;
    @BindView(R.id.loading)
    ProgressBar mLoading;
    @BindView(R.id.error)
    ImageView mErrorView;


    public EventFragment(){
        // no-op
    }

    public static EventFragment newInstance(){
        return new EventFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_event, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.loadEvents();
    }

    @Override
    public void setPresenter(@NonNull EventContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showLoadingIndicator(boolean active) {

    }

    @Override
    public void showLoadingError() {

    }

    @Override
    public void showNoEvent() {

    }

    @Override
    public void showEvents(BaseEvent[] events) {
        LogUtils.LOGD(TAG, "ShowEvent: length:" + events.length);
    }

    @Override
    public void showEventActor() {

    }

    @Override
    public void showEventDetail() {

    }
}

package com.grayzlp.ggithub.core.event;


import android.content.Context;

import com.grayzlp.ggithub.data.api.model.event.BaseEvent;
import com.grayzlp.ggithub.data.prefs.GithubPrefs;
import com.grayzlp.ggithub.util.LogUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.grayzlp.ggithub.util.LogUtils.makeLogTag;

public class EventPresenter implements EventContract.Presenter {

    private static final String TAG = makeLogTag("EventPresenter");

    private EventContract.View mEventView;
    private GithubPrefs mGithubPrefs;

    public EventPresenter(EventContract.View eventView, Context context) {
        this.mEventView = checkNotNull(eventView);
        checkNotNull(context);
        mGithubPrefs = GithubPrefs.get(context);
        mEventView.setPresenter(this);
    }

    @Override
    public void loadEvents() {
        LogUtils.LOGD(TAG, "Load events start");
        mEventView.showLoadingIndicator(true);
        Call<BaseEvent[]> call = mGithubPrefs.getApi().
                listReceivedEvents(mGithubPrefs.getUserName());
        call.enqueue(new Callback<BaseEvent[]>() {
            @Override
            public void onResponse(Call<BaseEvent[]> call, Response<BaseEvent[]> response) {
                LogUtils.LOGD(TAG, "Load events onResponse, isSuccessFul: " + response.message());
                if (response.isSuccessful()) {
                    mEventView.showLoadingIndicator(false);
                    mEventView.showEvents(response.body());
                } else {
                    mEventView.showLoadingError();
                }

            }

            @Override
            public void onFailure(Call<BaseEvent[]> call, Throwable t) {
                LogUtils.LOGD(TAG, "Load events onFailure, error: " + t.getMessage());
                mEventView.showLoadingIndicator(false);
                mEventView.showLoadingError();
            }
        });
    }

    @Override
    public void openEventActor(BaseEvent event) {
        mEventView.showEventActor();
    }

    @Override
    public void openEventDetail(BaseEvent event) {
        mEventView.showEventDetail();
    }
}

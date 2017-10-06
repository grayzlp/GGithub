package com.grayzlp.ggithub.core.module.event;


import android.content.Context;

import com.grayzlp.ggithub.data.local.events.EventsLocalDataSource;
import com.grayzlp.ggithub.data.model.event.BaseEvent;
import com.grayzlp.ggithub.data.prefs.GithubPrefs;
import com.grayzlp.ggithub.data.remote.event.EventsRemoteDataSource;
import com.grayzlp.ggithub.data.repo.event.EventsDataSource;
import com.grayzlp.ggithub.data.repo.event.EventsRepository;
import com.grayzlp.ggithub.util.LogUtils;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.grayzlp.ggithub.util.LogUtils.makeLogTag;

public class EventPresenter implements EventContract.Presenter {

    private static final String TAG = makeLogTag("EventPresenter");

    private EventContract.View mEventView;
    private GithubPrefs mGithubPrefs;
    private EventsRepository mRepository;

    public EventPresenter(EventContract.View eventView, Context context) {
        this.mEventView = checkNotNull(eventView);
        checkNotNull(context);
        mGithubPrefs = GithubPrefs.get(context);
        mEventView.setPresenter(this);
        mRepository = EventsRepository.getInstance(
                EventsRemoteDataSource.getInstance(mGithubPrefs),
                EventsLocalDataSource.getInstance(context));
    }

    @Override
    public void loadEvents(boolean forceUpdate) {
        LogUtils.LOGD(TAG, "Load events start");
        mEventView.showLoadingIndicator(true);
        if (forceUpdate) {
            mRepository.refreshTasks();
        }
        mRepository.getEvents(new EventsDataSource.LoadEventsCallback() {
            @Override
            public void onEventsLoaded(List<BaseEvent> events) {
                mEventView.showLoadingIndicator(false);
                mEventView.showEvents(events);
            }

            @Override
            public void onDataNotAvailable() {
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

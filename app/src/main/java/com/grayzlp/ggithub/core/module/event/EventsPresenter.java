package com.grayzlp.ggithub.core.module.event;


import com.google.common.base.Preconditions;
import com.grayzlp.ggithub.data.model.event.BaseEvent;
import com.grayzlp.ggithub.data.repo.event.EventsDataSource;
import com.grayzlp.ggithub.data.repo.event.EventsRepository;
import com.grayzlp.ggithub.di.ActivityScoped;
import com.grayzlp.ggithub.util.LogUtils;

import java.util.List;

import javax.inject.Inject;

import static com.grayzlp.ggithub.util.LogUtils.makeLogTag;

@ActivityScoped
public class EventsPresenter implements EventContract.Presenter {

    private static final String TAG = makeLogTag("EventsPresenter");

    private EventContract.View mEventView;
    private final EventsRepository mEventsRepository;

    @Inject
    public EventsPresenter(EventsRepository eventsRepository) {
        mEventsRepository =  eventsRepository;
    }

    @Override
    public void loadEvents(boolean forceUpdate) {
        LogUtils.LOGD(TAG, "Load events start");
        mEventView.showLoadingIndicator(true);
        if (forceUpdate) {
            mEventsRepository.refreshTasks();
        }
        mEventsRepository.getEvents(new EventsDataSource.LoadEventsCallback() {
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

    @Override
    public void takeView(EventContract.View view) {
        mEventView = Preconditions.checkNotNull(view);
        loadEvents(false);
    }

    @Override
    public void dropView() {
        mEventView = null;
    }
}

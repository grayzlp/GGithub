package com.grayzlp.ggithub.core.module.event;


import android.support.annotation.NonNull;

import com.google.common.base.Preconditions;
import com.grayzlp.ggithub.data.model.event.BaseEvent;
import com.grayzlp.ggithub.data.repo.event.EventsRepository;
import com.grayzlp.ggithub.di.ActivityScoped;
import com.grayzlp.ggithub.util.LogUtils;
import com.grayzlp.ggithub.util.scheduler.BaseSchedulerProvider;
import com.grayzlp.ggithub.util.scheduler.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.grayzlp.ggithub.util.LogUtils.makeLogTag;

@ActivityScoped
public class EventsPresenter implements EventContract.Presenter {

    private static final String TAG = makeLogTag("EventsPresenter");

    private boolean mFirstLoad = true;

    private EventContract.View mEventView;
    private final EventsRepository mEventsRepository;

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    private final BaseSchedulerProvider mScheduleProvider;

    @Inject
    public EventsPresenter(EventsRepository eventsRepository) {
        mEventsRepository =  eventsRepository;

        mScheduleProvider = SchedulerProvider.getInstance();
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        loadEvents(true);
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void loadEvents(boolean forceUpdate) {
        LogUtils.LOGD(TAG, "Load events start");
        mEventView.showLoadingIndicator(true);
        if (forceUpdate || mFirstLoad) {
            mEventsRepository.refreshTasks();
            mFirstLoad = false;
        }

        mCompositeDisposable.clear();
        Disposable disposable = mEventsRepository
                .getEvents()
                .subscribeOn(mScheduleProvider.io())
                .observeOn(mScheduleProvider.ui())
                .subscribe(
                        events -> {
                            mEventView.showLoadingIndicator(false);
                            mEventView.showEvents(events);
                        },
                        throwable -> {
                            mEventView.showLoadingIndicator(false);
                            mEventView.showLoadingError();
                        }
                );
        mCompositeDisposable.add(disposable);
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

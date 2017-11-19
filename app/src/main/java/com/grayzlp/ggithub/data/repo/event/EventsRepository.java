package com.grayzlp.ggithub.data.repo.event;

import com.grayzlp.ggithub.data.Remote;
import com.grayzlp.ggithub.data.model.event.BaseEvent;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Concrete implementation to load events to from the data sources into a cache.
 */
@Singleton
public class EventsRepository implements EventsDataSource {

    private final EventsDataSource mEventsRemoteDataSource;

    private boolean mCacheIsDirty = false;

    private List<BaseEvent> mCachedEvents;

    @Inject
    public EventsRepository(@Remote EventsDataSource remote) {
        mEventsRemoteDataSource = remote;
    }

    @Override
    public Flowable<List<BaseEvent>> getEvents() {
        if (mCachedEvents != null && !mCacheIsDirty) {
            return Flowable.just(mCachedEvents);
        }

        return getAndSaveRemoteEvents();
    }


    private Flowable<List<BaseEvent>> getAndSaveRemoteEvents() {
        return mEventsRemoteDataSource
                .getEvents()
                .flatMap(events -> Flowable.just(events)
                        .doOnNext(items -> mCachedEvents = items))
                        .doOnComplete(() -> mCacheIsDirty = false);
    }

    @Override
    public void refreshTasks() {
        mCacheIsDirty = true;
    }
}

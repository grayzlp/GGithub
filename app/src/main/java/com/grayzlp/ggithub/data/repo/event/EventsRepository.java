package com.grayzlp.ggithub.data.repo.event;

import android.support.annotation.NonNull;

import com.grayzlp.ggithub.data.Local;
import com.grayzlp.ggithub.data.Remote;
import com.grayzlp.ggithub.data.model.event.BaseEvent;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Concrete implementation to load events to from the data sources into a cache.
 */
@Singleton
public class EventsRepository implements EventsDataSource {

    private final EventsDataSource mEventsRemoteDataSource;

    private final EventsDataSource mEventsLocalDataSource;

    private boolean mCacheIsDirty = false;

    private List<BaseEvent> mCachedEvents;

    @Inject
    public EventsRepository(@Remote EventsDataSource remote,
                             @Local EventsDataSource local) {
        mEventsRemoteDataSource = remote;
        mEventsLocalDataSource = local;
    }

    @Override
    public Observable<List<BaseEvent>> getEvents() {
        if (mCachedEvents != null && !mCacheIsDirty) {
            return Observable.just(mCachedEvents);
        }

        Observable<List<BaseEvent>> remoteEvents = getAndSaveRemoteEvents();

        if (mCacheIsDirty) {
            return remoteEvents;
        } else {
            Observable<List<BaseEvent>> localEvents = getAndCacheLocalEvents();
            return Observable.concat(localEvents, remoteEvents)
                    .firstOrError()
                    .toObservable();
        }
    }

    private Observable<List<BaseEvent>> getAndCacheLocalEvents() {
        return mEventsLocalDataSource.getEvents()
                .doOnNext(events -> mCachedEvents = events);
    }

    private Observable<List<BaseEvent>> getAndSaveRemoteEvents() {
        return mEventsRemoteDataSource
                .getEvents()
                .flatMap(events -> Observable.just(events).doOnNext(items -> {
                    mEventsLocalDataSource.saveTasks(items);
                    mCachedEvents = items;
                })).doOnComplete(() -> mCacheIsDirty = false);
    }

    private void refreshLocalDataSource(List<BaseEvent> events) {
        mEventsLocalDataSource.saveTasks(events);
    }

    @Override
    public void getTask(long id, @NonNull GetEventCallback callback) {
        // TODO
    }

    @Override
    public void saveTasks(List<BaseEvent> events) {
        // no-op, events is only readable in api
    }

    @Override
    public void refreshTasks() {
        mCacheIsDirty = true;
    }
}

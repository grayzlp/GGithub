package com.grayzlp.ggithub.data.repo.event;

import android.support.annotation.NonNull;

import com.grayzlp.ggithub.data.Local;
import com.grayzlp.ggithub.data.Remote;
import com.grayzlp.ggithub.data.model.event.BaseEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;

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
    public void getEvents(@NonNull final LoadEventsCallback callback) {
        checkNotNull(callback);
        if (mCachedEvents != null && !mCacheIsDirty) {
            callback.onEventsLoaded(new ArrayList<BaseEvent>(mCachedEvents));
            return;
        }
        if (mCacheIsDirty) {
            getEventsFromRemoteDataSource(callback);
        } else {
            mEventsLocalDataSource.getEvents(new LoadEventsCallback() {
                @Override
                public void onEventsLoaded(List<BaseEvent> events) {
                    refreshCache(events);
                    callback.onEventsLoaded(events);
                }

                @Override
                public void onDataNotAvailable() {
                    getEventsFromRemoteDataSource(callback);
                }
            });
        }
    }

    private void refreshCache(List<BaseEvent> events) {
        mCachedEvents = events;
        mCacheIsDirty = false;
    }

    private void getEventsFromRemoteDataSource(final LoadEventsCallback callback) {
        mEventsRemoteDataSource.getEvents(new LoadEventsCallback() {
            @Override
            public void onEventsLoaded(List<BaseEvent> events) {
                refreshCache(events);
                refreshLocalDataSource(events);
                callback.onEventsLoaded(events);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
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

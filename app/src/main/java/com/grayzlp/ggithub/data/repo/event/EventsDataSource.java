package com.grayzlp.ggithub.data.repo.event;

import android.support.annotation.NonNull;

import com.grayzlp.ggithub.data.model.event.BaseEvent;

import java.util.List;

/**
 * Main entry point for accessing events data.
 */

public interface EventsDataSource {

    interface LoadEventsCallback {
        void onEventsLoaded(List<BaseEvent> events);
        void onDataNotAvailable();
    }

    interface GetEventCallback {
        void onEventLoaded(BaseEvent event);
        void onDataNotAvaiable();
    }

    void getEvents(@NonNull LoadEventsCallback callback);

    void getTask(long id, @NonNull GetEventCallback callback);

    void saveTasks(List<BaseEvent> events);

    void refreshTasks();
}

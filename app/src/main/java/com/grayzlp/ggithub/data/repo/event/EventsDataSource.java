package com.grayzlp.ggithub.data.repo.event;

import android.support.annotation.NonNull;

import com.grayzlp.ggithub.data.model.event.BaseEvent;

import java.util.List;

import io.reactivex.Observable;

/**
 * Main entry point for accessing events data.
 */

public interface EventsDataSource {

    interface GetEventCallback {
        void onEventLoaded(BaseEvent event);
        void onDataNotAvaiable();
    }

    Observable<List<BaseEvent>> getEvents();

    void getTask(long id, @NonNull GetEventCallback callback);

    void saveTasks(List<BaseEvent> events);

    void refreshTasks();
}

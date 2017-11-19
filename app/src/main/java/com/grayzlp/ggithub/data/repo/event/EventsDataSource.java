package com.grayzlp.ggithub.data.repo.event;

import com.grayzlp.ggithub.data.model.event.BaseEvent;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Main entry point for accessing events data.
 */

public interface EventsDataSource {

    Flowable<List<BaseEvent>> getEvents();

    void refreshTasks();
}

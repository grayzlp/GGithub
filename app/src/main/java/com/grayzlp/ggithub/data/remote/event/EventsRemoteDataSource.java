package com.grayzlp.ggithub.data.remote.event;

import android.content.Context;
import android.support.annotation.NonNull;

import com.grayzlp.ggithub.data.model.event.BaseEvent;
import com.grayzlp.ggithub.data.prefs.GithubPrefs;
import com.grayzlp.ggithub.data.repo.event.EventsDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 *  Implementation of the data source use network.
 */
@Singleton
public class EventsRemoteDataSource implements EventsDataSource {

    private GithubPrefs mPrefs;

    @Inject
    public EventsRemoteDataSource(@NonNull Context context) {
        mPrefs = GithubPrefs.get(context);
    }

    @Override
    public Observable<List<BaseEvent>> getEvents() {
        return mPrefs.getApi().listReceivedEvents(mPrefs.getUserName());
    }

    @Override
    public void getTask(long id, @NonNull GetEventCallback callback) {
        // TODO
    }

    @Override
    public void saveTasks(List<BaseEvent> events) {
        // no-op
    }

    @Override
    public void refreshTasks() {
        // no-op
    }
}

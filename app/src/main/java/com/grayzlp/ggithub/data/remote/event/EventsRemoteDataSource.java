package com.grayzlp.ggithub.data.remote.event;

import android.content.Context;
import androidx.annotation.NonNull;

import com.grayzlp.ggithub.data.model.event.BaseEvent;
import com.grayzlp.ggithub.data.prefs.GithubPrefs;
import com.grayzlp.ggithub.data.repo.event.EventsDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

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
    public Flowable<List<BaseEvent>> getEvents() {
        return mPrefs.getApi().listReceivedEvents(mPrefs.getUserName());
    }

    @Override
    public void refreshTasks() {
        // no-op
    }
}

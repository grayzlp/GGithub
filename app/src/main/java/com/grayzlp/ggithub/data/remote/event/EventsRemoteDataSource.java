package com.grayzlp.ggithub.data.remote.event;

import android.content.Context;
import android.support.annotation.NonNull;

import com.grayzlp.ggithub.data.model.event.BaseEvent;
import com.grayzlp.ggithub.data.prefs.GithubPrefs;
import com.grayzlp.ggithub.data.repo.event.EventsDataSource;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void getEvents(@NonNull final LoadEventsCallback callback) {
        Call<BaseEvent[]> baseEventCall =  mPrefs.getApi().listReceivedEvents(mPrefs.getUserName());
        baseEventCall.enqueue(new Callback<BaseEvent[]>() {
            @Override
            public void onResponse(@NonNull Call<BaseEvent[]> call,
                                   @NonNull Response<BaseEvent[]> response) {
                if (response.isSuccessful()) {
                    callback.onEventsLoaded(Arrays.asList(response.body()));
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<BaseEvent[]> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
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

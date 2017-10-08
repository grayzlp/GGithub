package com.grayzlp.ggithub.data.local.events;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grayzlp.ggithub.data.model.event.BaseEvent;
import com.grayzlp.ggithub.data.model.event.inheritance.BaseEventDeserializer;
import com.grayzlp.ggithub.data.repo.event.EventsDataSource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.grayzlp.ggithub.data.local.events.EventsPersistenceContract.EventEntry;

/**
 * Concrete implementation of data source as a db.
 */

@Singleton
public class EventsLocalDataSource implements EventsDataSource {

    private static EventsLocalDataSource INSTANCE;

    private EventsDbHelper mDbHelper;

    @Inject
    public EventsLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new EventsDbHelper(context);
    }

    @Override
    public Observable<List<BaseEvent>> getEvents() {
        List<BaseEvent> events = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                EventEntry.COLUMN_NAME_ENTRY_ID,
                EventEntry.COLUMNE_NAME_OBJECT,
        };

        Cursor c = db.query(EventEntry.TABLE_NAME, projection, null,
                null, null, null, null);
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                byte[] bytes = c.getBlob(c.getColumnIndexOrThrow(EventEntry.COLUMNE_NAME_OBJECT));
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(BaseEvent.class, new BaseEventDeserializer())
                        .create();
                BaseEvent event = gson.fromJson(new String(bytes), BaseEvent.class);
                events.add(event);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        return Observable.just(events);
    }

    @Override
    public void getTask(long id, @NonNull GetEventCallback callback) {
        // TODO
    }

    @Override
    public void saveTasks(final List<BaseEvent> events) {
        deleteAllEvents();
        // TODO Use more efficient way instead of just a thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (BaseEvent event : events) {
                    saveTask(event);
                }
            }
        }).start();
    }

    private void saveTask(BaseEvent event) {
        long start = System.currentTimeMillis();
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EventEntry.COLUMN_NAME_ENTRY_ID, event.id);
        Gson gson = new Gson();
        values.put(EventEntry.COLUMNE_NAME_OBJECT, gson.toJson(event).getBytes());

        db.insert(EventEntry.TABLE_NAME, null, values);



        db.close();
        Log.d("grayzlp", "time = " + (System.currentTimeMillis() - start));
    }

    private void deleteAllEvents() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        db.delete(EventEntry.TABLE_NAME, null, null);
        db.close();
    }

    @Override
    public void refreshTasks() {
        // no-op
    }
}

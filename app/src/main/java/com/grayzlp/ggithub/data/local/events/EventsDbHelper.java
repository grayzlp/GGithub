package com.grayzlp.ggithub.data.local.events;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventsDbHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Events.db";

    private static final String TEXT_BLOB = " BLOB";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + EventsPersistenceContract.EventEntry.TABLE_NAME + " (" +
                    EventsPersistenceContract.EventEntry.COLUMN_NAME_ENTRY_ID + " LONG PRIMARY KEY" + COMMA_SEP +
                    EventsPersistenceContract.EventEntry.COLUMNE_NAME_OBJECT + TEXT_BLOB +
                    ")";


    public EventsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

package com.grayzlp.ggithub.data.local.events;

import android.provider.BaseColumns;

/**
 * The contract used for the db to save the tasks locally.
 */

public final class EventsPersistenceContract {

    private EventsPersistenceContract() {

    }

    public static abstract class EventEntry implements BaseColumns {
        public static final String TABLE_NAME = "task";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMNE_NAME_OBJECT = "object";
    }

}

package com.atritripathi.chantsjournal;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Mantra.class}, version = 1, exportSchema = false)
public abstract class MantraDatabase extends RoomDatabase {

    public abstract MantraDao mantraDao();

    private static MantraDatabase INSTANCE;

    static MantraDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MantraDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MantraDatabase.class, "mantra_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

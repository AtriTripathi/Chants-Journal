package com.atritripathi.chantsjournal;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "mantra_table")
public class Mantra {

    public Mantra(@NonNull String mantra) {
        this.mantra = mantra;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "mantra_name")
    private String mantra;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMantra() {
        return mantra;
    }

    public void setMantra(String mantra) {
        this.mantra = mantra;
    }
}

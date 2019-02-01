package com.atritripathi.chantsjournal;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "mantra_table")
public class Mantra {

    // Constructor
    public Mantra(String mantraName, int totalMalas) {
        this.mantraName = mantraName;
        this.totalMalas = totalMalas;
    }


    // Columns in the table
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "mantra_name")
    private String mantraName;

    @ColumnInfo(name = "total_malas")
    private int totalMalas;

    @ColumnInfo(name = "completed_malas")
    private int completedMalas;


    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMantraName() {
        return mantraName;
    }

    public void setMantraName(String mantra) {
        this.mantraName = mantra;
    }

    public int getTotalMalas() {
        return totalMalas;
    }

    public void setTotalMalas(int totalMalas) {
        this.totalMalas = totalMalas;
    }

    public int getCompletedMalas() {
        return completedMalas;
    }

    public void setCompletedMalas(int completedMalas) {
        this.completedMalas = completedMalas;
    }

}

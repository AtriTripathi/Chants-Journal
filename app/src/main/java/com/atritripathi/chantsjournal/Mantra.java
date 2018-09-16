package com.atritripathi.chantsjournal;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "mantra_table")
public class Mantra {

    // Constructor
    public Mantra(String mantraName, String malasCount, String startDate, String endDate, String notes) {
        this.mantraName = mantraName;
        this.malasCount = malasCount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
    }


    // Columns in the table
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "mantra_name")
    private String mantraName;

    @ColumnInfo(name = "malas_count")
    private String malasCount;

    @ColumnInfo(name = "start_date")
    private String startDate;

    @ColumnInfo(name = "end_date")
    private String endDate;

    @ColumnInfo(name = "notes")
    private String notes;


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

    public String getMalasCount() {
        return malasCount;
    }

    public void setMalasCount(String malasCount) {
        this.malasCount = malasCount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}

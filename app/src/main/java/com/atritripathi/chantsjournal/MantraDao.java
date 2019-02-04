package com.atritripathi.chantsjournal;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MantraDao {

    @Query("SELECT * FROM mantra_table")
    LiveData<List<Mantra>> getAllMantras();

    @Query("SELECT * FROM mantra_table WHERE id == :position")
    Mantra getMantra(int position);

    @Query("SELECT * FROM mantra_table WHERE mantra_name == :mantra")
    Mantra getMantra(String mantra);

    @Insert
    void insertMantra(Mantra mantra);

    @Query("UPDATE mantra_table SET completed_malas=:malas WHERE id=:mid")
    void updateMalas(int mid, int malas);

    @Query("UPDATE mantra_table SET completed_malas=:malas WHERE mantra_name=:mantra")
    void updateMalas(String mantra, int malas);

    @Delete
    void deleteMantra(Mantra mantra);

    @Query("DELETE FROM mantra_table")
    void deleteAllMantras();

}

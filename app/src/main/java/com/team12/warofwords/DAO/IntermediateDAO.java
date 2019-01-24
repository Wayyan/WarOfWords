package com.team12.warofwords.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.team12.warofwords.entity.Intermediate;

import java.util.List;

@Dao
public interface IntermediateDAO {
    @Query("SELECT * FROM intermediate")
    LiveData<List<Intermediate>> getAll();

    @Query("SELECT * FROM intermediate where levelID = :id")
    Intermediate findByID(int id);

    @Query("SELECT COUNT(*) from intermediate")
    int countIntermediates();


    @Query("SELECT SUM(diamonds) FROM intermediate")
    int countDiamonds();

    @Insert
    void insert(Intermediate intermediate);

    @Insert
    void insertAll(Intermediate... intermediates);

    @Update
    void update(Intermediate intermediate);

    @Delete
    void delete(Intermediate intermediate);
}

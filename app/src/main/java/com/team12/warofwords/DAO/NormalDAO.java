package com.team12.warofwords.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.team12.warofwords.entity.Normal;

import java.util.List;

@Dao
public interface NormalDAO {
    @Query("SELECT * FROM normal")
    LiveData<List<Normal>> getAll();

    @Query("SELECT * FROM normal where levelID = :id")
    Normal findByID(int id);

    @Query("SELECT SUM(diamonds) FROM normal")
    int countDiamonds();

    @Query("SELECT COUNT(*) from normal")
    int countNormals();

    @Insert
    void insert(Normal normal);

    @Update
    void update(Normal normal);

    @Insert
    void insertAll(Normal... normals);

    @Delete
    void delete(Normal normal);
}

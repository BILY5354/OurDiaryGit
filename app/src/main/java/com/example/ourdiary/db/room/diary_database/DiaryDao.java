package com.example.ourdiary.db.room.diary_database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DiaryDao {

    @Insert
    void insertDiaries(Diary... diaries);

    @Update
    void updateDiaries(Diary... diaries);

    @Delete
    void deleteDiaries(Diary... diaries);

    //删除到一无所有 慎用！
    @Query("DELETE FROM diary_table")
    void deleteAllDiaries();

    //这里一定要用升序，这样子rv的序号与id序号只相差1
    @Query("SELECT * FROM diary_table ORDER BY ID ASC")
    LiveData<List<Diary>>getAllDiariesLive();//得到全部的日记


    @Query("SELECT * FROM diary_table WHERE id=:diary_id")
    LiveData<Diary>getSpecificDiariesLive(int diary_id) ;//得到特定的日记

}

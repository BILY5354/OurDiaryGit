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

    //asc是升序，desc是降序
    @Query("SELECT * FROM diary_table ORDER BY ID DESC")
    LiveData<List<Diary>>getAllDiariesLive();//得到全部的日记

    //根据特定的diary id来找日记
    @Query("SELECT * FROM diary_table WHERE id=:diary_id")
    LiveData<Diary>getSpecificDiariesLive(int diary_id) ;//得到特定的日记

    /* Get specific diaries according to the topic id. */
    @Query("SELECT * FROM diary_table WHERE diaries_contacts_ref_topic_id=:ref_topic_id")
    LiveData<List<Diary>> getSpecificTopicIdDiary(int ref_topic_id);

}

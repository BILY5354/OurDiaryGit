package com.example.ourdiary.db.room.learning;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 *DAO必须是一个接口或抽象类。默认情况下，所有查询都必须在单独的线程上执行
 *@author home
 *@time 2021/4/9 21:28
*/
@Dao
public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)//如果一个新单词与列表中已经存在的单词完全相同，则选择的冲突策略会忽略它
    void insert(Word word);

    @Query("DELETE FROM word_table")
    void deleteAll();

    /**
     *LiveData,一种用于观察数据更新的lifecycle library类可以解决当数据发生变化时，
     * 在UI中显示更新后的数据
     *@author home
     *@time 2021/4/10 14:52
    */
    @Query("SELECT * FROM word_table ORDER BY word ASC")//升序排序单词列表
    LiveData<List<Word>> getAlphabetizedWords();//获取返回所有Word的List
}

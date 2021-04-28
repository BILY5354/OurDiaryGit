package com.example.ourdiary.db.room.learning;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 *创建名为Word类，类中每个属性代表表中的一列，Room最终使用这些来自数据库的属性
 * 去创造表和实例化对象
 *@author home
 *@time 2021/4/9 21:17
*/
@Entity(tableName = "word_table")//Entity代表SQLite的表
public class Word {

    @PrimaryKey
    @NonNull//指示参数、字段或方法返回值永远不能为空
    @ColumnInfo(name = "word")//如果希望列的名称与成员变量的名称不同，指定列的名称
    private String mWord;

    public Word(@NonNull String word) { this.mWord = word; }

    public String getWord() { return this.mWord; }
}

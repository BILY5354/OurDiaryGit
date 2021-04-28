package com.example.ourdiary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 *MqSqliteOpenHelper 工具类 单例模式（1.构造函数私有化 2.对外提供函数）
 * 用此函数式不能new的，只能通过getInstance方法使用它
 *@author home
 *@time 2021/3/26 22:43
*/
public class MySqliteOpenHelper extends SQLiteOpenHelper {

    //2.对外提供函数 单例模式
    private static SQLiteOpenHelper mInstance;

    public static synchronized SQLiteOpenHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySqliteOpenHelper(context,"OurDiary.db",null,1);//以后想要数据库省级 修改成2
        }
        return mInstance;
    }

    //1.构造函数私有化
    private MySqliteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 创建表 表数据初始化 数据库第一次创建的时候调用 第二次发现有了就不会重复创建了，也意味着该函数只会执行一次
     * 主键的要求：（1）_id标准的写法 id不标准（2）主键只能是Integer类型的
     *数据库初始化时用的
     *@author home
     *@time 2021/3/26 23:03
    */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table memo_entry(" +
                "_id integer primary key autoincrement," +
                "memo_content text)";//autoincrement实现主键下标自动增长
        sqLiteDatabase.execSQL(sql);
    }

    //数据库升级用的
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

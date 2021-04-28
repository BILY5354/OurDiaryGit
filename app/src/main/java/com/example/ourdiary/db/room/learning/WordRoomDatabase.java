package com.example.ourdiary.db.room.learning;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *1、您可以使用@Database将类注释为一个房间数据库，并使用注释参数声明属于数据库中的实体并设置版本号。
 * 每个实体都对应于一个将在数据库中创建的表。数据库迁移超出了这个代码实验室的范围，因此我们在这里将exportSchema设置为false，
 * 以避免出现构建警告。在实际的应用程序中，您应该考虑为Room设置一个用于导出模式的目录，这样您就可以将当前模式签入版本控制系统。
 *2、只有两种方式填充数据库，一是在下面创建数据库时预先填充，二是在Ac中获取用户输入的数据
 * @author home
 *@time 2021/4/9 21:56
*/
@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    public abstract WordDao wordDao();

    private static volatile WordRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //返回单列，并创建数据库
    static WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            .addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // 在后台填充数据库
                WordDao dao = INSTANCE.wordDao();
                dao.deleteAll();

                // 下面的都是初始数据
                Word word = new Word("Hello");
                dao.insert(word);
                word = new Word("World");
                dao.insert(word);
            });
        }
    };
}

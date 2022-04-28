package com.example.ourdiary.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 如果该数据库有多张表
 * 1.在@Database(entities = {填入类名.class}...)
 * 2.新建一个dao： public abstract NewDao NewDao();
 * 3.建立对应的Rep 仓库会有多个 一个表一个
 *  Repository(Application application) {
 *         AppDataBase db = AppDataBase.getMyBlogDatabase(application);
 *         mContactDao = db.contactDao();//得到对应的dao
 *     }
 * 4.建立对应的ViewModel 然后使用对应的Rep
 * */
@Database(entities = {Contact.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract ContactDao contactDao();

    private static AppDataBase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static synchronized AppDataBase getMyBlogDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class,
                    "MyBlogDatabase")
                    .addCallback(sRoomDatabaseCallback)
                    .build();
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
                ContactDao dao = INSTANCE.contactDao();

                // 下面的都是初始数据
                Contact contact = new Contact("预先塞入的1","13632079345");
                dao.insertContact(contact);
                contact = new Contact("预先塞入的2","13326845920");
                dao.insertContact(contact);
            });
        }
    };
}
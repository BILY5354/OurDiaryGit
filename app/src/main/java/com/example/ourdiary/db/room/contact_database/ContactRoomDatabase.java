package com.example.ourdiary.db.room.contact_database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Contact.class}, version = 1, exportSchema = false)
public abstract class ContactRoomDatabase extends RoomDatabase {

    public abstract ContactDao contactDao();

    private static ContactRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static synchronized ContactRoomDatabase getContactDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ContactRoomDatabase.class,
                    "contact_database").build();
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
                ContactDao dao = INSTANCE.contactDao();
                dao.deleteAllContacts();

                // 下面的都是初始数据
                Contact contact = new Contact("预先塞入的1","13632079345", 1);
                dao.insertContacts(contact);
                contact = new Contact("预先塞入的2","13326845920",1);
                dao.insertContacts(contact);
            });
        }
    };
}

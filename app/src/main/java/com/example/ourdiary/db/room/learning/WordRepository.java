package com.example.ourdiary.db.room.learning;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class WordRepository {
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;


    //注意，为了对worrepository进行单元测试，您必须删除应用程序
    //依赖。这增加了复杂性和更多的代码，并且这个示例与测试无关。
    //查看android-architecture-components存储库中的BasicSample
    //https://github.com/googlesamples
    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAlphabetizedWords();
    }

    // Room在一个单独的线程上执行所有的查询。
    //当数据发生变化时，观察到的LiveData将通知观察者。
    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    //必须在一个非ui线程上调用它，否则你的应用程序将抛出一个异常。房间可以确保
    //没有在主线程上做任何长时间运行的操作，阻塞UI。
    void insert (Word word) {
        WordRoomDatabase.databaseWriteExecutor.execute( () -> {
            mWordDao.insert(word);
        });
    }
}

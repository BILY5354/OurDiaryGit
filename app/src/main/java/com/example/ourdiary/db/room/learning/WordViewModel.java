package com.example.ourdiary.db.room.learning;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 *ViewModel的功能是在Repository和UI之间扮演数据交互中心的角色
 *@author home
 *@time 2021/4/10 11:50
*/
public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepository;

    private final LiveData<List<Word>> mAllWords;

    public WordViewModel(Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() { return  mAllWords; }

    public void insert(Word word) { mRepository.insert(word); }
}

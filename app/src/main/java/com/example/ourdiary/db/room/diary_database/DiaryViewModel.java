package com.example.ourdiary.db.room.diary_database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class DiaryViewModel extends AndroidViewModel {
    private DiaryRepository diaryRepository;

    public DiaryViewModel(@NonNull Application application) {
        super(application);
        diaryRepository = new DiaryRepository(application);
    }

    public LiveData<Diary> getSpecificDiary(int diary_id) {
        return diaryRepository.getSpecificDiariesLive(diary_id);
    }

    public LiveData<List<Diary>> getAllDiariesLive() {
        return diaryRepository.getAllDiariesLive();
    }

    public void insertDiaries(Diary... diaries) {
        diaryRepository.insetDiaries(diaries);
    }

    public void updateDiaries(Diary... diaries) { diaryRepository.updateDiaries(diaries); }

    public void deleteDiary(Diary... diaries) { diaryRepository.deleteDiaries(diaries);}

    public void deleteAllDiaries() {
        diaryRepository.deleteAllDiaries();
    }
}

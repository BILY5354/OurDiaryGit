package com.example.ourdiary.db.room.diary_database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

class DiaryRepository {
    private LiveData<List<Diary>> allDiariesLive;
    private DiaryDao diaryDao;

    DiaryRepository(Context context) {

        DiaryRoomDatabase diaryRoomDatabase = DiaryRoomDatabase.getDiaryDatabase(context.getApplicationContext());
        diaryDao = diaryRoomDatabase.getDiaryDao();
        allDiariesLive = diaryDao.getAllDiariesLive();
    }



    void insetDiaries(Diary... diaries) {
        new InsertAsyncTask(diaryDao).execute(diaries);
    }

    void updateDiaries(Diary... diaries) {
        new UpdateAsyncTask(diaryDao).execute(diaries);
    }

    void deleteDiaries(Diary... diaries) {
        new DeleteAsyncTask(diaryDao).execute(diaries);
    }

    void deleteAllDiaries(Diary... diaries) {
        new DeleteAllAsyncTask(diaryDao).execute();
    }

    public LiveData<Diary> getSpecificDiariesLive(int diary_id) {
        return diaryDao.getSpecificDiariesLive(diary_id);
    }

    public LiveData<List<Diary>> getAllDiariesLive() {
        return allDiariesLive;
    }

    public LiveData<List<Diary>> getSpecificTopicIdDiary(int ref_topic_id) {
        return diaryDao.getSpecificTopicIdDiary(ref_topic_id);
    }

    static class InsertAsyncTask extends AsyncTask<Diary, Void, Void> {
        private DiaryDao diaryDao;

        InsertAsyncTask(DiaryDao diaryDao) {
            this.diaryDao = diaryDao;
        }


        @Override
        protected Void doInBackground(Diary... diaries) {
            diaryDao.insertDiaries(diaries);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<Diary, Void, Void> {
        private DiaryDao diaryDao;

        UpdateAsyncTask(DiaryDao diaryDao) {
            this.diaryDao = diaryDao;
        }

        @Override
        protected Void doInBackground(Diary... diaries) {
            diaryDao.updateDiaries(diaries);
            return null;
        }
    }

    static class DeleteAsyncTask extends AsyncTask<Diary, Void, Void> {
        private DiaryDao diaryDao;

        DeleteAsyncTask(DiaryDao diaryDao) {
            this.diaryDao = diaryDao;
        }

        @Override
        protected Void doInBackground(Diary... diaries) {
            diaryDao.deleteDiaries(diaries);
            return null;
        }

    }

    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private DiaryDao diaryDao;

        DeleteAllAsyncTask(DiaryDao diaryDao) {
            this.diaryDao = diaryDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            diaryDao.deleteAllDiaries();
            return null;
        }

    }
}

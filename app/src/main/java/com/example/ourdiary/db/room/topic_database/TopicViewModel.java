package com.example.ourdiary.db.room.topic_database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class TopicViewModel extends AndroidViewModel{

    private TopicRepository topicRepository;

    public TopicViewModel(@NonNull Application application) {
        super(application);
        topicRepository = new TopicRepository(application);
    }

    public LiveData<List<TopicEntryAndOrder>> getAllTopicEntryAndOrder() {
        return topicRepository.getAllTopicEntryAndOrder();
    }
}

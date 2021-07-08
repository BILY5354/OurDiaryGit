package com.example.ourdiary.db.room.topic_database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 *
 *@author home
 *@time 2021/6/14 14:25
*/
public class TopicViewModel extends AndroidViewModel{

    private TopicRepository topicRepository;

    public TopicViewModel(@NonNull Application application) {
        super(application);
        topicRepository = new TopicRepository(application);
    }


    /** start TopicEntry */
    public void insertTopicEntry(TopicEntry topicEntry) {
        topicRepository.insertTopicEntry(topicEntry);
    }

    public void deleteOneTopicEntry(TopicEntry topicEntry) {
        topicRepository.deleteOneTopicEntry(topicEntry);
    }

    public void updateTopicEntry(TopicEntry topicEntry) {
        topicRepository.updateTopicEntry(topicEntry);
    }

    public LiveData<List<TopicEntry>> getAllTopicEntriesLive() {
        return topicRepository.getAllTopicEntriesLive();
    }

    public LiveData<List<TopicEntryAndOrder>> getAllTopicEntryAndOrder() {
        return topicRepository.getTopicEntryAndOrder();
    }
    /** end TopicEntry */


    /** start TopicOrder */
    public void insertTopicOrder(TopicOrder topicOrder) {
        topicRepository.insertTopicOrder(topicOrder);
    }

    public void deleteOneTopicOrder(TopicOrder topicOrder) {
        topicRepository.deleteOneTopicOrder(topicOrder);
    }

    public void updateTopicOrder(TopicOrder topicOrder) {
        topicRepository.updateTopicOrder(topicOrder);
    }
    /** end TopicOrder */



}

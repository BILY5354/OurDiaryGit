package com.example.ourdiary;

import android.app.Application;

import com.example.ourdiary.remote.data.model.Article;

import java.util.List;

public class ArticleData extends Application {

     private List<Article> articleList;

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}

package com.example.ourdiary.remote.data.model;

import java.util.List;

public class Article {

    private Integer id;
    private Integer isTop;
    private Integer type;
    private Integer categoryId;
    private String articleContent;
    private String articleCover;
    private String articleTitle;
    private String categoryName;
    private List<Tag> tagList;

    public Article() {
    }

    public Article(Integer id, Integer isTop, Integer type, Integer categoryId, String articleContent, String articleCover, String articleTitle, String categoryName, List<Tag> tagList) {
        this.id = id;
        this.isTop = isTop;
        this.type = type;
        this.categoryId = categoryId;
        this.articleContent = articleContent;
        this.articleCover = articleCover;
        this.articleTitle = articleTitle;
        this.categoryName = categoryName;
        this.tagList = tagList;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticleCover() {
        return articleCover;
    }

    public void setArticleCover(String articleCover) {
        this.articleCover = articleCover;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", isTop=" + isTop +
                ", type=" + type +
                ", categoryId=" + categoryId +
                ", articleContent='" + articleContent + '\'' +
                ", articleCover='" + articleCover + '\'' +
                ", articleTitle='" + articleTitle + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", tagList=" + tagList +
                '}';
    }
}

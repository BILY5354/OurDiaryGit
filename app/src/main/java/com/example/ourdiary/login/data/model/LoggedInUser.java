package com.example.ourdiary.login.data.model;

import java.util.Set;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    //用户id
    private Integer id;

    //头像
    private String avatar;

    //用户名 "shisan@163.com"
    private String username;

    //用户昵称 "管前端"
    private String nickname;

    //用户别名 将来用户显示的 "MING"
    private String intro;

    //用户喜欢的文章
    private Set articleLikeSet;

    //用于判断是否成功登录
    private boolean logStatue;

    public LoggedInUser() {
        logStatue = false;
    }

    public LoggedInUser(String intro) {
        this.intro = intro;
    }

    public LoggedInUser(Integer id, String avatar, String username, String nickname, String intro, Set articleLikeSet, boolean logStatue) {
        this.id = id;
        this.avatar = avatar;
        this.username = username;
        this.nickname = nickname;
        this.intro = intro;
        this.articleLikeSet = articleLikeSet;
        this.logStatue = logStatue;
    }


    public Integer getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getIntro() {
        return intro;
    }

    public Set getArticleLikeSet() {
        return articleLikeSet;
    }

    public boolean isLogStatue() {
        return logStatue;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public void setArticleLikeSet(Set articleLikeSet) {
        this.articleLikeSet = articleLikeSet;
    }

    public void setLogStatue(boolean logStatue) {
        this.logStatue = logStatue;
    }

    @Override
    public String toString() {
        return "LoggedInUser{" +
                "id=" + id +
                ", avatar='" + avatar + '\'' +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", intro='" + intro + '\'' +
                ", articleLikeSet=" + articleLikeSet +
                ", logStatue=" + logStatue +
                '}';
    }

    /*
    * "articleLikeSet": [
            56,
            58
        ],
        "avatar": "data:头像.png;base64,",
        "commentLikeSet": [],
        "email": "shisan@163.com",
        "id": 1,
        "intro": "MING",
        "ipAddress": "0:0:0:0:0:0:0:1",
        "ipSource": "",
        "lastLoginTime": "2022-04-26T10:29:48.551",
        "loginType": 1,
        "nickname": "管前端",
        "userInfoId": 1,
        "username": "shisan@163.com"*/
}
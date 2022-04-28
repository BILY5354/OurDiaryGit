package com.example.ourdiary.remote.data;

import com.example.ourdiary.remote.data.model.Article;
import com.example.ourdiary.remote.data.model.LoggedInUser;
import com.example.ourdiary.remote.data.model.Tag;

import java.util.List;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;
    private List<Article> articleList = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    /**START 登录请求*/
    public LoggedInUser getLoggedInUser() {
        return user;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    //获取用户别名 MING
    public String getIntro() {
        return user.getIntro();
    }

    //获取用户名（邮箱）
    public String getUsername() {
        return user.getUsername();
    }

    //获取用户图像
    public String getAvatar() {
        return user.getAvatar();
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(String username, String password) {
        // handle login
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }
    /**END 登录请求*/

    /**START 获取文章*/
    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public Result<List<Article>> getArticleList() {
        Result<List<Article>> result = dataSource.getArticleList();
        if (result instanceof Result.Success) {
            setArticleList(((Result.Success<List<Article>>)result).getData());
        }
        return result;
    }
    //获取是否有数据
    public boolean isEmpty() {
        return articleList.isEmpty();
    }

    //获取文章篇数
    public Integer getArticleNu() {
        return articleList.size();
    }

    //获取一篇文章
    public Article getArticle(int i) {
        return articleList.get(i);
    }

    //获取一篇文章的标签
    public List<Tag> getArticleTag(int i) {
        return articleList.get(i).getTagList();
    }

    /**END 获取文章*/

}
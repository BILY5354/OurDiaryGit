package com.example.ourdiary.remote.data;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.ourdiary.remote.data.model.Article;
import com.example.ourdiary.remote.data.model.LoggedInUser;
import com.example.ourdiary.remote.data.model.Tag;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    /**
     * START 登录请求
     */
    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser loggedUser = new LoggedInUser();
            /*Log.d("test", loggedUser.toString());*/
            loginMyBlog(username, password, loggedUser);
            Thread.sleep(1000);//等待2秒与服务器通信
            /*Log.d("test", loggedUser.toString());*/
            if (loggedUser.isLogStatue()) {
                /*Log.d("test", "在if语句中判断登录成功");*/

               /* *//**
                 * 登录判断成功后便开始获取文章数据*//*
                List<Article> articleList = new ArrayList<>();
                getMyBlogArticles(articleList);
                Thread.sleep(2000);//等待2秒与服务器通信
                Log.d("test", "睡眠2秒后 list长度为" + articleList.size());*/
                return new Result.Success<>(loggedUser);
            } else {
                /*Log.d("test", "在if语句中失败");*/
                return new Result.Failed("用户名密码错误");
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }
    /**END 登录请求*/

    /**
     * START 获取文章
     */
    public Result<List<Article>> getArticleList() {
        try {
            //登录判断成功后便开始获取文章数据
            List<Article> articleList = new ArrayList<>();
            getMyBlogArticles(articleList);
            Thread.sleep(2000);//等待2秒与服务器通信
            /*Log.d("test", "睡眠2秒后 list长度为" + articleList.size());*/
            if (articleList.isEmpty()) {
                return new Result.Failed("获取文章失败，请稍候再试");
            } else {
                return new Result.Success(articleList);
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error getting articles", e));
        }
    }

    /**
     * END 获取文章
     */

    public void logout() {
        // TODO: revoke authentication
    }

    /**
     * START 登录请求
     */
    //post异步请求
    private void loginMyBlog(String usernames, String passwords, LoggedInUser userAuth) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();//创建Http客户端
                //Form表单格式的参数传递
                FormBody params = new FormBody
                        .Builder()
                        .add("username", usernames)
                        .add("password", passwords)
                        .build();
                Request request = new Request
                        .Builder()
                        .url("http://192.168.56.1:8080/login")//***.***.**.***为本机IP，xxxx为端口，/  /  为访问的接口后缀
                        .post(params)
                        .build();//创建Http请求
                //Log.d("test", request + "");

                try {
                    Response response = client.newCall(request).execute();//执行发送的指令
                    //Log.d("test", "请求成功，检验登录信息中");
                    final String responseData = response.body().string();//获取返回的结果 一定要写这一行
                    JSONObject jsonObject = JSONObject
                            .parseObject(responseData);//获取返回的结果变成json对象
                    String code = jsonObject.getString("code");
                    if (code.equals("20000")) {
                        //解析json数据
                        JSONObject dataObject = JSONObject.parseObject(jsonObject.getString("data"));
                        userAuth.setId(dataObject.getInteger("id"));
                        userAuth.setIntro(dataObject.getString("intro"));
                        userAuth.setNickname(dataObject.getString("nickname"));
                        userAuth.setAvatar(dataObject.getString("avatar"));
                        userAuth.setUsername(dataObject.getString("username"));
                        userAuth.setLogStatue(true);
                        Log.d("test", "登录成功" + userAuth);
                    } else if (code.equals("51000")) {
                        Log.d("test", "用户名或密码不正确");
                        userAuth.setLogStatue(false);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("test", "网络错误");
                }
            }
        }).start();
    }
    /**END 登录请求*/

    /**
     * START 获取文章
     */
    private void getMyBlogArticles(List<Article> articleList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();//创建Http客户端
                Request request = new Request
                        .Builder()
                        .url("http://192.168.56.1:8080/articles")
                        .get()
                        .build();

                try {
                    Response response = client.newCall(request).execute();//执行发送的指令
                    /*Log.d("test", "请求成功，获取文章中");*/
                    final String responseData = response.body().string();//获取返回的结果 一定要写这一行
                    JSONObject jsonObject = JSONObject
                            .parseObject(responseData);//获取返回的结果变成json对象
                    String code = jsonObject.getString("code");
                    if (code.equals("20000")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        Log.d("test", jsonArray.size() + "");
                        for (int i = 0; i < jsonArray.size(); i++) {
                            List<Tag> tagList = new ArrayList<>();
                            JSONObject temp = jsonArray.getJSONObject(i);

                            Integer id = temp.getInteger("id");
                            Integer isTop = temp.getInteger("isTop");
                            Integer type = temp.getInteger("type");
                            Integer categoryId = temp.getInteger("categoryId");
                            String articleContent = temp.getString("articleContent");
                            String articleCover = null;
                            String articleTitle = temp.getString("articleTitle");
                            String categoryName = temp.getString("categoryName");
                            JSONArray tagJsonArr = temp.getJSONArray("tagDTOList");
                            Log.d("test", "解析json前" + id + articleTitle);
                            for (int j = 0; j < tagJsonArr.size(); j++) {
                                JSONObject tempTag = tagJsonArr.getJSONObject(j);
                                Integer tagId = tempTag.getInteger("id");
                                String tagName = tempTag.getString("tagName");
                                Tag tag = new Tag(tagId, tagName);
                                tagList.add(tag);
                            }

                            Article article = new Article(id, isTop, type, categoryId, articleContent, articleCover, articleTitle, categoryName, tagList);
                            /*Log.d("test", "新建article对象" + article.getId() + article.getArticleTitle());*/
                            articleList.add(i, article);
                        }
                        /*Log.d("test", "转换后数组长度为" + articleList.size());*/
                        /*for (int i = 0; i < articleList.size(); i++) {
                            Log.d("test", articleList.get(i).getId()+articleList.get(i).getArticleTitle());
                        }*/
                    }
                    /*Log.d("test", responseData);*/
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("test", "网络错误");
                }
            }
        }).start();
    }
    /**END 获取文章*/

}
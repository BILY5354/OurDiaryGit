package com.example.ourdiary.login.data;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.ourdiary.login.data.model.LoggedInUser;


import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser loggedUser = new LoggedInUser();
            Log.d("test",loggedUser.toString());
            loginMyBlog(username, password, loggedUser);
            Thread.sleep(2000);
            Log.d("test",loggedUser.toString());
            if (loggedUser.isLogStatue()) {
                Log.d("test","在if语句中判断登录成功");
                return new Result.Success<>(loggedUser);
            } else {
                Log.d("test","在if语句中失败");
                return new Result.Failed("用户名密码错误");
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }


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
                    if (code.equals("20000")){
                        //解析json数据
                        JSONObject dataObject = JSONObject.parseObject(jsonObject.getString("data"));
                        userAuth.setId(dataObject.getInteger("id"));
                        userAuth.setIntro(dataObject.getString("intro"));
                        userAuth.setLogStatue(true);
                        Log.d("test", "登录成功"+userAuth);
                    }else if (code.equals("51000")){
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
}
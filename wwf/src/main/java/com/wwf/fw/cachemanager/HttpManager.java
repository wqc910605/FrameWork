package com.wwf.fw.cachemanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.wwf.fw.gloab.Oschina;
import com.wwf.fw.utils.Fields;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sy_heima on 2016/8/15.
 */
public class HttpManager {

    private HttpManager() {

    }

    private static HttpManager sHttpManager = new HttpManager();

    public static HttpManager getInstance() {
        return sHttpManager;
    }

    //去网络获取数据
    public String dataGet(String url, HashMap<String, String> params, String cookie) {

        try {
            OkHttpClient okHttpClient = new OkHttpClient();

          /*  Request request = new Request.Builder()
                    .addHeader("cookie","")
                    .url(url)
                    .build();*/

            Request.Builder builder = new Request.Builder();
            if (!TextUtils.isEmpty(cookie)) {

                builder.addHeader("cookie", cookie);


            }

            //动态添加
            StringBuffer urlParams = new StringBuffer();

           /* for (String name : params.keySet()) {
                urlParams.append("");
            }*/

            if (params != null) {


                //如果参数大于0我们先添加
                if (params.keySet().size() > 0) {
                    urlParams.append("?");
                }
                for (String name : params.keySet()) {
                    urlParams.append(name);
                    urlParams.append("=");
                    urlParams.append(params.get(name));
                    urlParams.append("&");
                }
                if (params.keySet().size() > 0) {
                    urlParams.deleteCharAt(urlParams.length() - 1);
                }

            }

            builder.url(url + urlParams.toString());
            System.out.println("这个新的组成的地址:" + url + urlParams.toString());


            Request request = builder.build();

            Response response = okHttpClient.newCall(request).execute();

            //获取头
            Headers headers = response.headers();
            for (int i = 0; i < headers.size(); i++) {
                String name = headers.name(i);
                String value = headers.get(name);
                System.out.println("name:" + name + "---->value" + value);
                //Content-Type---->valuetext/html;charset=UTF-8
                //判断是否网页
            }

            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
    } //去网络获取数据


    //统一的post请求
    public String postFromData(String url, HashMap<String, String> params, String cookieValue) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
            Request.Builder builder = new Request.Builder();
            if (!TextUtils.isEmpty(cookieValue)) {
                builder.addHeader("cookie", cookieValue);
            }
            builder.url(url);
            FormBody.Builder formBuilder = new FormBody.Builder();
            if (params != null && params.keySet() != null && params.keySet().size() > 0) {
                for (String name : params.keySet()) {
                    formBuilder.add(name, params.get(name));
                }

            }
            builder.post(formBuilder.build());
            Request request = builder.build();
            Response response = okHttpClient.newCall(request).execute();

            /*保存cookie*/

            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //统一的post请求
    public String postLoginData(String url, HashMap<String, String> params) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
            Request.Builder builder = new Request.Builder();
            builder.url(url);
            FormBody.Builder formBuilder = new FormBody.Builder();
            if (params != null && params.keySet() != null && params.keySet().size() > 0) {
                for (String name : params.keySet()) {
                    formBuilder.add(name, params.get(name));
                }

            }
            builder.post(formBuilder.build());
            Request request = builder.build();
            Response response = okHttpClient.newCall(request).execute();

            /*保存cookie*/
            SharedPreferences sharedPreferences = Oschina.context.getSharedPreferences(Fields.Login.FILE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(Fields.Login.COOKIE_VALUE, response.header("Set-Cookie"));
            edit.commit();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}

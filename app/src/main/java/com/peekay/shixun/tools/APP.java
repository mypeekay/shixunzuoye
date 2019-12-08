package com.peekay.shixun.tools;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import okhttp3.OkHttpClient;

public class APP extends Application {
    public static final String BASE_URI = "http://ven6.com/";

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient.Builder builder=new OkHttpClient.Builder().addInterceptor(new LoggerInterceptor("sss666",true));
        OkHttpUtils.initClient(builder.build());
    }
}

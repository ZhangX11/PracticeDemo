package com.ctyon.practicedemo;

import android.app.Application;

import com.ctyon.practicedemo.retrofit.RetrofitUtil;
import com.ctyon.practicedemo.retrofit.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zx on 2017/6/23.
 */

public class MyApplication extends Application {

    public static RetrofitUtil retrofitUtil;

    @Override
    public void onCreate() {
        super.onCreate();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitUtil = retrofit.create(RetrofitUtil.class);
    }
}

package com.ctyon.practicedemo.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zx on 2017/6/23.
 */

public interface RetrofitUtil {
    //获取第一页数据
    @GET(URL.PAGE1)
    Call<QuiShiEntity> getData();
    //分页获取数据
    @GET(URL.QUERY_BY_PAGE)
    Call<QuiShiEntity> queryDataByPage(@Query("page") int page);
}

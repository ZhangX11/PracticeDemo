package com.ctyon.practicedemo.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ctyon.practicedemo.MyApplication;
import com.ctyon.practicedemo.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zx
 * On 2018/8/3
 */
public class MyRetrofitActivity extends AppCompatActivity implements View.OnClickListener {


    private List<QuiShiEntity.ItemsBean> data = new ArrayList<>();
    private MyAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myretrofit);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //设置是否有返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initData();
        adapter = new MyAdapter(data,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("Retrofit");
    }

    private void initData() {
        /*MyApplication.retrofitUtil.getData().enqueue(new Callback<QuiShiEntity>() {
            @Override
            public void onResponse(Call<QuiShiEntity> call, Response<QuiShiEntity> response) {
                data.addAll(response.body().getItems());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<QuiShiEntity> call, Throwable t) {

            }
        });*/

        MyApplication.retrofitUtil.queryData(3).enqueue(new Callback<QuiShiEntity>() {
            @Override
            public void onResponse(Call<QuiShiEntity> call, Response<QuiShiEntity> response) {
                data.addAll(response.body().getItems());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<QuiShiEntity> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}

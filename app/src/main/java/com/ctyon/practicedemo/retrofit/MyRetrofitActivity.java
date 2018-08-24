package com.ctyon.practicedemo.retrofit;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
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
public class MyRetrofitActivity extends AppCompatActivity implements View.OnClickListener,
        BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemLongClickListener,
        BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {


    private List<QuiShiEntity.ItemsBean> data = new ArrayList<>();
    private MyAdapter adapter;
    private Toolbar toolbar;
    private QuickAdapter quickAdapter;
    private int currentPage = 1;//当前页，默认为1
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myretrofit);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(this);
        setSupportActionBar(toolbar);
        //设置是否有返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        initData();
        //继承RecyclerView.Adapter
        //adapter = new MyAdapter(data,this);
        //继承BaseQuickAdapter,传入item布局ID和数据源
        quickAdapter = new QuickAdapter(R.layout.item,data);
        quickAdapter.setOnItemClickListener(this);
        quickAdapter.setOnItemLongClickListener(this);
        quickAdapter.setOnItemChildClickListener(this);
        quickAdapter.setOnLoadMoreListener(this,recyclerView);
        recyclerView.setAdapter(quickAdapter);
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

        MyApplication.retrofitUtil.queryDataByPage(currentPage).enqueue(new Callback<QuiShiEntity>() {
            @Override
            public void onResponse(Call<QuiShiEntity> call, Response<QuiShiEntity> response) {
                data.addAll(response.body().getItems());
                quickAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<QuiShiEntity> call, Throwable t) {
                View view = View.inflate(MyRetrofitActivity.this, R.layout.empty_view, null);
                TextView tvReload = view.findViewById(R.id.tv_empty);
                tvReload.setOnClickListener(MyRetrofitActivity.this);
                quickAdapter.setEmptyView(view);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_empty:
                swipeRefreshLayout.setRefreshing(true);
                refreshData();
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Toast.makeText(this, "position"+position+"click", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        Toast.makeText(this, "position"+position+"longClick", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.tv_up_vote){
            Toast.makeText(this, "已赞", Toast.LENGTH_SHORT).show();
        }else if (view.getId() == R.id.tv_down_vote){
            Toast.makeText(this, "已踩", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        loadMore();
    }

    private void loadMore() {
        currentPage = currentPage+1;
        MyApplication.retrofitUtil.queryDataByPage(currentPage).enqueue(new Callback<QuiShiEntity>() {
            @Override
            public void onResponse(Call<QuiShiEntity> call, Response<QuiShiEntity> response) {
                List<QuiShiEntity.ItemsBean> newData = response.body().getItems();
                data.addAll(newData);
                quickAdapter.loadMoreComplete();
            }

            @Override
            public void onFailure(Call<QuiShiEntity> call, Throwable t) {
                quickAdapter.loadMoreFail();
            }
        });
    }

    private void refreshData(){
        MyApplication.retrofitUtil.getData().enqueue(new Callback<QuiShiEntity>() {
            @Override
            public void onResponse(Call<QuiShiEntity> call, Response<QuiShiEntity> response) {
                data.clear();
                data.addAll(response.body().getItems());
                quickAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<QuiShiEntity> call, Throwable t) {
                Toast.makeText(MyRetrofitActivity.this, "刷新失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        refreshData();
    }
}

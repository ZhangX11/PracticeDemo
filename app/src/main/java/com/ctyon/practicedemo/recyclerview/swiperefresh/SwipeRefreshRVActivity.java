package com.ctyon.practicedemo.recyclerview.swiperefresh;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.ctyon.practicedemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zx
 * On 2018/8/4
 * SwipeRefreshLayout实现RecyclerView下拉刷新上拉加载
 */
public class SwipeRefreshRVActivity extends AppCompatActivity implements  View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, SwipeRefreshAdapter.OnItemClickListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private List<String> data;
    private Handler handler;
    private SwipeRefreshAdapter refreshAdapter;
    private LinearLayoutManager layoutManager;
    private boolean isLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh);
        data = new ArrayList<>();
        handler = new Handler();
        initView();
        initData();
        refreshAdapter = new SwipeRefreshAdapter(this,data);
        refreshAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(refreshAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("SwipeRefreshRV");
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //设置是否有返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(Color.YELLOW,Color.BLUE,Color.GREEN,Color.GRAY);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //线性布局
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPositon = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPositon+1 == refreshAdapter.getItemCount()){
                    if (!isLoading){
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loadData();
                                isLoading = false;
                            }
                        },3000);
                    }
                }
            }
        });
    }

    private void initData() {
        String text = "data";
        for (int i = 0; i < 20; i++) {
            data.add(text+i);
        }
    }

    /***
     * 刷新的数据
     */
    private void refreshData() {
        data.clear();
        String text = "refresh data";
        for (int i = 0; i < 10; i++) {
            data.add(text+i);
        }
        initData();
        updateView();
    }

    /***
     * 加载的数据
     */
    private void loadData() {
        String text = "load data";
        for (int i = 0; i < 10; i++) {
            data.add(text+i);
        }
        updateView();
    }
    //刷新视图
    private void updateView(){
        refreshAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this,"刷新成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshData();
            }
        },3000);
    }

    private int getScollYDistance(){
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisibleChildView = layoutManager.findViewByPosition(position);
        int firstVisiableChildViewTop = firstVisibleChildView.getTop();
        int itemHeight = firstVisibleChildView.getHeight();
        //第一个可见的item*item高度-最顶端位置
        return (position) * itemHeight - firstVisiableChildViewTop;
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this,"position"+position+"\nclick",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(this,"position"+position+"\nlong click",Toast.LENGTH_SHORT).show();
    }
}

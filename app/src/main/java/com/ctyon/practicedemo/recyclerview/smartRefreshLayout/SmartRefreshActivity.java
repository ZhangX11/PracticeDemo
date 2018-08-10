package com.ctyon.practicedemo.recyclerview.smartRefreshLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ctyon.practicedemo.R;
import com.scwang.smartrefresh.header.DropboxHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zx
 * On 2018/8/7
 */
public class SmartRefreshActivity extends AppCompatActivity implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {

    private Toolbar toolbar;
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;
    private List<String> data;
    private SmartRefreshAdapter refreshAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smartrefresh);
        data = new ArrayList<>();
        initView();
        initData();
        refreshAdapter = new SmartRefreshAdapter(this,data);
        recyclerView.setAdapter(refreshAdapter);
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
    }

    /***
     * 加载的数据
     */
    private void loadData() {
        String text = "load data";
        for (int i = 0; i < 10; i++) {
            data.add(text+i);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("SmartRefresh");
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //设置是否有返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(this);
        smartRefreshLayout = findViewById(R.id.smart_refresh);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadMoreListener(this);
        //设置气球快递header样式
        //smartRefreshLayout.setRefreshHeader(new DeliveryHeader(this));
        //设置掉落盒子header样式
        smartRefreshLayout.setRefreshHeader(new DropboxHeader(this));
        //
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        recyclerView = findViewById(R.id.recycler_view);
        //线性布局
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        refreshData();
        refreshLayout.finishRefresh(2000);
        refreshAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        loadData();
        refreshLayout.finishLoadMore(2000);
        refreshAdapter.notifyDataSetChanged();
    }
}

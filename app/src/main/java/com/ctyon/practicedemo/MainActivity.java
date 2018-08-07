package com.ctyon.practicedemo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ctyon.practicedemo.recyclerview.smartRefreshLayout.SmartRefreshList;
import com.ctyon.practicedemo.recyclerview.swiperefresh.SwipeRefreshRVActivity;
import com.ctyon.practicedemo.retrofit.MyRetrofitActivity;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends ListActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private String[] mTitles = new String[]{
            "Retrofit的使用",
            "RecyclerView+SwipeRefreshLayout下拉刷新上拉加载",
            "SmartRefreshLayout列表"
    };

    private Class[] mActivities = new Class[]{
            MyRetrofitActivity.class,
            SwipeRefreshRVActivity.class,
            SmartRefreshList.class

    };


    private void initView() {
        setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mTitles));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        startActivity(new Intent(this, mActivities[position]));
    }


    private static Boolean isExit = false;
    private void exitSystem() {
        // 准备退出
        ScheduledExecutorService service;
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            service = new ScheduledThreadPoolExecutor(2);
            service.schedule(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            },2, TimeUnit.SECONDS);
        }else {
            finish();
            System.exit(0);
        }
    }

    /**
     *再按一次退出
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitSystem();
        }
        return false;
    }
}

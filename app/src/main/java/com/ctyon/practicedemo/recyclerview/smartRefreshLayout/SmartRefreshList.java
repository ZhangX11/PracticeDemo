package com.ctyon.practicedemo.recyclerview.smartRefreshLayout;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by zx
 * On 2018/8/7
 */
public class SmartRefreshList extends ListActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private String[] mTitles = new String[]{
            "SmartRefresh刷新",
    };

    private Class[] mActivities = new Class[]{
            SmartRefreshActivity.class,

    };


    private void initView() {
        setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mTitles));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        startActivity(new Intent(this, mActivities[position]));
    }
}

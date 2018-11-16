package com.ctyon.practicedemo.recyclerview.multilayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ctyon.practicedemo.R;

import java.util.ArrayList;
import java.util.List;

/***
 * Created by v_zhangxin12 on 2018/11/8.
 */
public class MultiLayoutActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MultiLayoutAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_layout);
        initView();
        initData();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler_view);
    }

    private void initData() {
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            titles.add("标题"+1);
        }
        adapter = new MultiLayoutAdapter(titles,this);
        //网格布局
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                switch (viewType){
                    case 1://title
                        return 6;
                    case 2://big item
                        return 3;
                    case 3://small item
                        return 2;
                    default:
                        return 6;
                }
            }
        });
    }
}

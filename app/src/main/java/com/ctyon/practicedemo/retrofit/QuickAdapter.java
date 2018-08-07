package com.ctyon.practicedemo.retrofit;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by zx on 2017/7/1.
 */

public class QuickAdapter extends BaseQuickAdapter<QuiShiEntity.ItemsBean,BaseViewHolder> {

    public QuickAdapter(@LayoutRes int layoutResId, @Nullable List<QuiShiEntity.ItemsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuiShiEntity.ItemsBean item) {

    }
}

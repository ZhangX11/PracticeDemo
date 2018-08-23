package com.ctyon.practicedemo.retrofit;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctyon.practicedemo.R;

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
        helper.setText(R.id.tv_content,item.getContent());
        QuiShiEntity.ItemsBean.UserBean user = item.getUser();
        if (user == null){

            helper.setText(R.id.tv_author,"佚名");
        }else {
            helper.setText(R.id.tv_author,user.getLogin());
        }
        helper.setText(R.id.tv_up_vote,"赞"+item.getVotes().getUp()).addOnClickListener(R.id.tv_up_vote);
        helper.setText(R.id.tv_down_vote,"踩"+item.getVotes().getDown()).addOnClickListener(R.id.tv_down_vote);
    }
}

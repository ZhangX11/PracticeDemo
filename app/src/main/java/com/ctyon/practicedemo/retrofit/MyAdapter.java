package com.ctyon.practicedemo.retrofit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ctyon.practicedemo.R;

import java.util.List;

/**
 * Created by zx on 2017/6/23.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<QuiShiEntity.ItemsBean> data;
    private Context context;

    public MyAdapter(List<QuiShiEntity.ItemsBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_content.setText(data.get(position).getContent());
        QuiShiEntity.ItemsBean.UserBean user = data.get(position).getUser();
        if (user == null){

            holder.tv_author.setText("佚名");
        }else {
            holder.tv_author.setText(user.getLogin());
        }
        holder.tv_up.setText("赞"+data.get(position).getVotes().getUp());
        holder.tv_down.setText("踩"+data.get(position).getVotes().getDown());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_content;
        private final TextView tv_author;
        private final TextView tv_up;
        private final TextView tv_down;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_author = (TextView) itemView.findViewById(R.id.tv_author);
            tv_up = (TextView) itemView.findViewById(R.id.tv_up_vote);
            tv_down = (TextView) itemView.findViewById(R.id.tv_down_vote);
        }
    }
}

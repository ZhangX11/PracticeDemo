package com.ctyon.practicedemo.recyclerview.smartRefreshLayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ctyon.practicedemo.R;

import java.util.List;

/**
 * Created by zx
 * On 2018/8/4
 */
public class SmartRefreshAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<String> data;

    public SmartRefreshAdapter(Context mContext, List<String> data) {
        this.mContext = mContext;
        this.data = data;
    }

    interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_swiperefresh,parent,
                    false);
            return new MyItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyItemViewHolder){
            ((MyItemViewHolder) holder).textView.setText(data.get(position));
            if (onItemClickListener != null){
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = holder.getLayoutPosition();
                            onItemClickListener.onItemClick(holder.itemView,position);
                        }
                    });

                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            int position = holder.getLayoutPosition();
                            onItemClickListener.onItemLongClick(holder.itemView,position);
                            return false;
                        }
                    });
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    //item
    public class MyItemViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        public MyItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_text_item);
        }
    }
}

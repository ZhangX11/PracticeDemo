package com.ctyon.practicedemo.recyclerview.swiperefresh;

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
public class SwipeRefreshAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<String> data;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    public SwipeRefreshAdapter(Context mContext, List<String> data) {
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
    public int getItemViewType(int position) {
        if (position+1 == getItemCount()){
            return TYPE_FOOTER;
        }else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER){
            View view = LayoutInflater.from(mContext).inflate(R.layout.loading,parent,false);
            return new MyFooterViewHolder(view);
        }else if (viewType == TYPE_ITEM){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_swiperefresh,parent,
                    false);
            return new MyItemViewHolder(view);

        }
        return null;
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
        return data.size()== 0?0:data.size()+1;
    }
    //item
    public class MyItemViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        public MyItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_text_item);
        }
    }
    //footer
    class MyFooterViewHolder extends RecyclerView.ViewHolder{

        public MyFooterViewHolder(View itemView) {
            super(itemView);

        }
    }
}

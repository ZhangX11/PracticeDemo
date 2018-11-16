package com.ctyon.practicedemo.recyclerview.multilayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ctyon.practicedemo.R;

import java.util.List;

/***
 * Created by v_zhangxin12 on 2018/11/8.
 */
public class MultiLayoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TITLE_TYPE = 1;
    private static final int BIG_ITEM_TYPE = 2;
    private static final int SMALL_ITEM_TYPE = 3;
    private List<String> titels;
    private Context mContext;

    public MultiLayoutAdapter(List<String> titels, Context mContext) {
        this.titels = titels;
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position ==8|| position ==17||position ==23){
            return TITLE_TYPE;
        }else if ( (1<=position&&position<= 4)|| (9<=position&&position<= 10)
                ||(18<=position&&position<= 19)||(24<=position&&position<= 25)){

            return BIG_ITEM_TYPE;
        }
        return SMALL_ITEM_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == BIG_ITEM_TYPE){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_big,parent,false);
            return new BigItemViewHolder(view);
        }else if (viewType == SMALL_ITEM_TYPE){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_small,parent,false);
            return new SmallItemViewHolder(view);

        }else if (viewType == TITLE_TYPE){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_title,parent,
                    false);
            return new TitleViewHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BigItemViewHolder){
            ((BigItemViewHolder) holder).title.setText(titels.get(position));
        }
        if (holder instanceof  SmallItemViewHolder){
            ((SmallItemViewHolder) holder).title.setText(titels.get(position));
        }
        if (holder instanceof TitleViewHolder){
            ((TitleViewHolder) holder).sectionTitle.setText(titels.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return titels.size();
    }

    //item big
    public class BigItemViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView subTitle;
        LinearLayout linearLayout;
        public BigItemViewHolder(View itemView) {
            super(itemView);
            title =  itemView.findViewById(R.id.tv_title);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
    //item small
    public class SmallItemViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;
        public SmallItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
        }
    }

    //title
    class TitleViewHolder extends RecyclerView.ViewHolder{
        TextView sectionTitle;
        public TitleViewHolder(View itemView) {
            super(itemView);
            sectionTitle = (TextView) itemView.findViewById(R.id.section_title);
        }
    }
}

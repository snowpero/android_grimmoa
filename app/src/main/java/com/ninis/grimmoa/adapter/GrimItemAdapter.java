package com.ninis.grimmoa.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ninis.grimmoa.R;
import com.ninis.grimmoa.data.ImgPostData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gypark on 2017. 3. 21..
 */

public abstract class GrimItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private List<ImgPostData> items = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grim_item_row_card, parent, false);
        return new GrimViewHolder(root);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int view_type = getItemViewType(position);

        if( VIEW_TYPE_ITEM == view_type ) {
            GrimViewHolder viewHolder = (GrimViewHolder) holder;

            ImgPostData itemData = items.get(position);
            if (itemData != null) {
                viewHolder.setData(itemData);
            }
        } else {
            MoreLoadingViewHolder viewHolder = (MoreLoadingViewHolder) holder;
            viewHolder.progressBar.setIndeterminate(true);
        }

        //check for last item
        if ((position >= getItemCount() - 1))
            load();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) != null ? VIEW_TYPE_ITEM : VIEW_TYPE_LOADING;
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void setItems(List<ImgPostData> items) {
        if( !this.items.isEmpty() )
            this.items.clear();

        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void addItems(List<ImgPostData> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public abstract void load();
}

package com.ninis.grimmoa.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.ninis.grimmoa.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gypark on 2017. 3. 28..
 */

public class MoreLoadingViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.progressbar_more)
    ProgressBar progressBar;

    public MoreLoadingViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}

package com.ninis.grimmoa.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninis.grimmoa.R;
import com.ninis.grimmoa.data.ImgPostData;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gypark on 2017. 3. 21..
 */

public class GrimViewHolder extends RecyclerView.ViewHolder {

    private final String SITE_PPOMPPU = "ppomppu";
    private final String SITE_SLR = "slrclub";
    private final String SITE_POPCO = "popco";

    @BindView(R.id.iv_post_thumb)
    ImageView ivThumb;
    @BindView(R.id.tv_post_title)
    TextView tvTitle;
    @BindView(R.id.tv_post_user_id)
    TextView tvUserID;
    @BindView(R.id.tv_post_date)
    TextView tvDate;
//    @BindView(R.id.tv_site_info)
//    TextView tvSiteInfo;
    @BindView(R.id.iv_post_site_icon)
    ImageView ivSiteIcon;
    @BindView(R.id.iv_post_user_img)
    ImageView ivUserImg;

    public GrimViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setData(ImgPostData itemData) {
        if( itemData != null ) {
            if( !TextUtils.isEmpty(itemData.getPostThumb()) ) {
                Picasso.with(itemView.getContext())
                        .load(itemData.getPostThumb())
                        .fit()
                        .placeholder(R.drawable.default_placeholder)
                        .into(ivThumb);
            } else {
                ivThumb.setImageResource(R.drawable.ic_todayhumor);
            }

            tvTitle.setText(itemData.getPostTitle());
            tvDate.setText(itemData.getPostDate());

            // User ID
            if(URLUtil.isValidUrl(itemData.getUserId())) {
                Picasso.with(itemView.getContext()).load(itemData.getUserId()).into(ivUserImg);
                tvUserID.setText("");
            } else {
                tvUserID.setText(itemData.getUserId());
                ivUserImg.setImageDrawable(null);
            }

            String siteInfo = itemData.getSiteInfo();
            if( SITE_PPOMPPU.equalsIgnoreCase(siteInfo) ) {
                ivSiteIcon.setImageResource(R.drawable.ic_ppomppu);
            } else if( SITE_SLR.equalsIgnoreCase(siteInfo) ) {
                ivSiteIcon.setImageResource(R.drawable.ic_slrclub);
            } else if( SITE_POPCO.equalsIgnoreCase(siteInfo) ) {
                ivSiteIcon.setImageResource(R.drawable.ic_popco);
            } else {
                ivSiteIcon.setImageDrawable(null);
            }

            itemView.setTag(itemData.getPostLink());
        }
    }
}

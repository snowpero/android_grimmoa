package com.ninis.grimmoa.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gypark on 2017. 3. 21..
 */

public class ImgPostData {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("post_date")
    @Expose
    private String postDate;
    @SerializedName("post_link")
    @Expose
    private String postLink;
    @SerializedName("post_thumb")
    @Expose
    private String postThumb;
    @SerializedName("post_title")
    @Expose
    private String postTitle;
    @SerializedName("site_info")
    @Expose
    private String siteInfo;
    @SerializedName("count_recommend")
    @Expose
    private String countRecommend;
    @SerializedName("count_reply")
    @Expose
    private String countReply;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("url")
    @Expose
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostLink() {
        return postLink;
    }

    public void setPostLink(String postLink) {
        this.postLink = postLink;
    }

    public String getPostThumb() {
        return postThumb;
    }

    public void setPostThumb(String postThumb) {
        this.postThumb = postThumb;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getSiteInfo() {
        return siteInfo;
    }

    public void setSiteInfo(String siteInfo) {
        this.siteInfo = siteInfo;
    }

    public String getCountRecommend() {
        return countRecommend;
    }

    public void setCountRecommend(String countRecommend) {
        this.countRecommend = countRecommend;
    }

    public String getCountReply() {
        return countReply;
    }

    public void setCountReply(String countReply) {
        this.countReply = countReply;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

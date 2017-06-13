package com.ninis.grimmoa.network;

import com.ninis.grimmoa.data.ImgPostData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by gypark on 2017. 3. 21..
 */

public interface ApiInterface {
    @GET("/getPosts.json")
    Call<List<ImgPostData>> getImgPosts(@Query("allData") boolean isAllData);

    @GET("/getPosts.json")
    Call<List<ImgPostData>> getImgPosts(@Query("allData") boolean isAllData, @Query("page") int page);
}

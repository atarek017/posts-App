package com.example.recyclerviewretrofit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface JsonPlaceHolderApi {
    @GET("posts")
    Call<ArrayList<Post>> getPosts(
            @QueryMap Map<String, String> parmeters
    );


}

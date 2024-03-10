package com.dhairya.restapis.apis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface apiInterface {
    @GET("posts")
    Call<List<Apimodel>> getdata();
}

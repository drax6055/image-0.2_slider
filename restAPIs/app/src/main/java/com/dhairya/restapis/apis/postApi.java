package com.dhairya.restapis.apis;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface postApi {
    @FormUrlEncoded
    @POST("Auth.php")
    Call<postModel>addmodel (
            @Field("name") String name,
            @Field("email")String email,
            @Field("password") String password
    );

}

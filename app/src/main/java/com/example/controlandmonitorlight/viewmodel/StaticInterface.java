package com.example.controlandmonitorlight.viewmodel;

import com.example.controlandmonitorlight.model.Data;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StaticInterface {
    @GET("1")
    Call<Data> getData() ;
}

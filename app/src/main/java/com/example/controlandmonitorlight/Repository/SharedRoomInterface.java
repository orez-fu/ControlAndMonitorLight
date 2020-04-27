package com.example.controlandmonitorlight.Repository;

import com.example.controlandmonitorlight.model.SharedRoomRequest;
import com.example.controlandmonitorlight.model.SharedRoomResponse;
import com.example.controlandmonitorlight.model.SimpleResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SharedRoomInterface {

    @POST("rooms/token")
    Call<SharedRoomResponse> getRoomInfo(@Body SharedRoomRequest body);

    @POST("rooms/user/{id}")
    Call<SimpleResponse> addRoomByToken(@Path("id") String id, @Body SharedRoomRequest body);

}

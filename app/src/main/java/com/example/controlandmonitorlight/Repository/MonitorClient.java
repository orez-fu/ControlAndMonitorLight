package com.example.controlandmonitorlight.Repository;

import com.example.controlandmonitorlight.model.RoomStatic;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MonitorClient {
    private String BASE_URL ="https://us-central1-luxremote-zm.cloudfunctions.net/webApi/api/v1/";
    private static MonitorInterface monitorInterface ;
    private static MonitorClient INSTANCE ;

    public  MonitorClient(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();
        monitorInterface = retrofit.create(MonitorInterface.class);
    }

    public static MonitorClient getINSTANCE(){
        if(INSTANCE == null ){
            INSTANCE = new MonitorClient();
        }
        return INSTANCE ;
    }

    public Call<RoomStatic> getStatic(int id ) {
        return  this.monitorInterface.getStatic(id);
    }
}

package com.example.controlandmonitorlight.Repository;

import android.util.Log;

import com.example.controlandmonitorlight.model.DeviceStatic;
import com.example.controlandmonitorlight.model.SharedRoomRequest;
import com.example.controlandmonitorlight.model.SharedRoomResponse;
import com.example.controlandmonitorlight.model.SimpleResponse;
import com.example.controlandmonitorlight.model.StaticModel;
import com.google.firebase.database.annotations.NotNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public class DeviceClient {
    private String BASE_URL = "https://us-central1-luxremote-zm.cloudfunctions.net/webApi/api/v1/";

    private static DeviceInterface deviceInterface;
    private static SharedRoomInterface sharedRoomInterface;


    private static DeviceClient Instance;

    private DeviceClient() {

        Gson gson = new GsonBuilder().serializeNulls().create();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                        Request originalRequest = chain.request();

                        Request newRequest = originalRequest.newBuilder().
                                header("Interceptor-Header", "xyz")
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        deviceInterface = retrofit.create(DeviceInterface.class);
        sharedRoomInterface = retrofit.create(SharedRoomInterface.class);
    }

    public static DeviceClient getInstance() {

        if (Instance == null) {

            Instance = new DeviceClient();
        }
        return Instance;
    }

    public Call<DeviceStatic> getDeviceStatic(int id, Map<String, String> parameters) {
        return this.deviceInterface.getDeviceStatic(id, parameters);
    }

    public Call<StaticModel> getStaticModel(String id, Map<String, String> parameters) {
        return this.deviceInterface.getStaticModel(id, parameters);
    }

    public Call<SharedRoomResponse> getRoomInfo(SharedRoomRequest body) {
        return this.sharedRoomInterface.getRoomInfo(body);
    }

    public Call<SimpleResponse> addRoomByToken(String id, SharedRoomRequest body) {
        return this.sharedRoomInterface.addRoomByToken(id, body);
    }
}

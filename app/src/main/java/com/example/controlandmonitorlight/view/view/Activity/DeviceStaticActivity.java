package com.example.controlandmonitorlight.view.view.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.model.DeviceStaticModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DeviceStaticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_static);
        Gson gson = new Gson();
        String gson1 = getIntent().getStringExtra("rules");
        Type type = new TypeToken<DeviceStaticModel>() {
        }.getType();
//        DeviceStaticModel rules = gson.fromGson(gson, type);
        DeviceStaticModel deviceStaticModel = gson.fromJson(gson1, type);
        Log.d("DEVICE_STATIC", deviceStaticModel.getDeviceId());
        //tesst
    }
}

/*view*/ /*activity*/
package com.example.controlandmonitorlight.view.view.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.adapter.CustomDataAdapter;
import com.example.controlandmonitorlight.adapter.CustomDevicesAdapter;
import com.example.controlandmonitorlight.model.Data;
import com.example.controlandmonitorlight.model.Devices;
import com.example.controlandmonitorlight.viewmodel.Comunication;
import com.example.controlandmonitorlight.viewmodel.DataViewModel;
import com.example.controlandmonitorlight.viewmodel.DevicesViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class KidRoomActivity extends AppCompatActivity implements Comunication {

    Toolbar toolbar ;
    RecyclerView recyclerViewData ;
    RecyclerView recyclerViewRoom ;
    CustomDataAdapter customDataAdapterData ;
    CustomDevicesAdapter customDevicesAdapter;
    DataViewModel viewModel ;
    DevicesViewModel devicesViewModel ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kid_room);
        Mapping();
        initToolbar();

        viewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        LoadingData();
        viewModel.getData().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> data) {

                initRecyclerviewData(data);
            }
        });

        devicesViewModel = ViewModelProviders.of(this).get(DevicesViewModel.class) ;
        devicesViewModel.setData();
        // Log.d("size = ","2");

        devicesViewModel.getData().observe(this, new Observer<List<Devices>>() {
            @Override
            public void onChanged(List<Devices> devices) {
                //  Toast.makeText(getApplicationContext(),""+devices.get(0).getName(),Toast.LENGTH_SHORT).show();
                initRecyclerviewDevices(devices);
            }
        });



    }

    void initRecyclerviewData(List<Data> data)
    {
        customDataAdapterData = new CustomDataAdapter(data,this) ;
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3 ) ;
        recyclerViewData.setLayoutManager(layoutManager);
        recyclerViewData.setAdapter(customDataAdapterData);

    }
    void initRecyclerviewDevices(List<Devices> data )
    {
        customDevicesAdapter = new CustomDevicesAdapter(data,this) ;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerViewRoom.setLayoutManager(layoutManager);
        recyclerViewRoom.setAdapter(customDevicesAdapter);
    }
    void initToolbar()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("KitRoom");
    }
    void Mapping()
    {
        toolbar = findViewById(R.id.toolbar);
        recyclerViewData = findViewById(R.id.recycledata);
        recyclerViewRoom = findViewById(R.id.recycleroom);
    }

    @Override
    public void setOnClickedItem(int position) {
        //       Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();
        if(position == 0 )
        {
            Intent intent =  new Intent(this, DeviceControlActivity.class);
            startActivity(intent);
        }
    }
    public void LoadingData()
    {
        // nó ko vào ham ondatachange
        Log.d("key = ","xyz");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("rooms").child("00001").child("devices");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String humidity = dataSnapshot.child("humidity").getValue(String.class);
                String lux = dataSnapshot.child("lux").getValue(String.class);
                String temperature = dataSnapshot.child("temperature").getValue(String.class);
                String value = humidity+" "+lux+" "+ temperature;
                viewModel.setData(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("error = ",databaseError.getMessage());
            }
        });
    }

}

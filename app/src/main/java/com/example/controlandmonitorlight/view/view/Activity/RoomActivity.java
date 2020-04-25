/*view*/ /*activity*/
package com.example.controlandmonitorlight.view.view.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.adapter.RoomDeviceAdapter;
import com.example.controlandmonitorlight.adapter.ParameterRoomAdapter;
import com.example.controlandmonitorlight.model.DeviceDataModel;
import com.example.controlandmonitorlight.model.DeviceModel;
import com.example.controlandmonitorlight.model.Room;
import com.example.controlandmonitorlight.repositories.RealtimeFirebaseRepository;
import com.example.controlandmonitorlight.repositories.RealtimeRepository;
import com.example.controlandmonitorlight.viewmodel.Comunication;
import com.example.controlandmonitorlight.viewmodel.DataViewModel;
import com.example.controlandmonitorlight.viewmodel.DevicesViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Calendar;
import java.util.List;

import static com.example.controlandmonitorlight.MainActivity.KEY_ROOM_ID;
import static com.example.controlandmonitorlight.MainActivity.KEY_ROOM_NAME;

public class RoomActivity extends AppCompatActivity implements Comunication {

    public static final String KEY_DEVICE_ID = "com.example.controlandmonitorlight.view.view.Activity.KEY_DEVICE_ID";

    // UI variables
    Toolbar toolbar;
    RecyclerView recyclerViewData;
    RecyclerView recyclerViewRoom;
    RoomDeviceAdapter customDataAdapterData;
    ParameterRoomAdapter customDevicesAdapter;
    DataViewModel viewModel;
    DevicesViewModel devicesViewModel;
    private SwitchCompat switchCompat;
    private TextView textWarning;

    // Data variables
    private String title;
    private String roomId;
    private Integer hasNotification;
    List<DeviceModel> devices;

    private Handler mainHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kid_room);
        Intent intent = getIntent();

        title = intent.getStringExtra(KEY_ROOM_NAME);
        roomId = intent.getStringExtra(KEY_ROOM_ID);

        mapping();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);

        viewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        devicesViewModel = ViewModelProviders.of(this).get(DevicesViewModel.class);
        Log.d("roomId",roomId) ;
        Toast.makeText(getApplicationContext(),""+roomId,Toast.LENGTH_SHORT).show();
        loadingData(roomId);

        devicesViewModel.LoadDevicesFireBase(roomId);

        viewModel.getData().observe(this, new Observer<List<DeviceDataModel>>() {
            @Override
            public void onChanged(List<DeviceDataModel> data) {
                initRecyclerviewData(data);
            }
        });

        devicesViewModel.getData().observe(this, new Observer<List<DeviceModel>>() {
            @Override
            public void onChanged(List<DeviceModel> objectDevices) {
                devices = objectDevices;
                initRecyclerviewDevices(objectDevices);
            }
        });

        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchCompat.isChecked()) {
                    RealtimeFirebaseRepository.getInstance().updateNotificationAtRoom(roomId, Room.NOTIFICATION_YES);
                    FirebaseMessaging.getInstance().subscribeToTopic(roomId);
                } else {
                    RealtimeFirebaseRepository.getInstance().updateNotificationAtRoom(roomId, Room.NOTIFICATION_NO);
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(roomId);
                }
            }
        });

        runningWarning();

    }

    void initRecyclerviewData(List<DeviceDataModel> data) {
        customDataAdapterData = new RoomDeviceAdapter(data, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerViewData.setLayoutManager(layoutManager);
        recyclerViewData.setAdapter(customDataAdapterData);

    }

    void initRecyclerviewDevices(List<DeviceModel> data) {
        customDevicesAdapter = new ParameterRoomAdapter(data, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewRoom.setLayoutManager(layoutManager);
        recyclerViewRoom.setAdapter(customDevicesAdapter);
    }

    void mapping() {
        toolbar = findViewById(R.id.toolbar);
        recyclerViewData = findViewById(R.id.recycledata);
        recyclerViewRoom = findViewById(R.id.recycleroom);
        switchCompat = findViewById(R.id.switch_notification);
        textWarning = findViewById(R.id.tv_warning);
    }

    @Override
    public void setOnClickedItem(int position) {
        Intent intent = new Intent(this, DeviceControlActivity.class);
        intent.putExtra(KEY_ROOM_NAME, title);
        intent.putExtra(KEY_ROOM_ID, roomId);
        intent.putExtra(KEY_DEVICE_ID, devices.get(position).getId());
        startActivity(intent);
    }

    public void loadingData(final String roomId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("rooms").child(roomId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                title = dataSnapshot.child("name").getValue(String.class);
                String humidity = dataSnapshot.child("humidity").getValue(String.class);
                String lux = dataSnapshot.child("lux").getValue(String.class);
                String temperature = dataSnapshot.child("temperature").getValue(String.class);
                String value = humidity + " " + temperature + " " + lux;
                hasNotification = dataSnapshot.child("notification").getValue(Integer.class);

                switchCompat.setChecked(hasNotification.equals(Room.NOTIFICATION_YES));
                if(hasNotification.equals(Room.NOTIFICATION_YES)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(roomId);
                }

                viewModel.setData(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("error = ", databaseError.getMessage());
            }
        });
    }

    private void runningWarning() {
        final WarningRunnable runnable = new WarningRunnable(roomId);
        new Thread(runnable).start();
    }

    class WarningRunnable implements  Runnable {
        private long timestamp = 0;
        private String roomId;
        private boolean isWarning = false;

        public WarningRunnable(String roomId) {
            this.roomId = roomId;
        }

        @Override
        public void run() {
            FirebaseDatabase.getInstance().getReference("rooms")
                    .child(roomId)
                    .child("lastTime").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    timestamp = Math.round(dataSnapshot.getValue(Double.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            while(true) {
                try {
                    Thread.sleep(1000);
                    if(timestamp + 4000 < Calendar.getInstance().getTimeInMillis()) {
                        textWarning.post(new Runnable() {
                            @Override
                            public void run() {
                                textWarning.setVisibility(View.VISIBLE);
                                isWarning = true;
                            }
                        });
                    } else if(isWarning){
                        textWarning.post(new Runnable() {
                            @Override
                            public void run() {
                                textWarning.setVisibility(View.GONE);
                                isWarning = false;
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

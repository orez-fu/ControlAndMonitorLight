package com.example.controlandmonitorlight.view.view.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.model.DeviceModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.controlandmonitorlight.MainActivity.KEY_ROOM_ID;
import static com.example.controlandmonitorlight.view.view.Activity.RoomActivity.KEY_DEVICE_ID;

public class ControlFragment extends Fragment {

    public static final String TITLE = "Control";
    private final String KEY_BUNDLE_ROOM_ID = "KEY_BUNDLE_ROOM_ID";
    private final String KEY_BUNDLE_DEVICE_ID = "KEY_BUNDLE_DEVICE_ID";

    // Data variables
    private String roomId;
    private String deviceId;
    int deviceStatus;

    // UI variables
    ImageButton buttonLed;
    ImageView imgLight;

    public static ControlFragment newInstance() {
        return new ControlFragment();
    }


    public ControlFragment() {
        // Required empty public constructor
    }

    String temp = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_control, container, false);

        if(getActivity().getIntent() != null) {
            Intent intent = getActivity().getIntent();
            roomId = intent.getStringExtra(KEY_ROOM_ID);
            deviceId = intent.getStringExtra(KEY_DEVICE_ID);
        }

//        roomId = "room-id-0002";
//        deviceId = "device-id-0002";

        buttonLed = view.findViewById(R.id.buttonLed);
        imgLight = view.findViewById(R.id.img_light);
        loadingData();

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(KEY_BUNDLE_DEVICE_ID, deviceId);
        outState.putString(KEY_BUNDLE_ROOM_ID, roomId);
    }

    private void loadingData() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("rooms").child(roomId)
                .child("devices").child(deviceId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String status = dataSnapshot.child("status").getValue(String.class);
                temp = status;
                deviceStatus = Integer.parseInt(temp);
                if (deviceStatus == 0) {
                    imgLight.setImageResource(R.drawable.light_off);
                    buttonLed.setBackgroundResource(R.drawable.buttonoff);

                } else {
                    imgLight.setImageResource(R.drawable.light_on);
                    buttonLed.setBackgroundResource(R.drawable.buttonon);

                }
                buttonLed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (deviceStatus == 0) {
                            imgLight.setImageResource(R.drawable.light_off);
                            buttonLed.setBackgroundResource(R.drawable.buttonoff);
                            sendDataa(DeviceModel.STATUS_ON);
                            deviceStatus = 1;
                        } else {
                            imgLight.setImageResource(R.drawable.light_on);
                            buttonLed.setBackgroundResource(R.drawable.buttonon);
                            sendDataa(DeviceModel.STATUS_OFF);
                            deviceStatus = 0;
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void sendDataa(String k) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("rooms").child(roomId)
                .child("devices").child(deviceId).child("status");
        reference.setValue(k);
    }
}

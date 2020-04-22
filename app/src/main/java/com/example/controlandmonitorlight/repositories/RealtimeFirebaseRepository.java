package com.example.controlandmonitorlight.repositories;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.controlandmonitorlight.model.Room;
import com.example.controlandmonitorlight.model.TimerModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class RealtimeFirebaseRepository {
    private static final String TAG = "REALTIME_REPOSITORY";

    private static RealtimeFirebaseRepository instance;
    private DatabaseReference reference;
    private List<Room> rooms = new ArrayList<>();

    private RealtimeFirebaseRepository(DatabaseReference reference) {
        this.reference = reference;
    }

    public static RealtimeFirebaseRepository getInstance() {
        if (instance == null) {
            instance = new RealtimeFirebaseRepository(FirebaseDatabase.getInstance().getReference());
        }

        return instance;
    }

    public void toggleStatusTimer(String deviceId, String key, int value) {
        reference.child("timer")
                .child(deviceId)
                .child(key)
                .child("status")
                .setValue(value).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }

    public void addNewTimer(String deviceId, TimerModel timer) {
        String key = reference.child("timer")
                .child(String.valueOf(deviceId))
                .push().getKey();
        Map<String, Object> postValues = timer.toMap(key);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/timer/" + deviceId + "/" + key, postValues);

        reference.updateChildren(childUpdates);
    }

    public void updateTimer(String deviceId, String key, TimerModel timer) {
        Map<String, Object> postValues = timer.toMap(key);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/timer/" + deviceId + "/" + key, postValues);

        reference.updateChildren(childUpdates);
    }


    public void deleteTimer(String deviceId, String timerId) {
        Log.d(TAG, "Timer ID: " + timerId);
        reference.child("timer").child(deviceId).child(timerId).removeValue();
    }

    public void updateNotificationAtRoom(String roomId, Integer stateNotification) {
        reference.child("rooms").child(roomId).child("notification").setValue(stateNotification);
    }


    public List<Room> getRooms(String uid) {
        reference.child(uid).child("rooms").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rooms.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String roomId = snapshot.getKey();

                    DatabaseReference referenceRooms = null;
                    if (roomId != null) {
                        referenceRooms = FirebaseDatabase.getInstance()
                                .getReference("rooms").child(roomId);
                        referenceRooms.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Room room = dataSnapshot.getValue(Room.class);
                                rooms.add(room);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.d(TAG, "Cancel");
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return rooms;
    }
}

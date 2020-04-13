package com.example.controlandmonitorlight.repositories;


import com.example.controlandmonitorlight.model.TimerModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RealtimeFirebaeRepository {
    private static final String TAG = "REALTIME_REPOSITORY";

    private static RealtimeFirebaeRepository instance;
    private DatabaseReference reference;

    private RealtimeFirebaeRepository(DatabaseReference reference) {
        this.reference = reference;
    }

    public static RealtimeFirebaeRepository getInstance() {
        if (instance == null) {
            instance = new RealtimeFirebaeRepository(FirebaseDatabase.getInstance().getReference());
        }

        return instance;
    }

    public void toggleStatusTimer(int deviceId, String key, int value) {
        reference.child("timer")
                .child(String.valueOf(deviceId))
                .child(key)
                .child("status")
                .setValue(value).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }

    public void addNewTimer(int deviceId, TimerModel timer) {
        String key = reference.child("timer")
                .child(String.valueOf(deviceId))
                .push().getKey();
        Map<String, Object> postValues = timer.toMap(key);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/timer/" + String.valueOf(deviceId) + "/" + key, postValues);

        reference.updateChildren(childUpdates);
    }

    public void updateTimer(int deviceId, String key, TimerModel timer) {
        Map<String, Object> postValues = timer.toMap(key);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/timer/" + String.valueOf(deviceId) + "/" + key, postValues);

        reference.updateChildren(childUpdates);
    }
}

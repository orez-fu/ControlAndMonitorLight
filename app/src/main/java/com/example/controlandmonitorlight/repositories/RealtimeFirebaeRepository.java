package com.example.controlandmonitorlight.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.controlandmonitorlight.model.Timer;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public void addNewTimer(int deviceId, Timer timer) {
        String key = reference.child("timer")
                .child(String.valueOf(deviceId))
                .push().getKey();
        Map<String, Object> postValues = timer.toMap(key);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/timer/" + String.valueOf(deviceId) + "/" + key, postValues);

        reference.updateChildren(childUpdates);
    }

    public void updateTimer(int deviceId, String key, Timer timer) {
        Map<String, Object> postValues = timer.toMap(key);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/timer/" + String.valueOf(deviceId) + "/" + key, postValues);

        reference.updateChildren(childUpdates);
    }
}

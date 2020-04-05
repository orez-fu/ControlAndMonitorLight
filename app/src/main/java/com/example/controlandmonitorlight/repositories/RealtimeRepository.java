package com.example.controlandmonitorlight.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.controlandmonitorlight.model.Timer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RealtimeRepository {
    private static final String TAG = "REALTIME_REPOSITORY";

    private static RealtimeRepository instance;

    private List<Timer> listTimers = new ArrayList<>();
    private MutableLiveData<List<Timer>> timers = new MutableLiveData<>();


    public static RealtimeRepository getInstance() {
        if(instance == null) {
            instance = new RealtimeRepository();
        }

        return instance;
    }

    public MutableLiveData<List<Timer>> getTimers() {
       if(listTimers.size() == 0) {
           loadTimers();
       }

        timers.setValue(listTimers);
        return timers;
    }

    private void loadTimers() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child("timer").child(String.valueOf(1));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Log.d(TAG, snapshot.toString());
                    Log.d(TAG, snapshot.getValue(Timer.class).getLabel() + ": " + snapshot.getValue(Timer.class).getTime());
                    listTimers.add(snapshot.getValue(Timer.class));
                }

                timers.postValue(listTimers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "Fail read data");
                Log.d(TAG, databaseError.toString());
            }
        });
    }


}

/*viewmodel*/
package com.example.controlandmonitorlight.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.controlandmonitorlight.model.Room;
import com.example.controlandmonitorlight.model.StaticModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class IntroductionViewModel extends ViewModel {
    FirebaseAuth mAuth;

    public MutableLiveData<List<Room>> intro = new MutableLiveData<>();
    public List<Room> list = new ArrayList<>();
    public MutableLiveData<Integer> progress = new MutableLiveData<>(0);

    public void LoadDataFireBase() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("rooms");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progress.postValue(10);

                if (!dataSnapshot.exists()) {
                    intro.postValue(list);
                    return;
                }

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String roomId = snapshot.getKey();
                    Log.d("INTRODUCTION", snapshot.getKey());
                    DatabaseReference reference1 = FirebaseDatabase.getInstance()
                            .getReference("rooms").child(roomId);
                    list.clear();
                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d("INTRODUCTION", dataSnapshot.getValue().toString());
                            Room r = dataSnapshot.getValue(Room.class);
                            list.add(r);
                            intro.postValue(list);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d("INTRODUCTION", "Cancel");
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("INTRODUCTION", "Cancel With no error");
            }
        });
    }

}


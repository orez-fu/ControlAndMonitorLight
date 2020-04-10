/*viewmodel*/
package com.example.controlandmonitorlight.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.model.ObjectDevices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DevicesViewModel extends ViewModel {
    private MutableLiveData<List<ObjectDevices>> data = new MutableLiveData<>();
    public MutableLiveData<List<ObjectDevices>> getData()
    {
        return  data ;
    }
    public List<ObjectDevices> mListDevices = new ArrayList<>() ;
    public void setData()
    {
       data.setValue(mListDevices);
    }

    public void LoadDevicesFireBase(String id )
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("rooms").child(id);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("devices")){
                    for (DataSnapshot snapshot : dataSnapshot.child("devices").getChildren()){
                        ObjectDevices device = snapshot.getValue(ObjectDevices.class);
                        mListDevices.add(device);
                    }
                    data.postValue(mListDevices);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

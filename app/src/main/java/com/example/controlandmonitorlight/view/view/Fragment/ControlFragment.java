/*view*/ /*fragment*/
package com.example.controlandmonitorlight.view.view.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.controlandmonitorlight.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ControlFragment extends Fragment {

    public static final String TITLE = "Control";
    public static ControlFragment newInstance() {
        return new ControlFragment();
    }
    Button buttonLed;
    int check ;
    public ControlFragment() {
        // Required empty public constructor
    }
    String temp ="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_control, container, false);
        buttonLed = view.findViewById(R.id.buttonLed);
        LoadingData();


        return view ;
    }

    public void LoadingData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("rooms").child("00002")
                .child("devices").child("abc-0001");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String status = dataSnapshot.child("status").getValue(String.class);
                temp = status;
                check = Integer.parseInt(temp);
                if(check == 0 ){
                    buttonLed.setBackgroundColor(Color.BLACK);

                }else{
                    buttonLed.setBackgroundColor(Color.BLUE);

                }
                buttonLed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(check == 0 ){
                            buttonLed.setBackgroundColor(Color.BLACK);
                            sendDataa("1");
                            check = 1 ;
                        }else{
                            buttonLed.setBackgroundColor(Color.BLUE);
                            sendDataa("0");
                            check = 0 ;
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void sendDataa(String k)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("rooms").child("00002")
                .child("devices").child("abc-0001").child("status");
        reference.setValue(k);
    }
}

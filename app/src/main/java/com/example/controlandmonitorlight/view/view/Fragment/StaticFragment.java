package com.example.controlandmonitorlight.view.view.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.adapter.ItemRoomAdapter;
import com.example.controlandmonitorlight.model.DeviceModel;
import com.example.controlandmonitorlight.model.Room;
import com.example.controlandmonitorlight.viewmodel.DevicesViewModel;
import com.example.controlandmonitorlight.viewmodel.IntroductionViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StaticFragment extends Fragment {

    FirebaseAuth mAuth;
    RecyclerView recyclerView ;
    List<Room>Rooms = new ArrayList<>();
    ItemRoomAdapter itemRoomAdapter;
    IntroductionViewModel introductionViewModel ;

    List<DeviceModel> numberDevices= new ArrayList<>();
    private DevicesViewModel devicesViewModel ;
    public StaticFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_static, container, false);
        recyclerView = view.findViewById(R.id.recycle);

        introductionViewModel = ViewModelProviders.of(this).get(IntroductionViewModel.class);
        introductionViewModel.SetData();
        introductionViewModel.LoadDataFireBase();
        introductionViewModel.getIntro().observe(this, new Observer<List<Room>>() {
            @Override
            public void onChanged(List<Room> rooms) {
                Rooms = rooms ;
            }
        });
        /*
        devicesViewModel = ViewModelProviders.of(this).get(DevicesViewModel.class) ;
        devicesViewModel

         */
        initRecyclerview(Rooms);
        return view ;
    }

    void initRecyclerview(List<Room> rooms){
        itemRoomAdapter = new ItemRoomAdapter(rooms,getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        itemRoomAdapter.notifyDataSetChanged();
    }
}

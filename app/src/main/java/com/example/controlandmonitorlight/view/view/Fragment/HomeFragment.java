package com.example.controlandmonitorlight.view.view.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.adapter.CustomListRoomAdapter;
import com.example.controlandmonitorlight.adapter.ItemRoomAdapter;
import com.example.controlandmonitorlight.model.Room;
import com.example.controlandmonitorlight.view.view.Activity.RoomActivity;
import com.example.controlandmonitorlight.viewmodel.IntroductionViewModel;
import com.example.controlandmonitorlight.viewmodel.RoomInterface;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.github.ybq.android.spinkit.style.Wave;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements RoomInterface {
    public static final String KEY_ROOM_ID = "com.example.controlandmonitorlight.KEY_ROOM_ID";
    public static final String KEY_ROOM_NAME = "com.example.controlandmonitorlight.KEY_ROOM_NAME";

    private RecyclerView rListRooms;
    private  List<Room> nameRooms = new ArrayList<>() ;
    IntroductionViewModel introductionViewModel ;
    CustomListRoomAdapter itemListRoomAdapter;
    private ProgressBar progressBar;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rListRooms = view.findViewById(R.id.list_room);
        progressBar = (ProgressBar) view.findViewById(R.id.spin_kit);

        FoldingCube wave = new FoldingCube();
        progressBar.setIndeterminateDrawable(wave);
        progressBar.setVisibility(View.VISIBLE);

        introductionViewModel = ViewModelProviders.of(this).get(IntroductionViewModel.class);
        introductionViewModel.SetData();
        introductionViewModel.LoadDataFireBase(getContext());

        introductionViewModel.getIntro().observe(getActivity(), new Observer<List<Room>>() {
            @Override
            public void onChanged(List<Room> rooms) {
                nameRooms = rooms;
                if(!nameRooms.isEmpty()) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
                initRecyclerview(nameRooms);
            }
        });

        return view ;
    }

    private void initRecyclerview(List<Room> rooms) {
        itemListRoomAdapter = new CustomListRoomAdapter(rooms,getContext());
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false) ;
        rListRooms.setLayoutManager(layoutManager);
        rListRooms.setAdapter(itemListRoomAdapter);
        itemListRoomAdapter.setClick(this);
    }

    @Override
    public void setOnclickItem(int position) {
       // Toast.makeText(getContext(),""+position,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), RoomActivity.class);
        intent.putExtra(KEY_ROOM_NAME, nameRooms.get(position).getName()) ;
        intent.putExtra(KEY_ROOM_ID, nameRooms.get(position).getId());
        startActivity(intent);
    }

}

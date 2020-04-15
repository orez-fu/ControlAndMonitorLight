package com.example.controlandmonitorlight.view.view.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.adapter.CustomListRoomAdapter;
import com.example.controlandmonitorlight.viewmodel.RoomInterface;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements RoomInterface {


    private RecyclerView rListRooms;
    private List<String> nameRooms = new ArrayList<>() ;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rListRooms = view.findViewById(R.id.list_room);
        nameRooms.add("Living Room") ;
        nameRooms.add("Kitchen Room") ;
        CustomListRoomAdapter customListRoomAdapter = new CustomListRoomAdapter(nameRooms,getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        rListRooms.setLayoutManager(layoutManager);
        rListRooms.setAdapter(customListRoomAdapter);
        customListRoomAdapter.setClick(this);

        return view ;
    }

    @Override
    public void setOnclickItem(int position) {
    }
}

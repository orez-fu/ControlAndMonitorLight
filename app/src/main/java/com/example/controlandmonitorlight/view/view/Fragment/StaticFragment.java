package com.example.controlandmonitorlight.view.view.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.adapter.ItemRoomAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StaticFragment extends Fragment {

    RecyclerView recyclerView ;
    List<String>Rooms = new ArrayList<>();
    public StaticFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_static, container, false);
        recyclerView = view.findViewById(R.id.recycle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Rooms.add("Living rooms") ;
        Rooms.add("Badth room ");
        ItemRoomAdapter itemRoomAdapter = new ItemRoomAdapter(Rooms,getContext());
        recyclerView.setAdapter(itemRoomAdapter);
        return view ;
    }
}

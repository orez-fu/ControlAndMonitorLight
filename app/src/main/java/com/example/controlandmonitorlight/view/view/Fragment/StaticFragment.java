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
import com.example.controlandmonitorlight.Repository.DeviceClient;
import com.example.controlandmonitorlight.adapter.ItemRoomAdapter;
import com.example.controlandmonitorlight.model.DeviceModel;
import com.example.controlandmonitorlight.model.Room;
import com.example.controlandmonitorlight.model.RoomStaticModel;
import com.example.controlandmonitorlight.model.StaticModel;
import com.example.controlandmonitorlight.viewmodel.DevicesViewModel;
import com.example.controlandmonitorlight.viewmodel.IntroductionViewModel;
import com.example.controlandmonitorlight.viewmodel.StaticTotalViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class StaticFragment extends Fragment {

    FirebaseUser user;
    RecyclerView recyclerView ;
    ItemRoomAdapter itemRoomAdapter;
    StaticTotalViewModel staticTotalViewModel ;

    public StaticFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_static, container, false);
        recyclerView = view.findViewById(R.id.recycle);

        user = FirebaseAuth.getInstance().getCurrentUser() ;
        final String userId = user.getUid();

        staticTotalViewModel = ViewModelProviders.of(this).get(StaticTotalViewModel.class);
        staticTotalViewModel.getStaticData(userId,16,4,2020);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        itemRoomAdapter = new ItemRoomAdapter(getContext());

        recyclerView.setAdapter(itemRoomAdapter);

        staticTotalViewModel.dataStatic.observe(getActivity(), new Observer<StaticModel>() {
            @Override
            public void onChanged(StaticModel staticModel) {
                itemRoomAdapter.setRoomStaticList(staticModel.getRooms());
            }
        });
        /*
        devicesViewModel = ViewModelProviders.of(this).get(DevicesViewModel.class) ;
        devicesViewModel

         */
        return view ;
    }

}

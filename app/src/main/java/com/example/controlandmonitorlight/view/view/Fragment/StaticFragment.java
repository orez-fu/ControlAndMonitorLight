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
import com.example.controlandmonitorlight.model.Room;
import com.example.controlandmonitorlight.model.StaticModel;
import com.example.controlandmonitorlight.viewmodel.IntroductionViewModel;
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
    FirebaseAuth mAuth;
    RecyclerView recyclerView ;
    ItemRoomAdapter itemRoomAdapter;
    IntroductionViewModel introductionViewModel ;

    // Data variables
    List<Room>Rooms = new ArrayList<>();
    private StaticModel mStatics;

    public StaticFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_static, container, false);
        recyclerView = view.findViewById(R.id.recycle);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser() ;
        final String id = String.valueOf(user.getUid());
        Log.d("ID = ",id );
        Toast.makeText(getContext(),"CO"+id,Toast.LENGTH_SHORT).show();
        introductionViewModel = ViewModelProviders.of(this).get(IntroductionViewModel.class);
        introductionViewModel.SetData();
        introductionViewModel.LoadDataFireBase(getContext());
        introductionViewModel.getIntro().observe(getActivity(), new Observer<List<Room>>() {
            @Override
            public void onChanged(List<Room> rooms) {
                Rooms = rooms ;
              //  Log.d("Rooms = " , ""+Rooms.size());
                initRecyclerview(Rooms);

            }
        });

        mStatics = new StaticModel();

        Log.d("STATIC_FRAGMENT", "Start call api for user: " + user.getUid());
        Map < String, String > parameter = new HashMap<>();
        parameter.put("day","16");
        parameter.put("month","4");
        parameter.put("year","2020");
        DeviceClient.getInstance().getStaticModel(user.getUid(), parameter).enqueue(new Callback<StaticModel>() {
            @Override
            public void onResponse(Call<StaticModel> call, Response<StaticModel> response) {
                Log.d("STATIC_FRAGMENT",response.body().toString());
                mStatics = response.body();
    //            Toast.makeText(getActivity(), "Called API",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<StaticModel> call, Throwable t) {
                Log.d("STATIC_FRAGMENT", "Failed" + t.getMessage().toString());
              //  Toast.makeText(getActivity(), "Called fail API",Toast.LENGTH_SHORT).show();

            }
        });
        return view ;
    }


    void initRecyclerview(List<Room> rooms ){
        itemRoomAdapter = new ItemRoomAdapter(rooms,getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Log.d("temp = ",rooms.size()+"");
        recyclerView.setAdapter(itemRoomAdapter);
        itemRoomAdapter.notifyDataSetChanged();
    }
}

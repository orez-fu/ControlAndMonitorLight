package com.example.controlandmonitorlight.view.view.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment implements RoomInterface {
    public static final String KEY_ROOM_ID = "com.example.controlandmonitorlight.KEY_ROOM_ID";
    public static final String KEY_ROOM_NAME = "com.example.controlandmonitorlight.KEY_ROOM_NAME";

    // UI Variables
    private View header;
    private TextView tvUserName;
    private CircleImageView imageUser;

    private RecyclerView rListRooms;
    private List<Room> nameRooms = new ArrayList<>();
    IntroductionViewModel introductionViewModel;
    CustomListRoomAdapter itemListRoomAdapter;
    private ProgressBar progressBar;

    // Data variables
    private FirebaseUser mUser;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rListRooms = view.findViewById(R.id.list_room);

        // mapping ui
        header = view.findViewById(R.id.user_bar_layout);
        tvUserName = header.findViewById(R.id.txt_username);
        imageUser = header.findViewById(R.id.img_profile_small);
        progressBar = (ProgressBar) view.findViewById(R.id.spin_kit);


        // load user
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        tvUserName.setText(String.valueOf("Hi, " + mUser.getDisplayName()));
        if (mUser.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(mUser.getPhotoUrl().toString())
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageUser);
        }

        FoldingCube wave = new FoldingCube();
        progressBar.setIndeterminateDrawable(wave);
        progressBar.setVisibility(View.VISIBLE);

        introductionViewModel = ViewModelProviders.of(this).get(IntroductionViewModel.class);
        introductionViewModel.LoadDataFireBase();

        introductionViewModel.intro.observe(getActivity(), new Observer<List<Room>>() {
            @Override
            public void onChanged(List<Room> rooms) {
                nameRooms = rooms;
                if (introductionViewModel.progress.getValue() != 0) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
                initRecyclerview(nameRooms);
            }
        });

        return view;
    }

    private void initRecyclerview(List<Room> rooms) {
        itemListRoomAdapter = new CustomListRoomAdapter(rooms, getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);


//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rListRooms.setLayoutManager(gridLayoutManager);
        rListRooms.setAdapter(itemListRoomAdapter);
        itemListRoomAdapter.setClick(this);
    }

    @Override
    public void setOnclickItem(int position) {
        // Toast.makeText(getContext(),""+position,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), RoomActivity.class);
        intent.putExtra(KEY_ROOM_NAME, nameRooms.get(position).getName());
        intent.putExtra(KEY_ROOM_ID, nameRooms.get(position).getId());
        startActivity(intent);
    }

}

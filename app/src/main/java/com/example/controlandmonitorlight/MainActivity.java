package com.example.controlandmonitorlight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.controlandmonitorlight.adapter.CustomAdapter;
import com.example.controlandmonitorlight.model.Room;
import com.example.controlandmonitorlight.model.User;
import com.example.controlandmonitorlight.view.view.Activity.RoomActivity;
import com.example.controlandmonitorlight.view.view.Activity.SettingActivity;
import com.example.controlandmonitorlight.view.view.Activity.StaticActivity;
import com.example.controlandmonitorlight.viewmodel.Comunication;
import com.example.controlandmonitorlight.viewmodel.IntroductionViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Comunication {
    public static final String KEY_ROOM_ID = "com.example.controlandmonitorlight.KEY_ROOM_ID";
    public static final String KEY_ROOM_NAME = "com.example.controlandmonitorlight.KEY_ROOM_NAME";

    FirebaseUser mUser;
    RecyclerView recyclerView ;
    ChipNavigationBar chipNavigationBar ;
    CustomAdapter customAdapter ;
    TextView mTitle ;
    List<Room> mListRoom = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        Mapping() ;
        if(savedInstanceState == null){
            chipNavigationBar.setItemSelected(R.id.home,true);
        }

        IntroductionViewModel viewModel = ViewModelProviders.of(this).get(IntroductionViewModel.class);
        //initRecycleView();
        viewModel.SetData();
        //viewModel.clicked(this);
        viewModel.LoadDataFireBase(this);
        viewModel.getIntro().observe(this, new Observer<List<Room>>() {
            @Override
            public void onChanged(List<Room> rooms) {
                mTitle.setText(mUser.getDisplayName());
                mListRoom = rooms;
                initRecycleView(rooms);
            }
        });
        eventNavigationBar();
    }
    void Mapping()
    {
        chipNavigationBar = findViewById(R.id.navigation);
        recyclerView = findViewById(R.id.recycle);
        mTitle = findViewById(R.id.title);
    }
    void initRecycleView(List<Room> list)
    {
        customAdapter = new CustomAdapter(list,this);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this,RecyclerView.VERTICAL,false) ;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(customAdapter);
    }
    public  void eventNavigationBar()
    {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Intent intent = null;
                switch (i) {

                    case R.id.static1:
                        intent = new Intent(MainActivity.this,StaticActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.settings:
                         intent = new Intent(MainActivity.this, SettingActivity.class);
                         startActivity(intent);
                        break;
                }
            }
        });
    }
    @Override
    public void setOnClickedItem(int position) {
            Intent intent = new Intent(this, RoomActivity.class);
            intent.putExtra(KEY_ROOM_NAME, mListRoom.get(position).getName()) ;
            intent.putExtra(KEY_ROOM_ID, mListRoom.get(position).getId());
            startActivity(intent);
    }
}

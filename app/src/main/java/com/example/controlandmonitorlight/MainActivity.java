package com.example.controlandmonitorlight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.controlandmonitorlight.model.Room;
import com.example.controlandmonitorlight.view.view.Fragment.HomeFragment;
import com.example.controlandmonitorlight.view.view.Fragment.SettingFragment;
import com.example.controlandmonitorlight.view.view.Fragment.StaticFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_ROOM_ID = "com.example.controlandmonitorlight.KEY_ROOM_ID";
    public static final String KEY_ROOM_NAME = "com.example.controlandmonitorlight.KEY_ROOM_NAME";

    private BottomNavigationView bottomNav;
    private FragmentManager fragmentManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // mapping
        bottomNav = findViewById(R.id.navigation);

        if(savedInstanceState == null){
            bottomNav.setSelectedItemId(R.id.home);
        }

        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_place,new HomeFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment = null ;
            switch (menuItem.getItemId()){
                case R.id.home:
                    fragment = new HomeFragment();
                    break;
                case R.id.static1:
                    fragment = new StaticFragment();
                    break;
                case R.id.profile:
                    fragment = new SettingFragment();
                    break;
            }
            if(fragment != null ){
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_place,fragment).commit();
                return true ;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
    }



}

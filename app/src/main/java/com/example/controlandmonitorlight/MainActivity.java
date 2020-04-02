package com.example.controlandmonitorlight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.controlandmonitorlight.adapter.CustomAdapter;
import com.example.controlandmonitorlight.model.Introduction;
import com.example.controlandmonitorlight.view.view.Activity.KidRoomActivity;
import com.example.controlandmonitorlight.view.view.Activity.StaticActivity;
import com.example.controlandmonitorlight.viewmodel.Comunication;
import com.example.controlandmonitorlight.viewmodel.IntroductionViewModel;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Comunication {

    RecyclerView recyclerView ;
    ChipNavigationBar chipNavigationBar ;
    CustomAdapter customAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mapping() ;
        if(savedInstanceState == null){
            chipNavigationBar.setItemSelected(R.id.home,true);
        }

        IntroductionViewModel viewModel = ViewModelProviders.of(this).get(IntroductionViewModel.class);
        //initRecycleView();
        viewModel.SetData();
        //viewModel.clicked(this);
        viewModel.getIntro().observe(this, new Observer<List<Introduction>>() {
            @Override
            public void onChanged(List<Introduction> introductions) {
                //Toast.makeText(getApplicationContext(),""+"oke",Toast.LENGTH_SHORT).show();
                initRecycleView(introductions);
            }
        });
        eventNavigationBar();
    }
    void Mapping()
    {
        chipNavigationBar = findViewById(R.id.navigation);
        recyclerView = findViewById(R.id.recycle);
    }
    void initRecycleView(List<Introduction> list)
    {
        customAdapter = new CustomAdapter(list,this);
        GridLayoutManager layoutManager= new GridLayoutManager(this,2) ;
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
                        Toast.makeText(getApplicationContext(),"Settings",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
    @Override
    public void setOnClickedItem(int position) {
        if (position == 0)
        {
            Intent intent = new Intent(this, KidRoomActivity.class);
            intent.putExtra("NameTitle","KidRoom") ;
            startActivity(intent);
        }
        if(position == 1 )
        {

        }
    }
}

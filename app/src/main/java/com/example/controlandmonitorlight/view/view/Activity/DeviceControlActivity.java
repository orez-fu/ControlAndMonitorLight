/*view*/ /*activity*/
package com.example.controlandmonitorlight.view.view.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.adapter.ViewpagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class DeviceControlActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private ViewpagerAdapter mViewPagerAdapter;
    private TabLayout mTabLayout;
    String title , id ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_device_control);

        mToolbar = findViewById(R.id.toolbar);
        Intent intent = getIntent();
        title = intent.getStringExtra("NameTitle");
        id = intent.getStringExtra("Id");
        initToolbar();
        setViewPager();
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeviceControlActivity.this,RoomActivity.class);
                intent.putExtra("NameTitle",title);
                intent.putExtra("Id",id);
                startActivity(intent);
                finish();
            }
        });
    }

    public void setViewPager() {
        mViewPager = findViewById(R.id.pager);
        mViewPagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);

        mTabLayout = findViewById(R.id.tab);
        mTabLayout.setupWithViewPager(mViewPager);
    }
    public void initToolbar()
    {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("KitRoom");
    }
}

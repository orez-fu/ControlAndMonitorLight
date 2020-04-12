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

import static com.example.controlandmonitorlight.MainActivity.KEY_ROOM_ID;
import static com.example.controlandmonitorlight.MainActivity.KEY_ROOM_NAME;

public class DeviceControlActivity extends AppCompatActivity {
    public static final String EXTRA_PAGER = "com.example.controlandmonitorlight.view.view.Activity.EXTRA_PAGER";

    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private ViewpagerAdapter mViewPagerAdapter;
    private TabLayout mTabLayout;

    // Data variables
    private String title, id;
    private int pagerPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_device_control);

        mToolbar = findViewById(R.id.toolbar);
        Intent intent = getIntent();
        title = intent.getStringExtra(KEY_ROOM_NAME);
        id = intent.getStringExtra(KEY_ROOM_ID);

        if(intent.hasExtra(EXTRA_PAGER)) {
            pagerPosition = intent.getIntExtra(EXTRA_PAGER, 0);
        } else {
            pagerPosition = 0;
        }

        initToolbar();
        setViewPager();

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeviceControlActivity.this, RoomActivity.class);
                intent.putExtra(KEY_ROOM_NAME, title);
                intent.putExtra(KEY_ROOM_ID, id);

                startActivity(intent);
                finish();
            }
        });
    }

    public void setViewPager() {
        mViewPager = findViewById(R.id.pager);
        mViewPagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(pagerPosition);
        mTabLayout = findViewById(R.id.tab);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);
    }
}

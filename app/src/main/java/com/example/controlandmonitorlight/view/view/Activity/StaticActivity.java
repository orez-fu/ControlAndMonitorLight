package com.example.controlandmonitorlight.view.view.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.adapter.ViewPagerStaticAdapter;
import com.example.controlandmonitorlight.adapter.ViewpagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class StaticActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPagerStaticAdapter viewPagerStaticAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_static);
        Mapping();
        initToolbar();
        setViewPager();
    }

    public void Mapping()
    {
        mViewPager = findViewById(R.id.pagerstatic);
        mTabLayout = findViewById(R.id.tab1);
        mTabLayout.setupWithViewPager(mViewPager);
        mToolbar = findViewById(R.id.toolbar1);
    }
    public void setViewPager() {
        viewPagerStaticAdapter = new ViewPagerStaticAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(viewPagerStaticAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
    public void initToolbar()
    {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("STATICS");
    }
}

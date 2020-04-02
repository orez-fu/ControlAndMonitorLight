package com.example.controlandmonitorlight.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.controlandmonitorlight.view.view.Fragment.ControlFragment;
import com.example.controlandmonitorlight.view.view.Fragment.DayFragment;
import com.example.controlandmonitorlight.view.view.Fragment.TimeFragment;
import com.example.controlandmonitorlight.view.view.Fragment.WeekFragment;

public class ViewPagerStaticAdapter extends FragmentPagerAdapter {
    private static int TAB_COUNT = 2;

    public ViewPagerStaticAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return DayFragment.newInstance();
            case 1:
                return WeekFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return DayFragment.TITLE;
            case 1:
                return WeekFragment.TITLE;
        }
        return super.getPageTitle(position);
    }
}

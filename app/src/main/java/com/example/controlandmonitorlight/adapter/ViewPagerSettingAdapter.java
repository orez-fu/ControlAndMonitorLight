package com.example.controlandmonitorlight.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.controlandmonitorlight.view.view.Fragment.ProfileFragment;

public class ViewPagerSettingAdapter extends FragmentStatePagerAdapter {
    private static int TAB_COUNT = 2;

    public ViewPagerSettingAdapter(FragmentManager manager) {
        super(manager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ProfileFragment.newInstance();
            case 1:
                return null;
        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}

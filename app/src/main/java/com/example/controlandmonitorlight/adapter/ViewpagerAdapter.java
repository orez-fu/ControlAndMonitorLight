/*ViewpagerAdapter*/
package com.example.controlandmonitorlight.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.controlandmonitorlight.view.view.Fragment.ControlFragment;
import com.example.controlandmonitorlight.view.view.Fragment.TimerFragment;

public class ViewpagerAdapter extends FragmentStatePagerAdapter {
    private static int TAB_COUNT = 2;

    public ViewpagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return ControlFragment.newInstance();
            case 1:
                return TimerFragment.newInstance();
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
                return ControlFragment.TITLE;
            case 1:
                return TimerFragment.TITLE;
        }
        return super.getPageTitle(position);
    }

}


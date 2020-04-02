package com.example.controlandmonitorlight.view.view.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.controlandmonitorlight.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayFragment extends Fragment {

    public static final String TITLE = "Day";
    public static DayFragment newInstance() {
        return new DayFragment();
    }

    public DayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day, container, false);
    }

}

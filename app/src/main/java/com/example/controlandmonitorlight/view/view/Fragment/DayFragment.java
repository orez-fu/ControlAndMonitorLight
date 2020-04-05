package com.example.controlandmonitorlight.view.view.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.model.RoomStatic;
import com.example.controlandmonitorlight.viewmodel.MonitorViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayFragment extends Fragment {

    public static final String TITLE = "Day";
    public static DayFragment newInstance() {
        return new DayFragment();
    }
    TextView mTotal;
    public DayFragment() {
        // Required empty public constructor
    }
    MonitorViewModel monitorViewModel ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        mTotal = view.findViewById(R.id.total);
        monitorViewModel = ViewModelProviders.of(DayFragment.this).get(MonitorViewModel.class);
        int id = 1 ;
        monitorViewModel.getData(id);
        monitorViewModel.data.observe(this, new Observer<RoomStatic>() {
            @Override
            public void onChanged(RoomStatic aRoomStatic) {
                mTotal.setText(aRoomStatic.getTotalWatt()+"");
            }
        });
        return view;
    }

}

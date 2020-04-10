package com.example.controlandmonitorlight.view.view.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.Repository.DeviceInterface;
import com.example.controlandmonitorlight.model.DeviceStatic;
import com.example.controlandmonitorlight.model.RoomStatic;
import com.example.controlandmonitorlight.viewmodel.DeviceStaticViewModel;
import com.example.controlandmonitorlight.viewmodel.MonitorViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayFragment extends Fragment {

    public static final String TITLE = "Day";
    public static DayFragment newInstance() {
        return new DayFragment();
    }
    TextView mTotal;
    TextView mTimeOn ;
    TextView mTimeNow;
    public DayFragment() {
        // Required empty public constructor
    }
    MonitorViewModel monitorViewModel ;
    DeviceStaticViewModel deviceStaticViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        mTotal = view.findViewById(R.id.total);
        mTimeNow= view.findViewById(R.id.txt_metadata);
        mTimeOn = view.findViewById(R.id.electric);
        monitorViewModel = ViewModelProviders.of(DayFragment.this).get(MonitorViewModel.class);
        deviceStaticViewModel = ViewModelProviders.of(DayFragment.this).get(DeviceStaticViewModel.class);
        int id1 = 1 ;

        deviceStaticViewModel.getData(id1, DeviceInterface.DAY,getContext());
        Log.d("co","abc");
        deviceStaticViewModel.deviceData.observe(this, new Observer<DeviceStatic>() {
            @Override
            public void onChanged(DeviceStatic deviceStatic) {
             //   Log.d("co","abc");
                Log.d("fragment=",deviceStatic.getRecords().size()+" "+deviceStatic.getTotalWatt()+" "+deviceStatic.getTimeOn());
                //mTotal.setText(deviceStatic.getTotalWalt()+" "+deviceStatic.getTimeOn());
                mTotal.setText("TotalWatt: "+deviceStatic.getTotalWatt()+"");
                mTimeOn.setText("TimeOn: "+deviceStatic.getTimeOn()+"");
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                mTimeNow.setText("Time is now: "+currentTime);

            }
        });
        int id = 1 ;
        monitorViewModel.getData(id);
        monitorViewModel.data.observe(this, new Observer<RoomStatic>() {
            @Override
            public void onChanged(RoomStatic aRoomStatic) {
                //mTotal.setText(aRoomStatic.getTotalWatt()+"");

            }
        });
        return view;
    }

}

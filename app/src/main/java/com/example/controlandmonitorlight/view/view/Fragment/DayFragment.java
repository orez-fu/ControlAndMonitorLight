package com.example.controlandmonitorlight.view.view.Fragment;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.Repository.DeviceInterface;
import com.example.controlandmonitorlight.adapter.CustomDateManagement;
import com.example.controlandmonitorlight.model.DeviceStatic;
import com.example.controlandmonitorlight.model.RoomStatic;
import com.example.controlandmonitorlight.viewmodel.DeviceStaticViewModel;
import com.example.controlandmonitorlight.viewmodel.MonitorViewModel;
import com.example.controlandmonitorlight.viewmodel.TimeManagement;
import com.github.mikephil.charting.charts.LineChart;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayFragment extends Fragment implements TimeManagement {

    public static final String TITLE = "Day";
    public static DayFragment newInstance() {
        return new DayFragment();
    }
    TextView mTotal;
    TextView mTimeOn ;
    TextView mTimeNow;
    ImageView mCanlender ;
    TextView mTextOptionsTime;
    LineChart lineChart;
    Map<String, String> parameters = new HashMap<>();
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
        mCanlender = view.findViewById(R.id.calender);
        mTextOptionsTime = view.findViewById(R.id.text_time);
        monitorViewModel = ViewModelProviders.of(DayFragment.this).get(MonitorViewModel.class);
        deviceStaticViewModel = ViewModelProviders.of(DayFragment.this).get(DeviceStaticViewModel.class);
        int id1 = 1 ;
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        this.parameters.put("day",today.monthDay+"");
        this.parameters.put("month",today.month+1+"");
        this.parameters.put("year",today.year+"");
        System.out.println(Arrays.asList(parameters));
        deviceStaticViewModel.getData(id1,parameters ,getContext());
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
    public void PickDate() {
        mCanlender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment customDateManagement= new CustomDateManagement(DayFragment.this);
                customDateManagement.setCancelable(false);
                customDateManagement.show(getFragmentManager(),"SCHEDULE");
            }
        });
    }

    @Override
    public void onSetTime(TimePicker view, int hourOfDay, int minute) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mTextOptionsTime.setText(String.valueOf(dayOfMonth)+"/"+String.valueOf(month)+"/"+String.valueOf(year));
    }
}

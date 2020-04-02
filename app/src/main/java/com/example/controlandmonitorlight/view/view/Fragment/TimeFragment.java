/*view*/ /*fragment*/
package com.example.controlandmonitorlight.view.view.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.controlandmonitorlight.Helper.MyButtonClickListener;
import com.example.controlandmonitorlight.Helper.MySwipeHelper;
import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.adapter.CustomDateManagement;
import com.example.controlandmonitorlight.adapter.CustomTimeManagement;
import com.example.controlandmonitorlight.adapter.TimeAdapter;
import com.example.controlandmonitorlight.model.Time;
import com.example.controlandmonitorlight.viewmodel.DateViewModel;
import com.example.controlandmonitorlight.viewmodel.TimeManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeFragment extends Fragment implements TimeManagement   {

    public static final String TITLE = "Schedule";
    Button mPick ;
    TextView mTime ;
    DateViewModel dateViewModel;
    RecyclerView recyclerView ;
    TimeAdapter timeAdapter ;
    CustomTimeManagement customTimeManagement;
    String time ;
    List<Time>test = new ArrayList<>();
    public static TimeFragment newInstance() {
        return new TimeFragment();
    }

    public TimeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time, container, false); ;
        mPick = view.findViewById(R.id.pick);
        mTime = view.findViewById(R.id.time);
        recyclerView = view.findViewById(R.id.listview);

        dateViewModel = ViewModelProviders.of(this).get(DateViewModel.class);
        mPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new CustomTimeManagement(TimeFragment.this);
                dialogFragment.setCancelable(false);
                dialogFragment.show(getFragmentManager(),"SCHEDULE");
               // DialogFragment dialogFragment1 = new CustomDateManagement(TimeFragment.this);
               // dialogFragment.setCancelable(false);
            }
        });

        return view ;
    }



    @Override
    public void onSetTime(TimePicker view, int hourOfDay, int minute) {
        time = String.valueOf(hourOfDay)+" : "+String.valueOf(minute);
        /*dateViewModel.onTimeSet(time);
        dateViewModel.getData().observe(this, new Observer<List<Time>>() {
            @Override
            public void onChanged(List<Time> times) {

                String time = times.get(0).getTime() + " : "+times.get(0).getDate();

            }
        });
         */
        test.add(new Time(time,"")) ;
        initRecyclerview(test);



    }
    void initRecyclerview(List<Time>times)
    {
        timeAdapter = new TimeAdapter(times,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(timeAdapter);

        MySwipeHelper mySwipeHelper = new MySwipeHelper(getContext(),recyclerView,200) {
            @Override
            public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<MySwipeHelper.MyButton> buffer) {
                buffer.add(new MyButton(getActivity(), "Edit", 30, 0, Color.parseColor("#FF3C30"), new MyButtonClickListener() {
                    @Override
                    public void onclick(int pos) {
                        Toast.makeText(getContext(),"Edit",Toast.LENGTH_SHORT).show();
                    }
                }));
                buffer.add(new MyButton(getActivity(), "Delete", 30, 0, Color.parseColor("#FF9502"), new MyButtonClickListener() {
                    @Override
                    public void onclick(int pos) {
                        Toast.makeText(getContext(),"Delete",Toast.LENGTH_SHORT).show();
                    }
                }));
            }
        };
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = String.valueOf(dayOfMonth)+"/"+String.valueOf(month)+"/"+String.valueOf(year);

    }
}

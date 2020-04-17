package com.example.controlandmonitorlight.view.view.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.adapter.CustomDateManagement;
import com.example.controlandmonitorlight.adapter.ItemRoomAdapter;
import com.example.controlandmonitorlight.model.StaticModel;
import com.example.controlandmonitorlight.viewmodel.StaticTotalViewModel;
import com.example.controlandmonitorlight.viewmodel.TimeManagement;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class StaticFragment extends Fragment implements TimeManagement {

    FirebaseUser user;
    RecyclerView recyclerView ;
    ItemRoomAdapter itemRoomAdapter;
    StaticTotalViewModel staticTotalViewModel ;
    TextView txtCalender;
    LinearLayout layoutPickDate;
    Calendar mCalender;
    private int day , month, year ;
    private String userId;
    public StaticFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_static, container, false);

        recyclerView = view.findViewById(R.id.recycle);
        txtCalender =  view.findViewById(R.id.txt_calendar);
        layoutPickDate = view.findViewById(R.id.layout_pick_date);

        mCalender = Calendar.getInstance();
        day = mCalender.get(Calendar.DAY_OF_MONTH) ;
        month = mCalender.get(Calendar.MONTH) ;
        year = mCalender.get(Calendar.YEAR);

        txtCalender.setText(day+"/"+(month+1)+"/"+year);

        user = FirebaseAuth.getInstance().getCurrentUser() ;
        userId = user.getUid();

        pickDate();

        staticTotalViewModel = ViewModelProviders.of(this).get(StaticTotalViewModel.class);
        staticTotalViewModel.getStaticData(userId,day,month,year);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        itemRoomAdapter = new ItemRoomAdapter(getContext());

        recyclerView.setAdapter(itemRoomAdapter);

        staticTotalViewModel.dataStatic.observe(getActivity(), new Observer<StaticModel>() {
            @Override
            public void onChanged(StaticModel staticModel) {
                Log.d("FIX_PASS_DAY", "Mutable: " + staticTotalViewModel.mCalendar.getValue().toString());
                itemRoomAdapter.setRoomStaticList(
                        staticModel.getRooms(),
                        staticTotalViewModel.mCalendar.getValue());
            }
        });

        return view ;
    }
    private void pickDate() {
        layoutPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment customDateManagement= new CustomDateManagement(StaticFragment.this);
                customDateManagement.setCancelable(false);
                customDateManagement.show(getFragmentManager(),"SCHEDULE");
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.d("FIX_PASS_DAY", "Receive: " + year+"|"+month+"|"+dayOfMonth);
        txtCalender.setText(String.valueOf(dayOfMonth)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
        staticTotalViewModel.getStaticData(userId,dayOfMonth,(month+1),year);
    }
}

/*view*/ /*fragment*/
package com.example.controlandmonitorlight.view.view.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.controlandmonitorlight.Helper.MyButtonClickListener;
import com.example.controlandmonitorlight.Helper.MySwipeHelper;
import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.adapter.TimerAdapter;
import com.example.controlandmonitorlight.model.Timer;
import com.example.controlandmonitorlight.view.view.Activity.AddEditTimerActivity;
import com.example.controlandmonitorlight.viewmodel.TimerViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class TimeFragment extends Fragment {
    public static final String TAG = "TIMER_FRAGMENT";

    public static final String TITLE = "Schedule";
    public static final int ADD_TIMER_REQUEST = 1001;

    // UI variables
    private FloatingActionButton btnAddTimer;
    private TimerViewModel timerViewModel;
    private RecyclerView recyclerView;
    private TimerAdapter timerAdapter;

    public static TimeFragment newInstance() {
        return new TimeFragment();
    }

    public TimeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_timer, container, false);

        recyclerView = rootView.findViewById(R.id.rcv_timer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        timerViewModel = ViewModelProviders.of(requireActivity()).get(TimerViewModel.class);
        timerViewModel.init();
        timerViewModel.getTimers().getValue();


        timerAdapter = new TimerAdapter(timerViewModel.getTimers().getValue());

        timerViewModel.getTimers().observe(requireActivity(), new Observer<List<Timer>>() {
            @Override
            public void onChanged(List<Timer> timers) {
                timerAdapter.notifyDataSetChanged();
            }
        });


        recyclerView.setAdapter(timerAdapter);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnAddTimer = view.findViewById(R.id.btn_add_timer);

        btnAddTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddEditTimerActivity.class);
                startActivityForResult(intent, ADD_TIMER_REQUEST);
            }
        });
    }

}

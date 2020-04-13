package com.example.controlandmonitorlight.view.view.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.adapter.TimerAdapter;
import com.example.controlandmonitorlight.model.TimerModel;
import com.example.controlandmonitorlight.repositories.RealtimeFirebaeRepository;
import com.example.controlandmonitorlight.view.view.Activity.AddEditTimerActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class TimeFragment extends Fragment {
    private static final String TAG = "TIMER_FRAGMENT";

    public static final String TITLE = "Schedule";
    public static final int ADD_TIMER_REQUEST = 10001;
    public static final int EDIT_TIMER_REQUEST = 10002;

    // UI variables
    private FloatingActionButton btnAddTimer;
    private RecyclerView recyclerView;
    private TimerAdapter timerAdapter;

    // Data variables
    List<TimerModel> timers;
    DatabaseReference reference;

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

        reference = FirebaseDatabase.getInstance().getReference();

        recyclerView = rootView.findViewById(R.id.rcv_timer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadingData();

        return rootView;
    }

    private void loadingData() {
        if(reference != null) {
            Query query = reference.child("timer").child(String.valueOf(1));
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    timers = new ArrayList<>();
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        Log.d(TAG, snapshot.getValue(TimerModel.class).getLabel() + ": " + snapshot.getValue(TimerModel.class).getTime());
                        timers.add(snapshot.getValue(TimerModel.class));
                    }
                    timerAdapter = new TimerAdapter(timers);
                    recyclerView.setAdapter(timerAdapter);

                    new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                            Toast.makeText(getContext(), "Note deleted " + String.valueOf(direction), Toast.LENGTH_SHORT).show();
                        }
                    }).attachToRecyclerView(recyclerView);

                    timerAdapter.setOnItemClickListener(new TimerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(TimerModel timer) {
                            Intent intent = new Intent(getContext(), AddEditTimerActivity.class);
                            intent.putExtra(AddEditTimerActivity.EXTRA_ID, timer.getId());
                            intent.putExtra(AddEditTimerActivity.EXTRA_HOUR, timer.getHour());
                            intent.putExtra(AddEditTimerActivity.EXTRA_MINUTE, timer.getMinute());
                            intent.putExtra(AddEditTimerActivity.EXTRA_STATUS, timer.getStatus());
                            intent.putExtra(AddEditTimerActivity.EXTRA_REPEAT, timer.getRepeat());
                            intent.putExtra(AddEditTimerActivity.EXTRA_TYPE, timer.getType());
                            intent.putExtra(AddEditTimerActivity.EXTRA_LABEL, timer.getLabel());

                            startActivityForResult(intent, EDIT_TIMER_REQUEST);
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(TAG, "Fail read data");
                    Log.d(TAG, databaseError.toString());
                }
            });
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_TIMER_REQUEST && resultCode == RESULT_OK) {
            int hour = data.getIntExtra(AddEditTimerActivity.EXTRA_HOUR, 0);
            int minute = data.getIntExtra(AddEditTimerActivity.EXTRA_MINUTE, 0);
            String repeat = data.getStringExtra(AddEditTimerActivity.EXTRA_REPEAT);
            String label = data.getStringExtra(AddEditTimerActivity.EXTRA_LABEL);
            String type = data.getStringExtra(AddEditTimerActivity.EXTRA_TYPE);
            int status = data.getIntExtra(AddEditTimerActivity.EXTRA_STATUS, 0);

            RealtimeFirebaeRepository.getInstance().addNewTimer(1,
                    new TimerModel(hour, minute, repeat, label, type, status));

        } else if (EDIT_TIMER_REQUEST == requestCode && RESULT_OK == resultCode) {
            String id = data.getStringExtra(AddEditTimerActivity.EXTRA_ID);
            if(id == null) {
                Toast.makeText(getActivity(), "Note can't be update", Toast.LENGTH_SHORT).show();
                return;
            }

            int hour = data.getIntExtra(AddEditTimerActivity.EXTRA_HOUR, 0);
            int minute = data.getIntExtra(AddEditTimerActivity.EXTRA_MINUTE, 0);
            String repeat = data.getStringExtra(AddEditTimerActivity.EXTRA_REPEAT);
            String label = data.getStringExtra(AddEditTimerActivity.EXTRA_LABEL);
            String type = data.getStringExtra(AddEditTimerActivity.EXTRA_TYPE);
            int status = data.getIntExtra(AddEditTimerActivity.EXTRA_STATUS, 0);

            RealtimeFirebaeRepository.getInstance().updateTimer(1, id,
                    new TimerModel(hour, minute, repeat, label, type, status));
            Toast.makeText(getActivity(), "Note updated", Toast.LENGTH_SHORT).show();

        }
    }
}

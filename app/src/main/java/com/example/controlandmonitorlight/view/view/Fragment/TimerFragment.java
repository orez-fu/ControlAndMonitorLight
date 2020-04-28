package com.example.controlandmonitorlight.view.view.Fragment;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.controlandmonitorlight.MainActivity;
import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.adapter.TimerAdapter;
import com.example.controlandmonitorlight.model.TimerModel;
import com.example.controlandmonitorlight.repositories.RealtimeFirebaseRepository;
import com.example.controlandmonitorlight.view.view.Activity.AddEditTimerActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import www.sanju.motiontoast.MotionToast;

import static android.app.Activity.RESULT_OK;
import static com.example.controlandmonitorlight.MainActivity.KEY_ROOM_ID;
import static com.example.controlandmonitorlight.MainActivity.KEY_ROOM_NAME;
import static com.example.controlandmonitorlight.view.view.Activity.RoomActivity.KEY_DEVICE_ID;


public class TimerFragment extends Fragment {
    private static final String TAG = "TIMER_FRAGMENT";

    public static final String TITLE = "Schedule";
    public static final int ADD_TIMER_REQUEST = 10001;
    public static final int EDIT_TIMER_REQUEST = 10002;

    // UI variables
    private FloatingActionButton btnAddTimer;
    private RecyclerView recyclerView;
    private TimerAdapter timerAdapter;

    // DeviceDataModel variables
    private List<TimerModel> timers;
    private DatabaseReference reference;
    private String deviceId;
    private String roomId;
    private String roomName;

    public static TimerFragment newInstance() {
        return new TimerFragment();
    }

    public TimerFragment() {
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


        Intent intent = getActivity().getIntent();
        deviceId = intent.getStringExtra(KEY_DEVICE_ID);
        roomId = intent.getStringExtra(KEY_ROOM_ID);
        roomName = intent.getStringExtra(KEY_ROOM_NAME);

        reference = FirebaseDatabase.getInstance().getReference();

        recyclerView = rootView.findViewById(R.id.rcv_timer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadingData();

        return rootView;
    }

    private void loadingData() {
        if(reference != null) {
            Query query = reference.child("timer").child(deviceId);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    timers = new ArrayList<>();
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        Log.d(TAG, snapshot.toString());
                        Log.d(TAG, snapshot.getValue(TimerModel.class).getLabel() + ": " + snapshot.getValue(TimerModel.class).getTime());
                        timers.add(snapshot.getValue(TimerModel.class));
                    }
                    timerAdapter = new TimerAdapter(timers);
                    recyclerView.setAdapter(timerAdapter);
//
                    new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                            final int position = viewHolder.getAdapterPosition();
                            final String deviceId = timers.get(position).getDeviceId();
                            final TimerModel deletedTimer = timers.get(position);

                            RealtimeFirebaseRepository.getInstance().deleteTimer(deviceId, timers.get(position).getId());
                            timers.remove(viewHolder.getAdapterPosition());

                            Snackbar.make(getView(), deletedTimer.getLabel(), Snackbar.LENGTH_LONG)
                                    .setAction("Undo", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            RealtimeFirebaseRepository.getInstance().addNewTimer(deviceId, deletedTimer);
                                        }
                                    }).show();

                            timerAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                            new RecyclerViewSwipeDecorator.Builder(getContext(), c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorDelete))
                                    .addSwipeLeftActionIcon(R.drawable.ic_delete_black_24dp)
                                    .create()
                                    .decorate();
                            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        }
                    }).attachToRecyclerView(recyclerView);
//
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
                            intent.putExtra(KEY_ROOM_ID, roomId);
                            intent.putExtra(KEY_DEVICE_ID, deviceId);
                            intent.putExtra(KEY_ROOM_NAME, roomName);

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
                intent.putExtra(KEY_ROOM_ID, roomId);
                intent.putExtra(KEY_DEVICE_ID, deviceId);
                intent.putExtra(KEY_ROOM_NAME, roomName);

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

            RealtimeFirebaseRepository.getInstance().addNewTimer(deviceId,
                    new TimerModel(roomId, deviceId, hour, minute, repeat, label, type, status));
            MotionToast.Companion.createColorToast(getActivity(),"Note adding completed!",
                    MotionToast.Companion.getTOAST_SUCCESS(),
                    MotionToast.Companion.getGRAVITY_BOTTOM(),
                    MotionToast.Companion.getLONG_DURATION(),
                    ResourcesCompat.getFont(getContext(), R.font.helvetica_regular));

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

            RealtimeFirebaseRepository.getInstance().updateTimer(deviceId, id,
                    new TimerModel(roomId, deviceId, hour, minute, repeat, label, type, status));
            MotionToast.Companion.createColorToast(getActivity(),"Note updated!",
                    MotionToast.Companion.getTOAST_SUCCESS(),
                    MotionToast.Companion.getGRAVITY_BOTTOM(),
                    MotionToast.Companion.getLONG_DURATION(),
                    ResourcesCompat.getFont(getContext(), R.font.helvetica_regular));

        }
    }
}

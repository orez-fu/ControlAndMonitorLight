package com.example.controlandmonitorlight.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.controlandmonitorlight.model.TimerModel;
import com.example.controlandmonitorlight.repositories.RealtimeRepository;

import java.util.List;

public class TimerViewModel extends ViewModel  {
    private static final String TAG = "TIMER_VIEW_MODEL";

    private MutableLiveData<List<TimerModel>> timers;
    private RealtimeRepository realtimeRepository;

    public void init() {
        timers = RealtimeRepository.getInstance().getTimers();
    }

    public MutableLiveData<List<TimerModel>> getTimers() {
        if(timers == null) {
            timers = new MutableLiveData<>();
        }
        Log.d(TAG, "Retrieve once with size: " + timers.getValue().size());
        return timers;
    }
}

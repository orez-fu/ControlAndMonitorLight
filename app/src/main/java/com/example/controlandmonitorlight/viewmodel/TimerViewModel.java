package com.example.controlandmonitorlight.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.controlandmonitorlight.model.Timer;
import com.example.controlandmonitorlight.repositories.RealtimeRepository;

import java.util.ArrayList;
import java.util.List;

public class TimerViewModel extends ViewModel  {
    private static final String TAG = "TIMER_VIEW_MODEL";

    private MutableLiveData<List<Timer>> timers;
    private RealtimeRepository realtimeRepository;

    public void init() {
        timers = RealtimeRepository.getInstance().getTimers();
    }

    public MutableLiveData<List<Timer>> getTimers() {
        if(timers == null) {
            timers = new MutableLiveData<>();
        }
        Log.d(TAG, "Retrieve once with size: " + timers.getValue().size());
        return timers;
    }
}

package com.example.controlandmonitorlight.view.view.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.controlandmonitorlight.R;

import java.util.Calendar;
import java.util.Date;


public class AddEditTimerActivity extends AppCompatActivity {
    public static final String TAG = "ADD_EDIT_TIMER";

    private static final int INIT_DISPLAY_TIME = 1;
    private static final int UPDATE_DISPLAY_TIME = 2;

    private Toolbar mToolbar;

    // UI variables
    private EditText edtLabel;
    private TimePicker timePicker;
    private TextView mTimeDisplay;

    // Data variables
    private int mHour;
    private int mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_timer);
//
        mToolbar = findViewById(R.id.add_toolbar);
        initToolbar();

        edtLabel = findViewById(R.id.edt_label);
        timePicker = findViewById(R.id.time_picker);
        mTimeDisplay = findViewById(R.id.txt_time);

        updateTextTime(INIT_DISPLAY_TIME);



        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                updateTextTime(UPDATE_DISPLAY_TIME);
            }
        });

    }

    public void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Timer");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_timer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_timer:
                saveTimer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveTimer() {
        String label = edtLabel.getText().toString();

    }

    private void updateTextTime(int flag) {
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        int minute = rightNow.get(Calendar.MINUTE);


        if(flag == INIT_DISPLAY_TIME) {
            mHour = hour;
            mMinute = minute;
        }

        Log.d(TAG, "TIME NOW: " + hour + ":" + minute);
        Log.d(TAG, "TIME Pick: " + mHour + ":" + mMinute);

        int nowMinutes = hour * 60 + minute;
        int changeMinutes = mHour * 60 + mMinute;
        int result;
        if(changeMinutes >= nowMinutes) {
            result = changeMinutes - nowMinutes;
        } else {
            result = 60 * 24 - nowMinutes + changeMinutes;
        }

        String timeString = "";
        timeString += "Alarm in " + String.valueOf(result / 60) + " hours " + String.valueOf(result % 60) + " minutes";
        mTimeDisplay.setText(timeString);
    }
}

package com.example.controlandmonitorlight.view.view.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.boardcast.TimerReceiver;
import com.example.controlandmonitorlight.model.TimerModel;

import java.util.Calendar;

import static com.example.controlandmonitorlight.MainActivity.KEY_ROOM_ID;
import static com.example.controlandmonitorlight.MainActivity.KEY_ROOM_NAME;
import static com.example.controlandmonitorlight.view.view.Activity.RoomActivity.KEY_DEVICE_ID;


public class AddEditTimerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String TAG = "ADD_EDIT_TIMER";

    public static final String EXTRA_ID = "com.example.controlandmonitorlight.view.view.Activity.EXTRA_ID";
    public static final String EXTRA_HOUR = "com.example.controlandmonitorlight.view.view.Activity.EXTRA_HOUR";
    public static final String EXTRA_MINUTE = "com.example.controlandmonitorlight.view.view.Activity.EXTRA_MINUTE";
    public static final String EXTRA_REPEAT = "com.example.controlandmonitorlight.view.view.Activity.EXTRA_REPEAT";
    public static final String EXTRA_LABEL = "com.example.controlandmonitorlight.view.view.Activity.EXTRA_LABEL";
    public static final String EXTRA_TYPE = "com.example.controlandmonitorlight.view.view.Activity.EXTRA_TYPE";
    public static final String EXTRA_STATUS = "com.example.controlandmonitorlight.view.view.Activity.EXTRA_STATUS";

    private static final int INIT_DISPLAY_TIME = 1;
    private static final int UPDATE_DISPLAY_TIME = 2;

    private Toolbar mToolbar;

    // UI variables
    private EditText edtLabel;
    private TimePicker timePicker;
    private TextView mTimeDisplay;
    private Spinner spinner;
    private RadioGroup radioGroup;
    private RadioButton radioButtonOn;
    private RadioButton radioButtonOff;
    private SwitchCompat switchCompat;

    // DeviceDataModel variables
    private int mHour;
    private int mMinute;
    private String mRepeat;
    private String mLabel;
    private int mStatus;
    private String mAction;
    private String roomId;
    private String deviceId;
    private String roomName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_timer);

        mToolbar = findViewById(R.id.add_toolbar);

        edtLabel = findViewById(R.id.edt_label);
        timePicker = findViewById(R.id.time_picker);
        mTimeDisplay = findViewById(R.id.txt_time);
        spinner = findViewById(R.id.spinner_repeat);
        radioGroup = findViewById(R.id.radio_action);
        switchCompat = findViewById(R.id.switch_status_timer);
        radioButtonOn = findViewById(R.id.radio_turn_on);
        radioButtonOff = findViewById(R.id.radio_turn_off);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.repeats,
                android.R.layout.simple_spinner_item
        );

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(this);

        updateTextTime(INIT_DISPLAY_TIME);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                updateTextTime(UPDATE_DISPLAY_TIME);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId == R.id.radio_turn_on) {
                    mAction = TimerModel.TYPE_ON;
                } else {
                    mAction = TimerModel.TYPE_OFF;
                }
            }
        });

        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchCompat.isChecked()) {
                    mStatus = TimerModel.STATUS_ON;
                } else {
                    mStatus = TimerModel.STATUS_OFF;
                }
            }
        });

        final Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)) {
            initToolbar("Update Timer");
            timePicker.setHour(intent.getIntExtra(EXTRA_HOUR, 0));
            timePicker.setMinute(intent.getIntExtra(EXTRA_MINUTE, 0));
            edtLabel.setText(intent.getStringExtra(EXTRA_LABEL));

            mRepeat = intent.getStringExtra(EXTRA_REPEAT);
            if(TimerModel.REPEAT_ONCE.equals(mRepeat)) {
                spinner.setSelection(0);
            } else {
                spinner.setSelection(1);
            }

            mAction = intent.getStringExtra(EXTRA_TYPE);
            if(TimerModel.TYPE_ON.equals(mAction)) {
                radioGroup.check(radioButtonOn.getId());
            } else {
                radioGroup.check(radioButtonOff.getId());
            }

            mStatus = intent.getIntExtra(EXTRA_STATUS, 1);
            switchCompat.setChecked(mStatus == TimerModel.STATUS_ON);
        } else {
            initToolbar("Add Timer");
            mRepeat = TimerModel.REPEAT_ONCE;
            spinner.setSelection(0);

            mAction = TimerModel.TYPE_ON;
            radioGroup.check(radioButtonOn.getId());

            mStatus = TimerModel.STATUS_ON;
            switchCompat.setChecked(true);
        }

        deviceId = intent.getStringExtra(KEY_DEVICE_ID);
        roomId = intent.getStringExtra(KEY_ROOM_ID);
        roomName = intent.getStringExtra(KEY_ROOM_NAME);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(AddEditTimerActivity.this, DeviceControlActivity.class);
                intentBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentBack.putExtra(KEY_ROOM_ID, roomId);
                intentBack.putExtra(KEY_DEVICE_ID, deviceId);
                intentBack.putExtra(DeviceControlActivity.EXTRA_PAGER, 1);
                intentBack.putExtra(KEY_ROOM_NAME, roomName);

                startActivity(intentBack);
                finish();
            }
        });

    }

    public void initToolbar(String title) {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);
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
        mLabel = edtLabel.getText().toString();

        Intent data = new Intent();
        data.putExtra(EXTRA_HOUR, mHour);
        data.putExtra(EXTRA_MINUTE, mMinute);
        data.putExtra(EXTRA_TYPE, mAction);
        data.putExtra(EXTRA_STATUS, mStatus);
        data.putExtra(EXTRA_REPEAT, mRepeat);
        data.putExtra(EXTRA_LABEL, mLabel);
        data.putExtra(KEY_ROOM_NAME, roomName);
        data.putExtra(KEY_ROOM_ID, roomId);
        data.putExtra(KEY_DEVICE_ID, deviceId);
        data.putExtra(DeviceControlActivity.EXTRA_PAGER, 1);

        String id = getIntent().getStringExtra(EXTRA_ID);
        if(id != null) {
            data.putExtra(EXTRA_ID, id);
        } else {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent alarmIntent = new Intent(this, TimerReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, alarmIntent, 0);

            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, mHour);
            c.set(Calendar.MINUTE, mMinute);
            c.set(Calendar.SECOND, 0);

            if(c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE, 1);
            }

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        }


        setResult(RESULT_OK, data);
        finish();
    }

    private void updateTextTime(int flag) {
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        int minute = rightNow.get(Calendar.MINUTE);


        if(flag == INIT_DISPLAY_TIME) {
            mHour = hour;
            mMinute = minute;
        }

//        Log.d(TAG, "TIME NOW: " + hour + ":" + minute);
//        Log.d(TAG, "TIME Pick: " + mHour + ":" + mMinute);

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String text = adapterView.getItemAtPosition(position).toString();
        if(text.equals("Once")) {
            mRepeat = TimerModel.REPEAT_ONCE;
        } else {
            mRepeat = TimerModel.REPEAT_EVERY;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

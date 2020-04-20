package com.example.controlandmonitorlight.view.view.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.model.DeviceStaticModel;
import com.example.controlandmonitorlight.model.RecordModel;
import com.example.controlandmonitorlight.utils.ConvertTime;
import com.example.controlandmonitorlight.utils.ConvertTimeOn;
import com.example.controlandmonitorlight.utils.ConvertWatt;
import com.github.mikephil.charting.charts.LineChart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DeviceStaticActivity extends AppCompatActivity {
    private String TAG = "DEVICE_STATIC_ACTIVITY";
    private TextView txtDate, txtHourMinute, txtTotalWatt;
    private LineChart lineChart;
    private DeviceStaticModel deviceStaticModel;
    private int day, month, year;
    private Toolbar toolbar;
    private ArrayList<Entry> dataTurnLight = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_static);

        mapping();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Gson gson = new Gson();
        String gson1 = getIntent().getStringExtra("rules");
        Type type = new TypeToken<DeviceStaticModel>() {
        }.getType();
        day = getIntent().getIntExtra("EXTRA_DAY", 0);
        month = getIntent().getIntExtra("EXTRA_MONTH", 1);
        year = getIntent().getIntExtra("EXTRA_YEAR", 2);

        Log.d("DAY_MONTH_YEAR", day + "." + month + "." + year);
//        DeviceStaticModel rules = gson.fromGson(gson, type);
        deviceStaticModel = gson.fromJson(gson1, type);
        showInformation();
        processData();
        drawLineChart();
        Log.d("DEVICE_STATIC", deviceStaticModel.getDeviceId());

    }

    void showInformation() {
        txtDate.setText(deviceStaticModel.getDeviceId());
        txtHourMinute.setText(ConvertTimeOn.convertTimeOn(deviceStaticModel.getTimeOn()));
        txtTotalWatt.setText(ConvertWatt.convertWatt(deviceStaticModel.getTotalWatt()));
    }

    void processData() {
        long timeStartDate = ConvertTime.convertDateTimeToUnix(year, month, day, 0, 0, 0) / 1000;
        long timeEndDate = timeStartDate + 86400;

        List<RecordModel> listRecord = new ArrayList<>();
        listRecord = deviceStaticModel.getRecords();

        List<Integer> listTurn = new ArrayList<>();
        for (int i = 0; i <= 1000000; i++) listTurn.add(0);
        for (RecordModel item : listRecord) {
            long currentTimeStamp = (long) item.getTimestamp() / 1000;
            int currentTimeOn = item.getTimeOn();
            for (int i = 0; i < currentTimeOn; i++) {
                int index = (int) (currentTimeStamp - i - timeStartDate);

                Log.d(TAG, currentTimeStamp + "-" + i + "-" + timeStartDate + "= " + index);
                if (index < 0) break;
                listTurn.set(index, 1);
            }
        }
        dataTurnLight.add(new Entry(0, listTurn.get(0)));
        for (int i = 1; i <= 86399; i++)
            if (listTurn.get(i) != listTurn.get(i - 1)) {
                if (listTurn.get(i) == 0) {
                    dataTurnLight.add(new Entry(i - 1, 0));
                    dataTurnLight.add(new Entry(i, 0));
                } else {
                    dataTurnLight.add(new Entry(i, 0));
                    dataTurnLight.add(new Entry(i, 1));
                }
            } else dataTurnLight.add(new Entry(i, listTurn.get(i)));
    }

    void drawLineChart() {
        // Month start 0 - 11

        // Left Axis
        YAxis left = lineChart.getAxisLeft();
        left.setDrawLabels(true); // no axis labels
        left.setDrawAxisLine(true); // no axis line
        left.setDrawGridLines(false); // no grid lines
        left.setDrawZeroLine(true); // draw a zero line
        lineChart.getAxisRight().setEnabled(false); // no right axis
        left.setTextColor(R.color.colorWhite); // color text label
        left.setAxisMinimum(-0.25f);
        left.setAxisMaximum(1.25f);
        left.setGranularity(1f); // interval 1
        left.setLabelCount(2, false);
        left.setValueFormatter(new YValueFormatter());

        // Right Axis
        lineChart.getAxisRight().setEnabled(false);

        // X Axis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
//        xAxis.setLabelRotationAngle();
        xAxis.setAxisMaximum(86400f);
        xAxis.setAxisMinimum(0f);
        xAxis.setLabelCount(24, false);
        xAxis.setGranularity(3600f);
        xAxis.setValueFormatter(new XValueFormatter());

        // General Chart Attribute
        lineChart.setVisibleXRangeMaximum(7200);
        lineChart.setVisibleXRangeMinimum(3600);
        Description description = lineChart.getDescription();
        description.setEnabled(false);

        LineDataSet lineDataSet = new LineDataSet(dataTurnLight, "Turn Time");
        lineDataSet.setDrawCircles(false);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData data = new LineData(dataSets);

        lineChart.setData(data);
        lineChart.getLegend().setTextColor(R.color.colorWhite);
        lineChart.invalidate();
    }

    void mapping() {
        txtDate = findViewById(R.id.txt_date);
        txtHourMinute = findViewById(R.id.txt_hours_minute);
        txtTotalWatt = findViewById(R.id.txt_total_watt);
        lineChart = findViewById(R.id.line_chart);
        toolbar = findViewById(R.id.add_toolbar);
    }

}

class YValueFormatter extends ValueFormatter {

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        if (value == 1) {
            return "On";
        }
        return "Off";
    }
}

class XValueFormatter extends ValueFormatter {
    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        int stamp = (int)value / 3600;
        if((int)value % 3600 == 0) {
            return String.valueOf(stamp) + ":00";
        }
        return "";
    }
}
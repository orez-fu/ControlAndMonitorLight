package com.example.controlandmonitorlight.view.view.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.model.DeviceStaticModel;
import com.example.controlandmonitorlight.model.RecordModel;
import com.example.controlandmonitorlight.utils.ConvertTime;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DeviceStaticActivity extends AppCompatActivity  {
    private String TAG = "DEVICE_STATIC_ACTIVITY";
    private TextView txtDate, txtHourMinute, txtTotalWatt;
    private LineChart lineChart;
    private DeviceStaticModel deviceStaticModel;
    private int day,month,year;
    private ArrayList<Entry> dataTurnLight = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_static);

        mapping();

        Gson gson = new Gson();
        String gson1 = getIntent().getStringExtra("rules");
        Type type = new TypeToken<DeviceStaticModel>() {
        }.getType();
         day = getIntent().getIntExtra("EXTRA_DAY",0) ;
         month = getIntent().getIntExtra("EXTRA_MONTH",1);
         year = getIntent().getIntExtra("EXTRA_YEAR",2);

        Log.d("DAY_MONTH_YEAR", day+"."+month+"."+year);
//        DeviceStaticModel rules = gson.fromGson(gson, type);
        deviceStaticModel = gson.fromJson(gson1, type);
        showInformation();
        drawLineChart();
        Log.d("DEVICE_STATIC", deviceStaticModel.getDeviceId());

    }

    void showInformation() {
        txtDate.setText(deviceStaticModel.getDeviceId());
        txtHourMinute.setText(deviceStaticModel.getTimeOn() + "");
        txtTotalWatt.setText(deviceStaticModel.getTotalWatt() + "");


    }

    void drawLineChart() {
        // Month start 0 - 11
        long timeStartDate = ConvertTime.convertDateTimeToUnix(year,month-1,day,0,0,0)/1000;
        long timeEndDate = timeStartDate + 86400;

        List<RecordModel> listRecord = new ArrayList<>();
        listRecord = deviceStaticModel.getRecords();

        List <Integer> listTurn = new ArrayList<>();
        for (int i=0; i<=1000000; i++) listTurn.add(0);
        for (RecordModel item : listRecord) {
            long currentTimeStamp = (long)item.getTimestamp()/1000;
            int currentTimeOn  = item.getTimeOn();
            for (int i= 0; i< currentTimeOn; i++){
                int index = (int)(currentTimeStamp-i-timeStartDate);
                Log.d(TAG,currentTimeStamp+"-"+i +"-"+timeStartDate+"= " + index);
                listTurn.set(index,1);
            }
        }
        dataTurnLight.add(new Entry(0,listTurn.get(0)));
        for (int i=1; i<=86399; i++)
            if (  listTurn.get(i) != listTurn.get(i-1)){
                if ( listTurn.get(i) == 0 ){
                    dataTurnLight.add( new Entry(i-1,0));
                    dataTurnLight.add( new Entry(i,0));
                } else {
                    dataTurnLight.add(new Entry(i,0));
                    dataTurnLight.add(new Entry(i,1));
                }
            } else dataTurnLight.add(new Entry(i, listTurn.get(i)));
        LineDataSet lineDataSet = new LineDataSet(dataTurnLight, "Turn Time");
        lineDataSet.setDrawCircles(false);
        ArrayList <ILineDataSet > dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData data = new LineData(dataSets);
        lineChart.setData(data);
        Log.d(TAG,data.toString());
        lineChart.invalidate();
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setVisibleXRangeMaximum(7200);
        lineChart.setVisibleXRangeMinimum(3600);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisLeft().setLabelCount(2);

    }

    void mapping() {
        txtDate = findViewById(R.id.txt_date);
        txtHourMinute = findViewById(R.id.txt_hours_minute);
        txtTotalWatt = findViewById(R.id.txt_total_watt);
        lineChart = findViewById(R.id.line_chart);
    }

}

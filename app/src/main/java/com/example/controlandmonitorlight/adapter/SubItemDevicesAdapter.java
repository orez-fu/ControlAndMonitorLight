package com.example.controlandmonitorlight.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.model.DeviceModel;
import com.example.controlandmonitorlight.model.DeviceStaticModel;
import com.example.controlandmonitorlight.view.view.Activity.DeviceStaticActivity;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.List;

public class SubItemDevicesAdapter extends RecyclerView.Adapter<SubItemDevicesAdapter.ViewHolder> {

    private List<DeviceStaticModel> devices;
    private final Context mContext;
    private Calendar mCalendar;

    public SubItemDevicesAdapter(List<DeviceStaticModel> devices, Context context) {
        this.devices = devices;
        this.mContext = context;
        this.mCalendar = Calendar.getInstance();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_static_devices, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.nameDevice.setText(devices.get(position).getDeviceId());
        holder.timeOn.setText(String.valueOf(devices.get(position).getTimeOn()));
        holder.totalWalt.setText(String.valueOf(devices.get(position).getTotalWatt()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                Intent intent = new Intent(mContext, DeviceStaticActivity.class);
                intent.putExtra("rules", gson.toJson(devices.get(position)));
                intent.putExtra("EXTRA_DAY", mCalendar.get(Calendar.DAY_OF_MONTH));
                intent.putExtra("EXTRA_MONTH", mCalendar.get(Calendar.MONTH)+1);
                intent.putExtra("EXTRA_YEAR", mCalendar.get(Calendar.YEAR));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameDevice, totalWalt, timeOn;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameDevice = itemView.findViewById(R.id.txt_name);
            totalWalt = itemView.findViewById(R.id.txt_totalWalt);
            timeOn = itemView.findViewById(R.id.txt_timeOn);
            cardView = itemView.findViewById(R.id.card_device);
        }
    }

    public void setDeviceStatic(List<DeviceStaticModel> devices, Calendar calendar) {
        this.devices = devices;
        this.mCalendar = calendar;
        notifyDataSetChanged();
    }
}

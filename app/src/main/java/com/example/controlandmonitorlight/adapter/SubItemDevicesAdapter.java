package com.example.controlandmonitorlight.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.controlandmonitorlight.R;

import java.util.List;

public class SubItemDevicesAdapter extends RecyclerView.Adapter<SubItemDevicesAdapter.ViewHolder> {

    List<String> devices ;

    public SubItemDevicesAdapter(List<String> devices) {
        this.devices = devices;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_static_devices,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameDevice.setText(devices.get(position));
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameDevice, totalWalt,timeOn ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameDevice = itemView.findViewById(R.id.txt_name);
            totalWalt = itemView.findViewById(R.id.txt_totalWalt);
            timeOn = itemView.findViewById(R.id.txt_timeOn);
        }
    }
}

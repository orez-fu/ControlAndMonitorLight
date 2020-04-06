/*CustomDevicesAdapter*/
package com.example.controlandmonitorlight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controlandmonitorlight.databinding.DeviceBinding;
import com.example.controlandmonitorlight.model.ObjectDevices;
import com.example.controlandmonitorlight.view.view.Activity.RoomActivity;
import com.example.controlandmonitorlight.viewmodel.Comunication;

import java.util.List;

public class CustomDevicesAdapter extends RecyclerView.Adapter<CustomDevicesAdapter.ViewHolder> {

    List<ObjectDevices> list ;
    Context mContext ;
    Comunication listener = null ;
    LayoutInflater layoutInflater ;
    public CustomDevicesAdapter(List<ObjectDevices> list, RoomActivity listener) {
        this.list = list;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        DeviceBinding deviceBinding = DeviceBinding.inflate(layoutInflater,parent,false);
        return new ViewHolder(deviceBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ObjectDevices devices = list.get(position);
        holder.bind(devices);
        holder.deviceBinding.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnClickedItem(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private DeviceBinding deviceBinding ;
        public ViewHolder(@NonNull DeviceBinding itemView) {
            super(itemView.getRoot());
            this.deviceBinding = itemView ;
        }
        public void bind(ObjectDevices devices ){
            this.deviceBinding.setData(devices);
        }
        public DeviceBinding getDeviceBinding(){
            return deviceBinding;
        }
    }
}

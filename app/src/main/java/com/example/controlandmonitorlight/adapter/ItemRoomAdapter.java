package com.example.controlandmonitorlight.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.model.DeviceModel;
import com.example.controlandmonitorlight.model.Room;
import com.example.controlandmonitorlight.viewmodel.DevicesViewModel;

import java.util.ArrayList;
import java.util.List;

public class ItemRoomAdapter extends RecyclerView.Adapter<ItemRoomAdapter.ViewHolder> {

    private List<Room> Name ;
    private List<DeviceModel> nameDevices  = new ArrayList<>();
    private Context context ;
    private List<String> Devices;
    private DevicesViewModel devicesViewModel ;
    private SubItemDevicesAdapter subItemDevicesAdapter;

    public ItemRoomAdapter(List<Room> name, Context context  ) {
        this.Name = name;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Log.d("name=",Name.get(position).getName()) ;
        holder.nameRoom.setText(Name.get(position).getName());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL,false);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setHasFixedSize(true);
        nameDevices = new ArrayList<>();
        nameDevices.clear();
        devicesViewModel = ViewModelProviders.of((FragmentActivity) context).get(DevicesViewModel.class);
        //devicesViewModel.setData();
        devicesViewModel.LoadDevicesFireBase(Name.get(position).getId());
        devicesViewModel.getData().observe((LifecycleOwner) context, new Observer<List<DeviceModel>>() {
            @Override
            public void onChanged(List<DeviceModel> deviceModels) {
                Log.d("nameDevices = ", deviceModels.size()+"");
                nameDevices = deviceModels;
                subItemDevicesAdapter = new SubItemDevicesAdapter(nameDevices) ;
                holder.recyclerView.setAdapter(subItemDevicesAdapter);
            }
        });



    }
    @Override
    public int getItemCount() {
        Log.d("namesize - " , Name.size()+"");
        return Name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameRoom , numberTotalWalt ;
        RecyclerView recyclerView ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameRoom = itemView.findViewById(R.id.room);
            numberTotalWalt = itemView.findViewById(R.id.total_walt);
            recyclerView = itemView.findViewById(R.id.child_item_devices);
        }
    }
}

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
import com.example.controlandmonitorlight.model.RoomStatic;
import com.example.controlandmonitorlight.model.RoomStaticModel;
import com.example.controlandmonitorlight.utils.ConvertWatt;
import com.example.controlandmonitorlight.viewmodel.DevicesViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ItemRoomAdapter extends RecyclerView.Adapter<ItemRoomAdapter.ViewHolder> {

    private List<RoomStaticModel> mListRoom = new ArrayList<>(); // danh sach phong
    private Context context;
    private Calendar mCalendar;

    private SubItemDevicesAdapter subItemDevicesAdapter;

    public ItemRoomAdapter(Context context) {
        this.context = context;
        this.mCalendar = Calendar.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.nameRoom.setText(mListRoom.get(position).getName());
        holder.numberTotalWalt.setText("Total Watt: "+ConvertWatt.convertWatt(mListRoom.get(position).getTotalWatt()));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setHasFixedSize(true);

        Log.d("FIX_PASS_DAY", "On Room Adapter: " + mCalendar.toString());

        subItemDevicesAdapter = new SubItemDevicesAdapter(mListRoom.get(position).getDevices(), context);
        subItemDevicesAdapter.setDeviceStatic(mListRoom.get(position).getDevices(), mCalendar);
        holder.recyclerView.setAdapter(subItemDevicesAdapter);
    }

    @Override
    public int getItemCount() {
        return mListRoom.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameRoom, numberTotalWalt;
        RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameRoom = itemView.findViewById(R.id.room);
            numberTotalWalt = itemView.findViewById(R.id.total_walt);
            recyclerView = itemView.findViewById(R.id.child_item_devices);
        }
    }

    public void setRoomStaticList(List<RoomStaticModel> roomStaticList, Calendar calendar) {
        this.mListRoom = roomStaticList;
        this.mCalendar = calendar;
        notifyDataSetChanged();
    }
}

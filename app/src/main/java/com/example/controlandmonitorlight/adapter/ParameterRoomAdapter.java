/*ParameterRoomAdapter*/
package com.example.controlandmonitorlight.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.model.DeviceModel;
import com.example.controlandmonitorlight.view.view.Activity.RoomActivity;
import com.example.controlandmonitorlight.viewmodel.Comunication;

import java.util.List;

public class ParameterRoomAdapter extends RecyclerView.Adapter<ParameterRoomAdapter.ViewHolder> {

    List<DeviceModel> list ;
    Context mContext ;
    Comunication listener = null ;

    public ParameterRoomAdapter(List<DeviceModel> list, RoomActivity listener) {
        this.list = list;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_devices,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        DeviceModel devices = list.get(position);
        holder.mType.setText(devices.getType());
        holder.mValue.setText(devices.getStatusDevices());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.setOnClickedItem(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageIcon ;
        private TextView mType ;
        private TextView mValue ;
        private CardView card ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           imageIcon = itemView.findViewById(R.id.image_icon) ;
           mType = itemView.findViewById(R.id.name_devices);
           mValue = itemView.findViewById(R.id.value);
           card = itemView.findViewById(R.id.card);
        }

    }
}

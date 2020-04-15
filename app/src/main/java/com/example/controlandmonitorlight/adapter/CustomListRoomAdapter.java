package com.example.controlandmonitorlight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.viewmodel.RoomInterface;

import java.util.List;

public class CustomListRoomAdapter extends RecyclerView.Adapter<CustomListRoomAdapter.ViewHolder> {

    private List<String> nameRooms;
    Context mContext ;
    private RoomInterface click;

    public void setClick(RoomInterface click){
        this.click = click;
    }
    public CustomListRoomAdapter(List<String> nameRooms, Context mContext) {
        this.nameRooms = nameRooms;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_room_activity,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.titleRooms.setText(nameRooms.get(position));
        holder.itemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.setOnclickItem(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return nameRooms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleRooms, numberDevicesRoom ;
        CardView itemCard ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleRooms = itemView.findViewById(R.id.title);
            numberDevicesRoom = itemView.findViewById(R.id.value);
            itemCard = itemView.findViewById(R.id.cardview);
        }
    }
}

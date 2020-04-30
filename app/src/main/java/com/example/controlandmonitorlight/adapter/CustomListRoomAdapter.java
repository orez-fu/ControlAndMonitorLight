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


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.model.Room;
import com.example.controlandmonitorlight.view.view.Activity.RoomActivity;
import com.example.controlandmonitorlight.viewmodel.RoomInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;

public class CustomListRoomAdapter extends RecyclerView.Adapter<CustomListRoomAdapter.ViewHolder> {

    private List<Room> nameRooms;
    Context mContext;
    private RoomInterface click;

    public void setClick(RoomInterface click) {
        this.click = click;
    }

    public CustomListRoomAdapter(List<Room> nameRooms, Context mContext) {
        this.nameRooms = nameRooms;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_room_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        String imageUrl = nameRooms.get(position).getImageUrl();

        holder.roomTitle.setText(nameRooms.get(position).getName());
        holder.numberDevicesRoom.setText(nameRooms.get(position).getDevices().size() + " devices");

        long lastTime = Math.round(nameRooms.get(position).getLastTime());
        if (lastTime + 6000 < Calendar.getInstance().getTimeInMillis()) {
            holder.viewStatusOn.post(new Runnable() {
                @Override
                public void run() {
                    holder.viewStatusOn.setVisibility(View.GONE);
                }
            });
            holder.viewStatusWarning.post(new Runnable() {
                @Override
                public void run() {
                    holder.viewStatusWarning.setVisibility(View.VISIBLE);
                }
            });
        } else {
            holder.viewStatusWarning.post(new Runnable() {
                @Override
                public void run() {
                    holder.viewStatusWarning.setVisibility(View.GONE);
                }
            });
            holder.viewStatusOn.post(new Runnable() {
                @Override
                public void run() {
                    holder.viewStatusOn.setVisibility(View.VISIBLE);
                }
            });
        }

        if (imageUrl != null) {
            Glide.with(mContext)
                    .load(imageUrl)
                    .into(holder.imageView);
        }

        holder.itemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.setOnclickItem(holder.getAdapterPosition());
            }
        });

        final String roomId = nameRooms.get(position).getId();
        final Runnable runnable = new Runnable() {
            private long timestamp = 0;
            private boolean isWarning = true;

            @Override
            public void run() {
                FirebaseDatabase.getInstance().getReference("rooms")
                        .child(roomId)
                        .child("lastTime").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        timestamp = Math.round(dataSnapshot.getValue(Double.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                while (true) {
                    try {
                        Thread.sleep(1000);
                        if (timestamp + 6000 < Calendar.getInstance().getTimeInMillis()) {
                            holder.viewStatusOn.post(new Runnable() {
                                @Override
                                public void run() {
                                    holder.viewStatusOn.setVisibility(View.GONE);
                                    isWarning = true;
                                }
                            });
                            holder.viewStatusWarning.post(new Runnable() {
                                @Override
                                public void run() {
                                    holder.viewStatusWarning.setVisibility(View.VISIBLE);
                                }
                            });
                        } else if (isWarning) {
                            holder.viewStatusWarning.post(new Runnable() {
                                @Override
                                public void run() {
                                    holder.viewStatusWarning.setVisibility(View.GONE);
                                }
                            });
                            holder.viewStatusOn.post(new Runnable() {
                                @Override
                                public void run() {
                                    holder.viewStatusOn.setVisibility(View.VISIBLE);
                                    isWarning = false;
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        new Thread(runnable).start();
    }

    @Override
    public int getItemCount() {
        return nameRooms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView roomTitle, numberDevicesRoom;
        CardView itemCard;
        ImageView imageView;
        View viewStatusOn;
        View viewStatusWarning;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomTitle = itemView.findViewById(R.id.txt_room_name);
            numberDevicesRoom = itemView.findViewById(R.id.txt_number_device);
            itemCard = itemView.findViewById(R.id.cardview);
            imageView = itemView.findViewById(R.id.img_room);
            viewStatusOn = itemView.findViewById(R.id.view_status_ok);
            viewStatusWarning = itemView.findViewById(R.id.view_status_warning);

        }
    }

}

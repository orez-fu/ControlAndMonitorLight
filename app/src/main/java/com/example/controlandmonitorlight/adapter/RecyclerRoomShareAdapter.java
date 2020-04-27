package com.example.controlandmonitorlight.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.model.Room;

import java.util.List;

public class RecyclerRoomShareAdapter extends RecyclerView.Adapter<RecyclerRoomShareAdapter.ViewHolder> {
    private List<Room> rooms;

    public RecyclerRoomShareAdapter(List<Room> rooms) {
        this.rooms = rooms;
    }
    private OnRoomClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_share, parent, false);

        return new RecyclerRoomShareAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Room room = rooms.get(position);

        holder.textRoom.setText(room.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(room);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView textRoom;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_view_item_room);
            textRoom = itemView.findViewById(R.id.tv_room_name);
        }
    }

    public interface OnRoomClickListener {
        void onItemClick(Room room);
    }

    public void setOnRoomClickListener(OnRoomClickListener listener) {
        this.listener = listener;
    }
}

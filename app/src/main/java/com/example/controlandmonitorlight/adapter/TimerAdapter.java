package com.example.controlandmonitorlight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.model.Timer;

import java.util.List;

public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.ViewHolder> {

    private List<Timer> mTimers;

    public TimerAdapter(List<Timer> mList) {
        this.mTimers = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtLabel.setText(mTimers.get(position).getLabel());
        holder.txtTime.setText(mTimers.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return mTimers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTime;
        TextView txtLabel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTime = itemView.findViewById(R.id.txt_time_display);
            txtLabel = itemView.findViewById(R.id.txt_label_display);
        }
    }
}

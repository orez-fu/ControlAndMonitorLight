package com.example.controlandmonitorlight.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.model.TimerModel;
import com.example.controlandmonitorlight.repositories.RealtimeFirebaseRepository;


import java.util.List;

public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.ViewHolder> {
    private final String TAG = "TIMER_ADAPTER";
    private OnItemClickListener listener;

    private List<TimerModel> mTimers;

    public TimerAdapter(List<TimerModel> mList) {
        this.mTimers = mList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        TimerModel currentTimer = mTimers.get(position);

        holder.txtLabel.setText(currentTimer.getLabel());
        holder.txtTime.setText(currentTimer.getTime12());
        if(currentTimer.getStatus() == TimerModel.STATUS_ON) {
            holder.switchCompat.setChecked(true);
        } else {
            holder.switchCompat.setChecked(false);
        }
        holder.txtAction.setText(currentTimer.getType() == TimerModel.TYPE_ON ? "Go on" : "Go off");
    }

    @Override
    public int getItemCount() {
        return mTimers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTime;
        TextView txtLabel;
        SwitchCompat switchCompat;
        TextView txtAction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTime = itemView.findViewById(R.id.txt_time_display);
            txtLabel = itemView.findViewById(R.id.txt_label_display);
            txtAction = itemView.findViewById(R.id.txt_action);
            switchCompat = itemView.findViewById(R.id.switch_compat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(mTimers.get(position));
                    }
                }
            });

            switchCompat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {

                        RealtimeFirebaseRepository.getInstance()
                                .toggleStatusTimer(mTimers.get(position).getDeviceId(),
                                        mTimers.get(position).getId(),
                                        switchCompat.isChecked() ? TimerModel.STATUS_ON : TimerModel.STATUS_OFF);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(TimerModel timer);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

package com.example.controlandmonitorlight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.databinding.TimeBinding;
import com.example.controlandmonitorlight.model.Time;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {

    List<Time> mList ;
    Context mContext ;
    LayoutInflater layoutInflater ;
    public TimeAdapter(List<Time> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater == null )
        {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        TimeBinding timeBinding = TimeBinding.inflate(layoutInflater,parent,false);
        return new ViewHolder(timeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private  TimeBinding timeBinding ;
        public ViewHolder(@NonNull TimeBinding itemView) {
            super(itemView.getRoot());
            this.timeBinding = itemView ;
        }

        public void bind(Time times )
        {
            this.timeBinding.setData(times);
        }
        public TimeBinding getTimeBinding(){
            return timeBinding ;
        }
    }
}

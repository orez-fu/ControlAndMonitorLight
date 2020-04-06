/* adapter */
package com.example.controlandmonitorlight.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controlandmonitorlight.MainActivity;
import com.example.controlandmonitorlight.databinding.DataBinding;
import com.example.controlandmonitorlight.model.Room;
import com.example.controlandmonitorlight.viewmodel.Comunication;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    List<Room> list ;
    Context mContext ;
    LayoutInflater layoutInflater ;
    Comunication click ;
    public CustomAdapter(List<Room> list , MainActivity click) {
        this.list = list;
        this.click = click ;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater == null)
        {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        DataBinding dataBinding = DataBinding.inflate(layoutInflater,parent,false);
        return new ViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Room room = list.get(position);
        holder.bind(room);
        holder.dataBinding.card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.setOnClickedItem(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private DataBinding dataBinding ;
        public ViewHolder(@NonNull DataBinding itemView) {
            super(itemView.getRoot());
            this.dataBinding = itemView ;
        }

        public void bind(Room room)
        {
            this.dataBinding.setData(room);
        }
        public DataBinding getDataBinding()
        {
            return dataBinding;
        }
    }
}
/*RoomDeviceAdapter*/
package com.example.controlandmonitorlight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.model.DeviceDataModel;
import com.example.controlandmonitorlight.viewmodel.Comunication;

import java.util.List;

public class RoomDeviceAdapter extends RecyclerView.Adapter<RoomDeviceAdapter.ViewHolder> {
    List<DeviceDataModel> list;
    Context mContext;
    Comunication click;

    public RoomDeviceAdapter(List<DeviceDataModel> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        DeviceDataModel roomDevice = list.get(position);
        holder.imageView.setImageResource(roomDevice.getImages());
        holder.mValue.setText(roomDevice.getValue());
        holder.mType.setText(roomDevice.getType());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mType ;
        TextView mValue ;
        ImageView imageView ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mType = itemView.findViewById(R.id.txt_type);
            mValue = itemView.findViewById(R.id.txt_value);
            imageView = itemView.findViewById(R.id.imageview);
        }
    }
}

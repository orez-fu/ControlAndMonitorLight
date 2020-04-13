/*RoomDeviceAdapter*/
package com.example.controlandmonitorlight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controlandmonitorlight.databinding.DataNew;
import com.example.controlandmonitorlight.model.DeviceDataModel;
import com.example.controlandmonitorlight.viewmodel.Comunication;

import java.util.List;

public class RoomDeviceAdapter extends RecyclerView.Adapter<RoomDeviceAdapter.ViewHolder> {
    List<DeviceDataModel> list;
    Context mContext;
    LayoutInflater layoutInflater;
    Comunication click;

    public RoomDeviceAdapter(List<DeviceDataModel> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(mContext);
        }
        DataNew dataBinding = DataNew.inflate(layoutInflater, parent, false);
        return new ViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        DeviceDataModel introduction = list.get(position);
        holder.bind(introduction);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private DataNew dataBinding;

        public ViewHolder(@NonNull DataNew itemView) {
            super(itemView.getRoot());
            this.dataBinding = itemView;
        }

        public void bind(DeviceDataModel intro) {
            this.dataBinding.setData(intro);
        }

        public DataNew getDataBinding() {
            return dataBinding;
        }
    }
}

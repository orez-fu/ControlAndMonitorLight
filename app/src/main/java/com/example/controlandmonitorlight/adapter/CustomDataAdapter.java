/*CustomDataAdapter*/
package com.example.controlandmonitorlight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controlandmonitorlight.databinding.DataNew;
import com.example.controlandmonitorlight.model.Data;
import com.example.controlandmonitorlight.viewmodel.Comunication;

import java.util.List;

public class CustomDataAdapter extends RecyclerView.Adapter<CustomDataAdapter.ViewHolder> {
    List<Data> list;
    Context mContext;
    LayoutInflater layoutInflater;
    Comunication click;

    public CustomDataAdapter(List<Data> list, Context mContext) {
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
        Data introduction = list.get(position);
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

        public void bind(Data intro) {
            this.dataBinding.setData(intro);
        }

        public DataNew getDataBinding() {
            return dataBinding;
        }
    }
}

package com.team12.warofwords.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.team12.warofwords.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Way yan on 11/7/2018.
 */

public class DeviceRecyclerAdapter extends RecyclerView.Adapter<DeviceRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<String> devices = new ArrayList<>();

    public DeviceRecyclerAdapter(Context context, List<String> devices) {
        this.context = context;
        this.devices = devices;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.device_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(devices.get(position));
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDevice;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDevice = itemView.findViewById(R.id.txt_device);
        }

        public void bindView(String device) {
            tvDevice.setText(device);
        }
    }
}

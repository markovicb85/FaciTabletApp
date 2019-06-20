package com.example.facitabletapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.facitabletapp.R;
import com.example.facitabletapp.Room.Device;

import java.util.ArrayList;
import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceHolder> {
    private List<Device> devices = new ArrayList<>();

    @NonNull
    @Override
    public DeviceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_item, parent, false);
        return new DeviceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceHolder holder, int position) {
        Device currentDevice = devices.get(position);
        holder.tvDeviceName.setText(currentDevice.getDeviceName());
        holder.cbDeviceStatus.setChecked(currentDevice.getStatus());
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
        notifyDataSetChanged();
    }

    public Device getDeviceAt(int position) {
        return devices.get(position);
    }

    class DeviceHolder extends RecyclerView.ViewHolder {
        private TextView tvDeviceName;
        private CheckBox cbDeviceStatus;

        public DeviceHolder(View itemView) {
            super(itemView);
            tvDeviceName = itemView.findViewById(R.id.tv_device_name);
            cbDeviceStatus = itemView.findViewById(R.id.cb_device_status);
        }
    }
}

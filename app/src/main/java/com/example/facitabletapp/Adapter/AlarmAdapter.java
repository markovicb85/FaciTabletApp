package com.example.facitabletapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.facitabletapp.R;
import com.example.facitabletapp.Room.Alarm;

import java.util.ArrayList;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmHolder> {
    private List<Alarm> alarms = new ArrayList<>();

    @NonNull
    @Override
    public AlarmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_item, parent, false);
        return new AlarmHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmHolder holder, int position) {
        Alarm currentAlarm = alarms.get(position);
        holder.tvAlarmName.setText(currentAlarm.getAlarmName());
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public void setAlarms(List<Alarm> alarm) {
        this.alarms = alarm;
        notifyDataSetChanged();
    }

    public Alarm getAlarmAt(int position) {
        return alarms.get(position);
    }

    class AlarmHolder extends RecyclerView.ViewHolder {
        private TextView tvAlarmName;

        public AlarmHolder(View itemView) {
            super(itemView);
            tvAlarmName = itemView.findViewById(R.id.tv_alarm_name);
        }
    }
}

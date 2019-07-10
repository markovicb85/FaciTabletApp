package com.example.facitabletapp.Adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        holder.tvAlarmStatus.setText(Integer.toString(currentAlarm.getStatus()));
        holder.tvAlarmDescription.setText(currentAlarm.getDescription());
        holder.tvAlarmDateTime.setText((CharSequence) currentAlarm.getCurrentTime());
        if(currentAlarm.getStatus() > 4){
            holder.ivAlarmIcon.setBackgroundColor(Color.RED);
        }else if(currentAlarm.getStatus() > 7){
            holder.ivAlarmIcon.setBackgroundColor(Color.BLUE);
        }
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
        private TextView tvAlarmStatus;
        private TextView tvAlarmDescription;
        private TextView tvAlarmDateTime;
        private ImageView ivAlarmIcon;

        public AlarmHolder(View itemView) {
            super(itemView);
            tvAlarmName = itemView.findViewById(R.id.tv_alarm_name);
            tvAlarmStatus = itemView.findViewById(R.id.tv_status);
            tvAlarmDateTime = itemView.findViewById(R.id.tv_date_time);
            tvAlarmDescription = itemView.findViewById(R.id.tv_description);
            ivAlarmIcon = itemView.findViewById(R.id.iv_alarm_icon);
        }
    }
}

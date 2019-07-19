package com.example.facitabletapp.Fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facitabletapp.Adapter.AlarmAdapter;
import com.example.facitabletapp.R;
import com.example.facitabletapp.Room.Alarm;
import com.example.facitabletapp.ViewModel.AlarmViewModel;

import java.util.List;

public class MainAlarmFragment extends Fragment {

    private TextView tvAlarmName;
    private TextView tvAlarmStatus;
    private TextView tvAlarmDateTime;
    private TextView tvAlarmDescription;
    private ImageView ivAlarmIcon;

    private AlarmViewModel alarmViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main_alarm, container, false);

        tvAlarmName = rootView.findViewById(R.id.tv_alarm_name);
        tvAlarmStatus = rootView.findViewById(R.id.tv_status);
        tvAlarmDateTime = rootView.findViewById(R.id.tv_date_time);
        tvAlarmDescription = rootView.findViewById(R.id.tv_description);
        ivAlarmIcon = rootView.findViewById(R.id.iv_alarm_icon);

        alarmViewModel =  ViewModelProviders.of(getActivity()).get(AlarmViewModel.class);
        alarmViewModel.getAllAlarms().observe(getActivity(), new Observer<List<Alarm>>() {
            @Override
            public void onChanged(@Nullable List<Alarm> alarms) {
                tvAlarmName.setText(alarms.get(0).getAlarmName().toString());
                tvAlarmStatus.setText(Integer.toString(alarms.get(0).getStatus()));
                tvAlarmDateTime.setText(alarms.get(0).getCurrentTime().toString());
                tvAlarmDescription.setText(alarms.get(0).getDescription().toString());
                if(alarms.get(0).getStatus() > 4){
                    ivAlarmIcon.setBackgroundColor(Color.RED);
                }else if(alarms.get(0).getStatus() > 7){
                    ivAlarmIcon.setBackgroundColor(Color.BLUE);
                }
            }
        });

        return rootView;
    }
}

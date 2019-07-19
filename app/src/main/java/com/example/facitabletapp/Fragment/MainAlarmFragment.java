package com.example.facitabletapp.Fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.example.facitabletapp.Tools.UDPClient;
import com.example.facitabletapp.ViewModel.AlarmViewModel;

import java.util.List;

public class MainAlarmFragment extends Fragment {

    private TextView tvAlarmName;
    private TextView tvAlarmStatus;
    private TextView tvAlarmDateTime;
    private TextView tvAlarmDescription;
    private ImageView ivAlarmIcon;

    public AlarmViewModel alarmViewModel;
    private UDPClient runnable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_main_alarm, container, false);

        tvAlarmName = rootView.findViewById(R.id.tv_alarm_name);
        tvAlarmStatus = rootView.findViewById(R.id.tv_status);
        tvAlarmDateTime = rootView.findViewById(R.id.tv_date_time);
        tvAlarmDescription = rootView.findViewById(R.id.tv_description);
        ivAlarmIcon = rootView.findViewById(R.id.iv_alarm_icon);

        alarmViewModel =  ViewModelProviders.of(getActivity()).get(AlarmViewModel.class);
        alarmViewModel.getAllAlarms().observe(getActivity(), new Observer<List<Alarm>>() {
            @Override
            public void onChanged(@Nullable List<Alarm> alarms) {
                if(!alarms.isEmpty()){
                    if(alarms.get(0).getAlarmName() != tvAlarmName.getText()) {
                        setMainAlarm(alarms);
                    }
                }else {
                    clearMainAlarm();
                }
            }
        });

        startReading(alarmViewModel);

        return rootView;
    }

    public void setMainAlarm(List<Alarm> alarms){
        tvAlarmName.setText(alarms.get(0).getAlarmName());
        tvAlarmStatus.setText(Integer.toString(alarms.get(0).getStatus()));
        tvAlarmDateTime.setText(alarms.get(0).getCurrentTime());
        tvAlarmDescription.setText(alarms.get(0).getDescription());
        if(alarms.get(0).getStatus() > 3){
            ivAlarmIcon.setBackgroundColor(Color.RED);
        }else if(alarms.get(0).getStatus() > 7){
            ivAlarmIcon.setBackgroundColor(Color.BLUE);
        }
    }

    public void clearMainAlarm(){
        tvAlarmName.setText("");
        tvAlarmStatus.setText("");
        tvAlarmDateTime.setText("");
        tvAlarmDescription.setText("");
        ivAlarmIcon.setBackgroundColor(Color.WHITE);
    }

    public void startReading(AlarmViewModel alarmViewModel) {
        runnable = new UDPClient(alarmViewModel, getContext());
        new Thread(runnable).start();
    }
}

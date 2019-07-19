package com.example.facitabletapp.Fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.facitabletapp.Adapter.AlarmAdapter;
import com.example.facitabletapp.R;
import com.example.facitabletapp.Room.Alarm;
import com.example.facitabletapp.Tools.UDPClient;
import com.example.facitabletapp.ViewModel.AlarmViewModel;

import java.util.ArrayList;
import java.util.List;

public class AlarmListFragment extends Fragment {

    List<Alarm> newAlarmsList;
    private AlarmViewModel alarmViewModel;
    private UDPClient runnable;

    private Handler mainHandler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_alarm_list, container, false);

        setHasOptionsMenu(true);

        RecyclerView recyclerView = rootView.findViewById(R.id.alarms_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final AlarmAdapter alarmAdapter= new AlarmAdapter();
        recyclerView.setAdapter(alarmAdapter);

        alarmViewModel =  ViewModelProviders.of(getActivity()).get(AlarmViewModel.class);
        alarmViewModel.getAllAlarms().observe(getActivity(), new Observer<List<Alarm>>() {
            @Override
            public void onChanged(@Nullable List<Alarm> alarms) {
                newAlarmsList = new ArrayList<>(alarms);
                newAlarmsList.remove(0);
                alarmAdapter.setAlarms(newAlarmsList);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                alarmViewModel.delete(alarmAdapter.getAlarmAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Alarm deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        startReading(alarmViewModel);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_alarm_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_alarms:
                alarmViewModel.deleteAllAlarms();
                Toast.makeText(getActivity(), "All alarms are deleted", Toast.LENGTH_SHORT).show();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startReading(AlarmViewModel alarmViewModel) {
        runnable = new UDPClient(alarmViewModel, getContext());
        new Thread(runnable).start();
    }
}

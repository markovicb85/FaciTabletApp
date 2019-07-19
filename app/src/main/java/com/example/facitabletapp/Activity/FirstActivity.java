package com.example.facitabletapp.Activity;

import android.app.Application;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.facitabletapp.Adapter.AlarmAdapter;
import com.example.facitabletapp.Fragment.AboutFragment;
import com.example.facitabletapp.Fragment.AddDeviceFragment;
import com.example.facitabletapp.Fragment.AlarmListFragment;
import com.example.facitabletapp.Fragment.ContactFragment;
import com.example.facitabletapp.Fragment.DeviceListFragment;
import com.example.facitabletapp.Fragment.SettingsFragment;
import com.example.facitabletapp.Fragment.TimeFragment;
import com.example.facitabletapp.R;
import com.example.facitabletapp.Room.Alarm;
import com.example.facitabletapp.ViewModel.AlarmViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static java.util.Arrays.copyOfRange;

public class FirstActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    public NavigationView navigationView;
    public Button btnStart;
    public Button btnStop;

    DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");


    public SharedPreferences sharedPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        btnStart = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {

            sharedPref = getApplicationContext().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = sharedPref.edit();

            if (sharedPref.contains("port")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_first_container,
                        new TimeFragment()).commit();
                //navigationView.setCheckedItem(R.id.alarm_list);
            }else{
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_first_container,
                        new SettingsFragment()).commit();
                navigationView.setCheckedItem(R.id.settings);
            }
        }

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        //Podaci da se popuni baza za testiranje - izbrisati kasnije
    /*    final AlarmAdapter alarmAdapter= new AlarmAdapter();
        AlarmViewModel alarmViewModel = alarmViewModel =  ViewModelProviders.of(FirstActivity.this).get(AlarmViewModel.class);
        alarmViewModel.getAllAlarms().observe(FirstActivity.this, new Observer<List<Alarm>>() {
                    @Override
                    public void onChanged(@Nullable List<Alarm> alarms) {
                        alarmAdapter.setAlarms(alarms);
                    }
                });

        Alarm alarm = new Alarm();
        alarm.setAlarmName("A168");
        alarm.setStatus(5);
        alarm.setCurrentTime(dateFormat.format(Calendar.getInstance().getTime()));
        alarm.setSuplementData(new String (".W"));
        alarm.setDescription(new String ("soba 168"));

        alarmViewModel.insert(alarm);

        Alarm alarm1 = new Alarm();
        alarm1.setAlarmName("A315");
        alarm1.setStatus(4);
        alarm1.setCurrentTime(dateFormat.format(Calendar.getInstance().getTime()));
        alarm1.setSuplementData(new String (".1"));
        alarm1.setDescription(new String (""));

        alarmViewModel.insert(alarm1);

        Alarm alarm2 = new Alarm();
        alarm2.setAlarmName("1899");
        alarm2.setStatus(1);
        alarm2.setCurrentTime(dateFormat.format(Calendar.getInstance().getTime()));
        alarm2.setSuplementData(new String (""));
        alarm2.setDescription(new String (""));

        alarmViewModel.insert(alarm2);*/

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.alarm_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_first_container,
                        new AlarmListFragment()).commit();
                break;
            case R.id.device_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_first_container,
                        new DeviceListFragment()).commit();
                break;
            case R.id.add_device:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_first_container,
                        new AddDeviceFragment()).commit();
                break;
            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_first_container,
                        new SettingsFragment()).commit();
                break;
            case R.id.about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_first_container,
                        new AboutFragment()).commit();
                break;
            case R.id.contact:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_first_container,
                        new ContactFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

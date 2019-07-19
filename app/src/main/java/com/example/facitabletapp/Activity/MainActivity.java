package com.example.facitabletapp.Activity;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.facitabletapp.Fragment.AddDeviceFragment;
import com.example.facitabletapp.Fragment.AlarmListFragment;
import com.example.facitabletapp.Fragment.ContactFragment;
import com.example.facitabletapp.Fragment.DeviceListFragment;
import com.example.facitabletapp.Fragment.AboutFragment;
import com.example.facitabletapp.Fragment.MainAlarmFragment;
import com.example.facitabletapp.Fragment.SettingsFragment;
import com.example.facitabletapp.R;
import com.example.facitabletapp.Tools.UDPClient;
import com.example.facitabletapp.ViewModel.AlarmViewModel;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    public NavigationView navigationView;
    public Button btnStart;
    public Button btnStop;
    private Button btnDeleteAllAlarms;
    private TextView roomName;

    public SharedPreferences sharedPref;
    private AlarmViewModel alarmViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        btnStart = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);
        btnDeleteAllAlarms = findViewById(R.id.btn_delete_all_alarm);
        roomName = findViewById(R.id.tv_info_room_name);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            sharedPref = getApplicationContext().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = sharedPref.edit();

            roomName.setText(sharedPref.getString("device", ""));

            if (sharedPref.contains("port")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AlarmListFragment()).commit();
                navigationView.setCheckedItem(R.id.alarm_list);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_alarm,
                        new MainAlarmFragment()).commit();
            }else{
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingsFragment()).commit();
                navigationView.setCheckedItem(R.id.settings);
            }
        }

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FirstActivity.class);
                startActivity(intent);
            }
        });

        btnDeleteAllAlarms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmViewModel =  ViewModelProviders.of(MainActivity.this).get(AlarmViewModel.class);
                alarmViewModel.deleteAllAlarms();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.alarm_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AlarmListFragment()).commit();
                break;
            case R.id.device_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DeviceListFragment()).commit();
                break;
            case R.id.add_device:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddDeviceFragment()).commit();
                break;
            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingsFragment()).commit();
                break;
            case R.id.about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AboutFragment()).commit();
                break;
            case R.id.contact:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
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



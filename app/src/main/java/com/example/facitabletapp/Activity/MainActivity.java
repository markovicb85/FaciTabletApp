package com.example.facitabletapp.Activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.facitabletapp.Fragment.AddDeviceFragment;
import com.example.facitabletapp.Fragment.AlarmListFragment;
import com.example.facitabletapp.Fragment.ContactFragment;
import com.example.facitabletapp.Fragment.DeviceListFragment;
import com.example.facitabletapp.Fragment.AboutFragment;
import com.example.facitabletapp.Fragment.SettingsFragment;
import com.example.facitabletapp.R;
import com.example.facitabletapp.Tools.UDPClient;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    public NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new SettingsFragment()).commit();
            navigationView.setCheckedItem(R.id.settings);
        }


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



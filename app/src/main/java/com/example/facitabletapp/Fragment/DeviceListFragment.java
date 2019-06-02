package com.example.facitabletapp.Fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.facitabletapp.Adapter.DeviceAdapter;
import com.example.facitabletapp.R;
import com.example.facitabletapp.Room.Device;
import com.example.facitabletapp.ViewModel.DeviceViewModel;

import java.util.List;

public class DeviceListFragment extends Fragment {

    public static final int ADD_DEVICE_REQUEST = 1;
    private DeviceViewModel deviceViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_device_list, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final DeviceAdapter deviceAdapter= new DeviceAdapter();
        recyclerView.setAdapter(deviceAdapter);

        deviceViewModel =  ViewModelProviders.of(getActivity()).get(DeviceViewModel.class);
        deviceViewModel.getAllDevices().observe(getActivity(), new Observer<List<Device>>() {
            @Override
            public void onChanged(@Nullable List<Device> devices) {
                deviceAdapter.setDevices(devices);
            }
        });
        return rootView;
    }

    //TODO Postaviti ikonicu u OptionsMenu da se otvara fragment za dodavanje uredjaja i oziveti metode ispod

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_DEVICE_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddDeviceActivity.EXTRA_DEVICE_NAME);
            boolean status = data.getBooleanExtra(AddDeviceActivity.EXTRA_DEVICE_STATUS, true);

            Device note = new Device(name, status);
            deviceViewModel.insert(note);

            Toast.makeText(this, "Device saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Device not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_device:
                Intent intent = new Intent(this, AddDeviceActivity.class);
                startActivityForResult(intent, ADD_DEVICE_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
}

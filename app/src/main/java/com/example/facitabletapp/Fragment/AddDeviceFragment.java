package com.example.facitabletapp.Fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.facitabletapp.R;
import com.example.facitabletapp.Room.Device;
import com.example.facitabletapp.ViewModel.DeviceViewModel;

public class AddDeviceFragment extends Fragment {

    private DeviceViewModel deviceViewModel;
    private EditText etDeviceName;
    private CheckBox cbDeviceStatus;
    private Device device;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_device, container, false);

        setHasOptionsMenu(true);

        deviceViewModel =  ViewModelProviders.of(getActivity()).get(DeviceViewModel.class);

        etDeviceName = rootView.findViewById(R.id.et_add_device_name);
        cbDeviceStatus = rootView.findViewById(R.id.cb_device_status);

        return rootView;
    }

    private void saveDevice() {
        String name = etDeviceName.getText().toString();
        boolean status = cbDeviceStatus.isChecked();

        if (name.trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please insert a device name", Toast.LENGTH_SHORT).show();
            return;
        }

        device = new Device(name, status);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_device_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_device:
                saveDevice();
                deviceViewModel.insert(device);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DeviceListFragment()).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

package com.example.facitabletapp.Fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
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

import com.example.facitabletapp.Activity.AddDeviceActivity;
import com.example.facitabletapp.Activity.MainActivity;
import com.example.facitabletapp.Adapter.DeviceAdapter;
import com.example.facitabletapp.R;
import com.example.facitabletapp.Room.Device;
import com.example.facitabletapp.ViewModel.DeviceViewModel;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class DeviceListFragment extends Fragment {

    public static final int ADD_DEVICE_REQUEST = 1;
    private DeviceViewModel deviceViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_device_list, container, false);

        setHasOptionsMenu(true);

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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                deviceViewModel.delete(deviceAdapter.getDeviceAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        return rootView;
    }

   @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_DEVICE_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddDeviceActivity.EXTRA_DEVICE_NAME);
            boolean status = data.getBooleanExtra(AddDeviceActivity.EXTRA_DEVICE_STATUS, true);

            Device note = new Device(name, status);
            deviceViewModel.insert(note);

            Toast.makeText(getActivity(), "Device saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Device not saved", Toast.LENGTH_SHORT).show();
        }
    }

    //TODO startActivityforResult da se primeni na fragment
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_device:

                //TODO startActivityforResult da se primeni na fragment
//                Intent intent = new Intent(getActivity(), AddDeviceActivity.class);
//                startActivityForResult(intent, ADD_DEVICE_REQUEST);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddDeviceFragment()).commit();
                return true;

            case R.id.delete_all_device:

                //TODO Dodati dialog za potvrdu brisanja
                deviceViewModel.deleteAllDevice();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

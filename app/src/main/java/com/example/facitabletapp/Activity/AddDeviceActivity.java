package com.example.facitabletapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.facitabletapp.R;

public class AddDeviceActivity extends AppCompatActivity {

    public static final String EXTRA_DEVICE_NAME =
            "com.example.facitabletapp.Activity.EXTRA_DEVICE_NAME";

    public static final String EXTRA_DEVICE_STATUS =
            "com.example.facitabletapp.Activity.EXTRA_DEVICE_STATUS";

    private EditText etDeviceName;
    private CheckBox cbDeviceStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_device);

        etDeviceName = findViewById(R.id.et_add_device_name);
        cbDeviceStatus = findViewById(R.id.cb_device_status);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add new device");
    }

    private void saveDevice() {
        String name = etDeviceName.getText().toString();
        boolean status = cbDeviceStatus.isChecked();

        if (name.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a device name", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_DEVICE_NAME, name);
        data.putExtra(EXTRA_DEVICE_STATUS, status);

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_device_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_device:
                saveDevice();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

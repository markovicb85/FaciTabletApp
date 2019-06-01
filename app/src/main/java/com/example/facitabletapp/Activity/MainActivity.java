package com.example.facitabletapp.Activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.facitabletapp.R;
import com.example.facitabletapp.Tools.UDPClient;

public class MainActivity extends AppCompatActivity {

    TextView showData;
    Button startConection;
    Button stopConection;
    UDPClient runnable;

    private Handler mainHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showData = (TextView) findViewById(R.id.tv_show_data);
        startConection = (Button) findViewById(R.id.btn_start_connection);
        stopConection = (Button) findViewById(R.id.btn_stop_connection);

    }

    public void startConnection(View view) {
        runnable = new UDPClient(showData);
        new Thread(runnable).start();
        startConection.setEnabled(false);
    }

    public void closeConnection(View view) {
        runnable.closeConnection();
        showData.setText("");
        startConection.setEnabled(true);
    }
}



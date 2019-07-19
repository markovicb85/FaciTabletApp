package com.example.facitabletapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.facitabletapp.Activity.FirstActivity;
import com.example.facitabletapp.Activity.MainActivity;
import com.example.facitabletapp.R;
import com.example.facitabletapp.Tools.UDPClient;

public class SettingsFragment extends Fragment {

    private EditText lanCode;
    private EditText portNumber;
    private EditText mainDevice;
    private Button save;

    SharedPreferences sharedPref;
    private UDPClient udpClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        lanCode = rootView.findViewById(R.id.et_add_lan_code);
        portNumber = rootView.findViewById(R.id.et_add_port_number);
        mainDevice = rootView.findViewById(R.id.et_add_main_device);
        save = rootView.findViewById(R.id.btn_save);

        sharedPref = getActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        if (sharedPref.contains("port")){
            lanCode.setText(sharedPref.getString("lanCode", ""));
            portNumber.setText(Integer.toString(sharedPref.getInt("port", 0)));
            mainDevice.setText(sharedPref.getString("device", ""));
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = lanCode.getText().toString();
                int port = Integer.parseInt(portNumber.getText().toString());
                String device = mainDevice.getText().toString();

                if (code.trim().isEmpty() && port == 0 && device.trim().isEmpty()) {
                    Toast.makeText(getActivity(), "Please insert a lan code and port number", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    editor.putString("lanCode", code);
                    editor.putInt("port", port);
                    editor.putString("device", device);
                    editor.commit();
                    Toast.makeText(getActivity(), "Lan code and port number are save successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        return rootView;
    }
}

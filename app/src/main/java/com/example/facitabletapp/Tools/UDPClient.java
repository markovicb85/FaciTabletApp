package com.example.facitabletapp.Tools;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

import com.example.facitabletapp.Room.Alarm;
import com.example.facitabletapp.ViewModel.AlarmViewModel;
import com.example.facitabletapp.ViewModel.DeviceViewModel;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static java.util.Arrays.copyOfRange;


public class UDPClient implements Runnable {

    private static String lanCode; //"C35EBB59";
    private int VIRTUAL_ROOM_DATA_SIZE = 16;
    private boolean reading = true;

    private DatagramSocket datagramSocket = null;
    private Context context;

    private byte[] key = new byte[4];
    private byte[] data = new byte[80];
    private byte[] roomData = new byte[40];

    private int i, j;
    private Alarm alarm;
    private AlarmViewModel alarmViewModel;
    private LiveData<List<Alarm>> alarmList;


    public UDPClient(AlarmViewModel alarmViewModel, Context context) {
        this.alarmViewModel = alarmViewModel;
        this.context = context;
    }


    public void run() {

        int server_port; //11099;

        SharedPreferences sharedPref = context.getSharedPreferences("AppPreferences", context.MODE_PRIVATE);
        lanCode = sharedPref.getString("lanCode", "");
        server_port = sharedPref.getInt("port", 0);

        byte[] message = new byte[80];
        byte[] bLanCode = hexStringToByteArray(lanCode);
        DatagramPacket datagramPacket = new DatagramPacket(message, message.length);
        DeviceViewModel deviceViewModel = ViewModelProviders.of((FragmentActivity) context).get(DeviceViewModel.class);
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");

        Globals globals = Globals.getInstance();
        //alarmList = globals.getAlarms(alarmViewModel);

        try {
            datagramSocket = new DatagramSocket(server_port);

            while (reading){
                datagramSocket.receive(datagramPacket);

                //Get key using XOR
                for(i = 0; i < 4; i++){
                    key[i] = (byte) (bLanCode[i] ^ message[i + 4]);
                }

                j = 0;
                //Convert alarm data using XOR
                for(i = 0; i < message.length-8; i++){
                    data[i] = (byte) (key[j] ^ message[i+8]);
                    if ((j == 3)) {
                        j = 0;
                    } else {
                        j++;
                    }
                }

                j = 0;
                //Separate data for each room
                for (i = 0; i < data.length-1; i++){
                    if(data[i] == 30){
                        //TODO Izbeci da svaki cas pravi novi Alarm objekat
                        alarm = new Alarm();
                        roomData = copyOfRange(data, j, i);
                        alarm.setAlarmName(new String (copyOfRange(roomData, 5, 9)));
                        alarm.setStatus(roomData[9]);
                        alarm.setCurrentTime(dateFormat.format(Calendar.getInstance().getTime()));
                        if(roomData.length > VIRTUAL_ROOM_DATA_SIZE) {
                            alarm.setSuplementData(new String (copyOfRange(roomData, 10, 11)));
                            alarm.setDescription(new String (copyOfRange(roomData, 14, roomData.length)));
                        }

                        if(deviceViewModel.deviceExist(alarm.getAlarmName())){
                            if (!alarmViewModel.alarmExist(alarm.getAlarmName()) && alarm.getStatus() > 3) {
                                alarmViewModel.insert(alarm);
                            }else if(alarmViewModel.alarmExist(alarm.getAlarmName()) && alarm.getStatus() < 4){
                                alarmViewModel.deleteAlarm(alarm);
                            }
                        }

                        roomData = null;
                        j = i+1;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (datagramSocket != null) {
                datagramSocket.close();
            }
        }
    }

    public void closeConnection() {
        this.reading = false;
        if (datagramSocket != null) {
            datagramSocket.close();
        }
    }

    //Method for XOR operations
    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len/2];

        for(int i = 0; i < len; i+=2){
            data[i/2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
        }

        return data;
    }

    private static String bytesToHex(byte[] hashInBytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}



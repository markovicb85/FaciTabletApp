package com.example.facitabletapp.Tools;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import static android.content.ContentValues.TAG;

public class UDPClient implements Runnable {

    private DatagramSocket datagramSocket = null;
    private boolean reading = true;
    private String lastMessage = "";
    private static String lanCode = "C35EBB59";
    private byte[] key = new byte[4];
    private byte[] data = new byte[80];
    private int j = 0;

    private TextView result;

    public UDPClient(TextView result) {
        this.result = result;
    }

    public void run() {

        int server_port = 11099;

        byte[] message = new byte[80];
        byte[] bLanCode = hexStringToByteArray(lanCode);
        DatagramPacket datagramPacket = new DatagramPacket(message, message.length);

        try {
            datagramSocket = new DatagramSocket(server_port);

            while (reading){
                datagramSocket.receive(datagramPacket);

                for(int i = 0; i < 4; i++){
                    key[i] = (byte) (bLanCode[i] ^ message[i + 4]);
                }

                for(int i = 0; i < message.length-8; i++){
                    data[i] = (byte) (key[j] ^ message[i+8]);
                    if ((j == 3)) {
                        j = 0;
                    } else {
                        j++;
                    }
                }

                for(byte b : data){
                    lastMessage += (char)b;
                }

                Handler threadHandler = new Handler(Looper.getMainLooper());
                threadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(lastMessage);
                    }
                });

                lastMessage = "";
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



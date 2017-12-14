package com.example.yukimakura.btremoteapp;
/**
 * Created by yukimakura on 2017/03/10.
 */

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.text.TextUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothCommunicator {
    private static final UUID sUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private boolean mIsRunning;

    private Activity mActivity;

    protected BluetoothCommunicatorListener mListener;

    private BluetoothDevice mBluetoothDevice;

    private BluetoothSocket mBluetoothSocket;

    private InputStream mInputStream;

    private OutputStream mOutputStream;

    protected String BtDeviceName;

    public BluetoothCommunicator(Activity activity, BluetoothCommunicatorListener listener ,String BTDevicename) {
        mIsRunning = true;
        mActivity = activity;
        mListener = listener;

        BtDeviceName = BTDevicename;

        findDevice();
        connectDevice();
    }

    public void writeMessage(final String str) {
        if (mOutputStream == null | TextUtils.isEmpty(str)) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mOutputStream.write(str.getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getBtname(String name){
        BtDeviceName = name;
    }

    public void destory() {
        mIsRunning = false;
        mActivity = null;

        disconnectDevice();
    }

    private void findDevice() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

        if (adapter == null) {
            return;
        }

        for (BluetoothDevice device : adapter.getBondedDevices()) {
            if (BtDeviceName.equals(device.getName())) {
                mBluetoothDevice = device;
                return;
            }
        }
    }

    private void connectDevice() {
        if (mBluetoothDevice == null) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BluetoothSocket socket = mBluetoothDevice
                            .createRfcommSocketToServiceRecord(sUUID);
                    socket.connect();

                    mBluetoothSocket = socket;
                    mInputStream = socket.getInputStream();
                    mOutputStream = socket.getOutputStream();

                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while (mIsRunning) {
                        bytesRead = mInputStream.read(buffer);
                        final String message = new String(buffer, 0, bytesRead);

                        if (TextUtils.isEmpty(message.trim())) {
                            continue;
                        }

                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mListener.onReceive(message.trim());
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void disconnectDevice() {
        mListener = null;

        if (mBluetoothDevice == null) {
            return;
        }

        try {
            if (mBluetoothSocket != null) {
                mBluetoothSocket.close();
                mBluetoothSocket = null;
            }

            if (mInputStream != null) {
                mInputStream.close();
                mInputStream = null;
            }

            if (mOutputStream != null) {
                mOutputStream.close();
                mOutputStream = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
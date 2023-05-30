package com.example.bditogapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class MacActivity extends AppCompatActivity {
    Button connect;
    ListView dataMac;
    ArrayAdapter<String> stringArrayAdapter;
    boolean started = false;
    ArrayList<String> stringAlist;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mac);
        connect = findViewById(R.id.buttonConnect);
        dataMac = findViewById(R.id.listMac);
        stringAlist = new ArrayList<>();
        stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,stringAlist);
        dataMac.setAdapter(stringArrayAdapter);



        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 new ConnectThread(BluetoothAdapter.getDefaultAdapter().getRemoteDevice(getIntent().getStringExtra("Address")))
                         .start();

                 started = true;

            }

        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        started = false;
    }

    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;// Socket для соединения
        private final BluetoothDevice mmDevice;// Устройство, к которому мы подключаемся
        public final UUID MY_UUID = UUID.fromString("fa916458-bbce-42f5-a016-f6e5e95a62eb"); // UUID для сервера Bluetooth

        @SuppressLint("MissingPermission")
        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket
            // because mmSocket is final.
            BluetoothSocket tmp = null;
            mmDevice = device;

            try {
                // Get a BluetoothSocket to connect with the given BluetoothDevice.
                // MY_UUID is the app's UUID string, also used in the server code.
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);// Создаем RFCOMM Socket
                // MY_UUID - уникальный идентификатор, используемый как клиентом, так и сервером Bluetooth
            } catch (IOException e) {
                Log.e(TAG, "Socket's create() method failed", e);// Log ошибки создания Socket
            }
            mmSocket = tmp;// Сохраняем Socket
        }

        @SuppressLint("MissingPermission")
        public void run() {
            BluetoothAdapter.getDefaultAdapter().cancelDiscovery();// Отменяем поиск других устройств
            try {
                mmSocket.connect(); // Устанавливаем соединение с удаленным устройством. Блокирующий вызов.
            } catch (IOException connectException) {// В случае ошибки соединения
                try {
                    mmSocket.close();// Закрываем Socket
                } catch (IOException closeException) {
                    Log.e(TAG, "Could not close the client socket", closeException);
                }
                return; // Выходим из метода ConnectThread.run() и наш поток terminate
            }

            manageMyConnectedSocket(mmSocket);// Запускаем новый поток для управления подключенным Socket
        }

        // Закрывает Socket и заставляет поток завершить работу
        public void cancel() {
            try {
                mmSocket.close();// Закрываем Socket
            } catch (IOException e) {
                Log.e(TAG, "Could not close the client socket", e);
            }
        }
        private ConnectThread connectThread;
        private TextView statusTextView;

        private void connectToDevice(BluetoothDevice device) {
            connectThread = new ConnectThread(device);
            connectThread.start();
            statusTextView.setText("Подключение..."); // обновляем надпись при начале подключения
        }

        private void disconnectFromDevice() {
            if (connectThread != null) {
                connectThread.cancel();
                connectThread = null;
                statusTextView.setText("Отключено"); // обновляем надпись при отключении
            }
        }


        private void manageMyConnectedSocket(BluetoothSocket mmSocket) {
            String stringResult = "";
            byte[] bytes = new byte[1024];

            while (mmSocket.isConnected() && started) {
                InputStream inputStream = null;
                try {
                    inputStream = mmSocket.getInputStream();// Получаем InputStream для чтения данных
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(mmSocket.isConnected());
                Integer value = 0;
                try {
                    value = inputStream.read(bytes);// Читаем данные из InputStream

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (value > 0) {
                    Integer finalValue = value;
                    runOnUiThread(new Runnable() {   //перерисовываю список в основном потоке
                        @Override
                        public void run() {
                            stringAlist.add(new String(bytes, 0, finalValue));// Добавляем новые данные в ArrayList
                            stringArrayAdapter.notifyDataSetChanged(); // Сообщаем адаптеру, что данные изменились и нужно обновить список


                        }
                    });
                }
        }
        try {
            mmSocket.close();// Закрываем Socket

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    }
 }
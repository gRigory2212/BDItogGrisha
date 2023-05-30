package com.example.bditogapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BluetothActivity extends AppCompatActivity {// Класс BluetoothActivity - это Activity, в котором осуществляется работа с Bluetooth-устройствами.
    private static final int MY_REQUEST_CODE = 1234;// MY_REQUEST_CODE - это код запроса на разрешение доступа к Bluetooth.
    ArrayList<BluetoothDevice> pairedDevices = new ArrayList<>();// ArrayList pairedDevices используется для хранения уже сопряженных с устройством Bluetooth-устройств.
    ListView btList;// ListView btList - используется для отображения найденных Bluetooth-устройств.
    Button buttBT;
    Button search ;
    BluetoothDevicesAdapter adapter;// BluetoothDevicesAdapter - это класс-адаптер, который используется для заполнения ListView btList найденными Bluetooth-устройствами.
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();// BluetoothAdapter bluetoothAdapter - это объект BluetoothAdapter, который используется для работы с Bluetooth-устройствами.

    private final BroadcastReceiver receiver = new BroadcastReceiver() {// BroadcastReceiver - это внутренний класс, который используется для получения сообщений о найденных Bluetooth-устройствах в окружении.
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (!pairedDevices.contains(device)){
                    pairedDevices.add(device);
                }
                System.out.println(device);
               adapter.notifyDataSetChanged();
            }
        }
    };




    @SuppressLint({"MissingPermission", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {// В методе onCreate() осуществляется инициализация компонентов Activity, настройка адаптера и установка слушателя на кнопку поиска Bluetooth-устройств.

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetoth);
        buttBT = findViewById(R.id.buttBT);
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);
        buttBT.setOnClickListener((v)->
                this.startActivity(new Intent(getBaseContext(), ITogMenuActivity.class))
        );
        btList = findViewById(R.id.BTlist);
        adapter = new BluetoothDevicesAdapter(this, pairedDevices);

        search = findViewById(R.id.buttonsearch);
        if(bluetoothAdapter.getScanMode()!=BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE){
            System.out.println(BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE);
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!isGpsEnabled) {
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), MY_REQUEST_CODE);
        }
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pairedDevices.clear();
                bluetoothAdapter.startDiscovery();
            }
        });
        btList.setAdapter(adapter);
        pairedDevices.addAll(bluetoothAdapter.getBondedDevices());

    }

    @Override
    protected void onDestroy() {// В методе onDestroy() производится отписка BroadcastReceiver, чтобы избежать утечки памяти.
        super.onDestroy();

        unregisterReceiver(receiver);
    }

}

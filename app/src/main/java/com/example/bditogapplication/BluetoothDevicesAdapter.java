package com.example.bditogapplication;

import static com.example.bditogapplication.ITogMenuActivity.REQUEST_ENABLE_BT;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class BluetoothDevicesAdapter extends ArrayAdapter<BluetoothDevice> {// Класс BluetoothDevicesAdapter - это адаптер для списка найденных Bluetooth-устройств.
    private Context context = null;// Context context - это контекст Activity, в которой используется адаптер.
    private ArrayList<BluetoothDevice> list = null;// ArrayList<BluetoothDevice> list - это список найденных Bluetooth-устройств.
    TextView btsen;

    public ArrayList<BluetoothDevice> getList() {
        return list;
    }


    public BluetoothDevicesAdapter(@NonNull Context context, ArrayList<BluetoothDevice> list) {
        super(context, R.layout.btsensor_item, list);
        this.context = context;
        this.list = list;


    }

    public class MyClickListener implements View.OnClickListener {// MyClickListener - это внутренний класс, который используется для обработки кликов на элементах списка Bluetooth-устройств.

        private BluetoothDevice device;
        private Context context;

        public MyClickListener(Context context, BluetoothDevice device) {
            this.context = context;
            this.device = device;
        }

        @NonNull
        @Override
        public void onClick(View view) {
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null) {
            } else {
                Intent intent = new Intent(context,ITogDataActivity.class);

                intent.putExtra("address", device.getAddress());
                intent.putExtra("name", device.getName());

                context.startActivity(intent);
            }
        }
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {// View getView() - этот метод отображает элементы списка найденных Bluetooth-устройств.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.btsensor_item, parent, false);
        }
        btsen = convertView.findViewById(R.id.textViewbtttt);
        BluetoothDevice item = getItem(position);

        MyClickListener clickListener = new MyClickListener(context, item);
        btsen.setOnClickListener(clickListener);// Внутри метода getView() устанавливается слушатель clickListener на TextView btsen, который при клике на элемент списка запускает новое Activity ITogDataActivity.
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

        }
        btsen.setText(item.getName());// В методе getView() отображаются название и MAC-адрес найденного Bluetooth-устройства в TextView btsen.
        if (item.getName() == null || item.getName().isEmpty()){// Если название устройства не указано (null или пустое), то в TextView отображается его MAC-адрес.
            btsen.setText(item.getAddress());

        }

        return convertView;
    }

}


package com.example.bditogapplication;

import static com.example.bditogapplication.ITogMenuActivity.REQUEST_ENABLE_BT;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class BTHelper {// Класс BTHelper - это класс-помощник для работы с Bluetooth-соединением.
    public static boolean check(Context ctx){// Метод check используется для проверки состояния Bluetooth-адаптера устройства.
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();// Вначале создается объект BluetoothAdapter с помощью метода getDefaultAdapter().

        if (!mBluetoothAdapter.isEnabled()) {// Затем происходит проверка: если Bluetooth-адаптер выключен, то создается экземпляр Intent для запроса разрешения на включение
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (ActivityCompat.checkSelfPermission(ctx, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                ((AppCompatActivity)ctx).startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);// Bluetooth и начинается запрос разрешения на включение Bluetooth startActivityForResult
            }
            return false;// После этого метод check возвращает false.
        }
        return true;// Если Bluetooth-адаптер уже включен, метод check возвращает true.
    }
}

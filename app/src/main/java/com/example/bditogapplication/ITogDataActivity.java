package com.example.bditogapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ITogDataActivity extends AppCompatActivity {// Эта активность отображает форму для добавления нового сенсора в базу данных
    // Создаем объект для работы с базой данных сенсоров
    // и определяем поля ввода и кнопки для сохранения и выхода из активности
    EditText sensor, unit, deviseET, addressET;
    Button savePoint;
    Button back;
    DBSEnsor dbPoints;
    Intent intent;
    BluetoothDevice device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itog_data); // Инициализируем объект для работы с базой данных
        dbPoints = new DBSEnsor(getBaseContext());
        sensor = findViewById(R.id.sensor);
        unit = findViewById(R.id.unit);
        deviseET = findViewById(R.id.deviceET);
        addressET = findViewById(R.id.address); // Инициализируем поля ввода и кнопки
        intent = getIntent();
        if(intent != null){ // Если было передано устройство Bluetooth, заполняем поля для его отображения
            String address = intent.getStringExtra("address");
            String name = intent.getStringExtra("name");

            deviseET.setText(name);
            addressET.setText(address);

        }


        savePoint = findViewById(R.id.savePoint);
        back = findViewById(R.id.buttonDatabk);
        back.setOnClickListener((v) -> finish());

        savePoint.setOnClickListener((v)->{ // Обработка нажатия кнопки сохранения
            SensorEntity pe = new SensorEntity(// Создаем новый объект сенсора и сохраняем его в базу данных
                    sensor.getText().toString(),
                    unit.getText().toString(),
                    deviseET.getText().toString(),
                    addressET.getText().toString()

            );
            dbPoints.insert(pe);
            finish();
        });
    }
}
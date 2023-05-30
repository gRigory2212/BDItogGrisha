package com.example.bditogapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ITogMenuActivity extends AppCompatActivity {// Эта активность отображает меню приложения
    public static final int REQUEST_ENABLE_BT = 2;
    DBSEnsor dbSensor;// Создаем объект для работы с базой данных сенсоров
    Button buttondata;
    Button buttonlist;
    Button buttonBT;// и определяем кнопки для перехода на другие активности

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itog_menu);// Инициализируем объект для работы с базой данных
        dbSensor = new DBSEnsor(this);
        buttondata = findViewById(R.id.buttonData);    // Инициализируем кнопки и устанавливаем для них обработчики нажатий
        buttonlist = findViewById(R.id.buttonList);
        buttonBT = findViewById(R.id.buttonBT2);
        buttondata.setOnClickListener((v) ->
                startActivity(new Intent(getBaseContext(), ITogDataActivity.class)) // Переход на активность с отображением данных сенсоров
        );
        buttonlist.setOnClickListener((v) ->
                this.startActivity(new Intent(getBaseContext(), ITogListActivity.class))   // Переход на активность со списком сенсоров
        );
        buttonBT.setOnClickListener((v)->
                this.startActivity(new Intent(getBaseContext(), BluetothActivity.class)));   // Переход на активность для работы с Bluetooth
        if(!BTHelper.check(this)){   // Проверяем, включен ли Bluetooth
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {// Обработка результата запроса на включение Bluetooth
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_CANCELED){   // Закрываем приложение, если Bluetooth не был включен
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {// Обработка запроса на разрешение использования различных функций устройства
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);    // Дополнительный функционал может быть добавлен в этот метод в будущем

    }
}
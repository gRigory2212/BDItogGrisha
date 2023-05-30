package com.example.bditogapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ITogListActivity extends AppCompatActivity {// Эта активность отображает устройств сенсоров из базы данных
    DBSEnsor dbSensor;// Создаем объект для работы с базой данных сенсоров
    SensorAdapter a;
    Button back;
    Button clear;
    ListView listbti;// и определяем кнопки для выхода из активности и удаления сенсоров
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itog_list);// Инициализируем объект для работы с базой данных
        dbSensor =  new DBSEnsor(getBaseContext());
        back = findViewById(R.id.buttonExit);
        clear = findViewById(R.id.buttonClear);
        listbti = findViewById(R.id.listIT); // Инициализируем кнопки и устанавливаем для них обработчики нажатий
        clear.setOnClickListener((v)->{ // Обработка нажатия кнопки удаления
            ArrayList<SensorEntity> sensors = a.getList(); // Получаем список всех сенсоров
            for(SensorEntity s : sensors){
                if (s.isRemove()){
                      dbSensor.delete(s); // Проходимся по списку и удаляем отмеченные сенсоры из базы данных
                }

            }
        });
        back.setOnClickListener((v)->  // Обработка нажатия кнопки выхода
                finish()
        );

    }

    protected void onResume() {// Здесь создается адаптер для отображения списка сенсоров
        super.onResume();
        a = new SensorAdapter(
                this, dbSensor.selectAll()
        );
        listbti.setAdapter(a);
    }
}
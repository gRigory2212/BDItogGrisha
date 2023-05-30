package com.example.bditogapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {// Класс DBHelper - это класс-помощник для работы с базой данных SQLite. Он наследует класс SQLiteOpenHelper
    public static final String SR_NAME = "sensor3.db";// SR_NAME - это имя базы данных.
    public static final String SENSORS_TABLE_NAME = "sensor";// SENSORS_TABLE_NAME - это имя таблицы в базе данных.
    public static final int SR_VERSION = 11;// SR_VERSION - это версия базы данных.

    public static final String SENSOR_ID = "id";
    public static final int SENSOR_ID_NUM = 0;
    public static final String SENSOR_NAME = "sensor";
    public static final int SENSOR_NAME_NUM = 1;
    public static final String SENSOR_UNIT = "unit";
    public static final int SENSOR_UNIT_NUM = 2;
    public static final String SENSOR_DEVICE = "device";
    public static final int SENSOR_DEVICE_NUM = 3;
    public static final String SENSOR_ADDRESS = "address";
    public static final int SENSOR_ADDRESS_NUM = 4;
    // SENSOR_ID, SENSOR_NAME, SENSOR_UNIT, SENSOR_DEVICE, SENSOR_ADDRESS - это константы для работы с полями таблицы.

    public DBHelper(Context ctx){
        super(ctx, SR_NAME, null, SR_VERSION );
    }// В конструкторе класса DBHelper создается база данных с именем SR_NAME и версией SR_VERSION.

    @Override
    public void onCreate(SQLiteDatabase db) {// Метод onCreate вызывается при создании базы данных и создает таблицу SENSORS_TABLE_NAME с полями SENSOR_ID, SENSOR_NAME, SENSOR_UNIT, SENSOR_DEVICE, SENSOR_ADDRESS.
        String qwerty = "create table " + SENSORS_TABLE_NAME +"("+// Для этого создается строка запроса, содержащая SQL-инструкцию CREATE TABLE.
                SENSOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SENSOR_NAME + " TEXT," +
                SENSOR_UNIT + " TEXT," +
                SENSOR_DEVICE + " TEXT," +
                SENSOR_ADDRESS + " TEXT" +
                ")" ;
        db.execSQL(qwerty);// Затем запрос выполняется методом execSQL объекта SQLiteDatabase переданной базы данных.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int ov, int nv) {// Метод onUpgrade вызывается при обновлении базы данных на более новую версию и удаляет старую таблицу SENSORS_TABLE_NAME и создает новую при помощи метода onCreate.
        db.execSQL("drop table " + SENSORS_TABLE_NAME);
        onCreate(db);
    }
}

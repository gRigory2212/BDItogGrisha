package com.example.bditogapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBSEnsor {
    DBHelper dbsen;
    SQLiteDatabase db;


    public DBSEnsor(Context ctx) {// Конструктор класса получает контекст и создает экземпляр класса DBHelper, содержащий методы для работы с базой данных.
        dbsen = new DBHelper(ctx);
        db = dbsen.getWritableDatabase();

    }

    public void insert(SensorEntity se) {// Метод insert добавляет новый элемент в таблицу базы данных. Для этого создается объект ContentValues, содержащий данные датчика,
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.SENSOR_NAME, se.getName());
        cv.put(DBHelper.SENSOR_UNIT, se.getUnit());
        cv.put(DBHelper.SENSOR_ADDRESS, se.getAddress());
        cv.put(DBHelper.SENSOR_DEVICE,se.getDevice());
        db.insert(DBHelper.SENSORS_TABLE_NAME, null, cv);// переданные в качестве параметра. Затем данные вставляются в таблицу при помощи метода insert объекта SQLiteDatabase.

    }

    public void delete(SensorEntity se){// Метод delete удаляет элемент из таблицы по его идентификатору. Для этого формируется массив аргументов из id переданного датчика,
        String[] args = new String[1];
        args[0]= se.getId()+"";
        db.delete(DBHelper.SENSORS_TABLE_NAME, "id = ?", args);// и осуществляется удаление записи из таблицы при помощи метода delete объекта SQLiteDatabase.

    }

    public ArrayList<SensorEntity> selectAll() {// Метод selectAll возвращает список всех элементов из таблицы базы данных.
        ArrayList<SensorEntity> list = new ArrayList<>();
        Cursor cursor = db.query(DBHelper.SENSORS_TABLE_NAME, null, null, null, null, null, null);// При помощи метода query объекта SQLiteDatabase получаем все строки из таблицы.
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                SensorEntity pe = new SensorEntity(
                        cursor.getString(DBHelper.SENSOR_NAME_NUM),
                        cursor.getString(DBHelper.SENSOR_UNIT_NUM),
                        cursor.getString(DBHelper.SENSOR_DEVICE_NUM),
                        cursor.getString(DBHelper.SENSOR_ADDRESS_NUM));
                pe.setId(cursor.getInt(DBHelper.SENSOR_ID_NUM));// После некоторых манипуляций с объектом Cursor формируется список элементов SensorEntity из полученных данных.
                list.add(pe);
            } while (cursor.moveToNext());
        }
        return list;
    }
}

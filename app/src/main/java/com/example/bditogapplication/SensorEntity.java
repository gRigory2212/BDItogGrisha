package com.example.bditogapplication;

public class SensorEntity {
    private int id;
    private String name;
    private String unit;
    private boolean remove;

    private String device;
    private String address;

    // Конструктор сенсора без указания идентификатора
    public SensorEntity( String name, String unit, String device, String address) {
        this.name = name;
        this.unit = unit;
        this.device = device;
        this.address = address;
    }

    // Метод, возвращающий строковое представление сенсора
    @Override
    public String toString() {
        return "SensorEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", remove=" + remove +
                ", device='" + device + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    // Конструктор сенсора с указанием всех параметров

    public SensorEntity(int id, String name, String unit, boolean remove, String device, String address) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.remove = remove;
        this.device = device;
        this.address = address;
    }

    // Геттеры и сеттеры полей класса

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
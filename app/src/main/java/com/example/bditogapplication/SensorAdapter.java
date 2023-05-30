package com.example.bditogapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SensorAdapter extends ArrayAdapter<SensorEntity> {// Создаем адаптер для списка датчиков, который наследуется от ArrayAdapter<SensorEntity>.
    private Context context = null;
    TextView textSensor;
    TextView textUnit;
    TextView textAddress;
    TextView textDevice;
    CheckBox choose;
    ArrayList<SensorEntity> list;

    public ArrayList<SensorEntity> getList() {
        return list;
    }

    public SensorAdapter(@NonNull Context context, ArrayList<SensorEntity> list) {// В конструкторе сохраняем список датчиков и контекст, необходимый для работы с интерфейсом.
        super(context,R.layout.mysensor_item ,list);
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) { //создаем новый элемент списка и заполняем его информацией из соответствующего SensorEntity.

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.mysensor_item,parent,false);
        }
        textSensor=convertView.findViewById(R.id.textViewsensortx );
        textUnit=convertView.findViewById(R.id.textViewunittx );
        textAddress=convertView.findViewById(R.id.textViewaddresstx );
        textDevice=convertView.findViewById(R.id.textViewdevicetx );
        choose=convertView.findViewById(R.id.checkBoxDE);
        SensorEntity item =getItem(position);

        textAddress.setOnClickListener(new View.OnClickListener() {// Установка слушателя клика на адрес датчика, чтобы при клике на него открывалась соответствующая страница.

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MacActivity.class);
                intent.putExtra("Address",item.getAddress());
                context.startActivity(intent);
            }
        });


        View finalConvertView = convertView;
        choose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {// Установка слушателя клика на адрес датчика, чтобы при клике на него открывалась соответствующая страница.

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                item.setRemove(b);// При изменении флажка меняем значение remove у SensorEntity и меняем фоновый цвет соответствующего элемента списка.
                if (b){
                    finalConvertView.setBackgroundColor(Color.MAGENTA);
                }else{
                    finalConvertView.setBackgroundColor(Color.WHITE);
                }
            }
        });
        choose.setChecked(item.isRemove());
        textSensor.setText(item.getName());
        textUnit.setText(item.getUnit()+ " ");
        textAddress.setText(item.getAddress());
        textDevice.setText(item.getDevice());

           textSensor.setTextColor(Color.BLUE);
           textUnit.setTextColor(Color.BLUE);
           textAddress.setTextColor(Color.BLUE);
           textDevice.setTextColor(Color.BLUE);


        return convertView;
    }


}

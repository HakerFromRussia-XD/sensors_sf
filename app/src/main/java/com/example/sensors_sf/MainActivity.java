package com.example.sensors_sf;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private SensorManager sensorManager;
    private Sensor sensor;

    private Thread threadSensors = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = findViewById(R.id.textView);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);


//        textView.setText((int) sensor.getPower() + sensor.getName());
        refresh();
    }

    private void refresh() {
        threadSensors = new Thread(() -> {
            while (true) {
                runOnUiThread(() -> {
                    textView.setText((int) sensor.getPower() + sensor.getName());
                });
                System.err.println("отправили новые данные getPower: "+sensor.getPower());
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    System.err.println("случилась ошибка "+e);
                }
            }
        });
        threadSensors.start();
    }
}
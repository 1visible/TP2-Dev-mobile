package com.example.tp2_exercice3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    ConstraintLayout layout;
    private TextView tv;
    private double inf, sup, min, max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.clayout);
        tv = findViewById(R.id.tv);
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sup = sensor.getMaximumRange() / 8;
            inf = -sup;
            min = 0;
            max = 0;
            if(!sensorManager.registerListener((SensorEventListener) this, sensor, SensorManager.SENSOR_DELAY_UI)) {
                sensorManager.unregisterListener((SensorEventListener) this, sensor);
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double x = event.values[0];
        double y = event.values[1];
        double z = event.values[2];
        double absMax = Math.max(Math.max(Math.abs(x), Math.abs(y)), Math.abs(z));
        if(absMax != x && absMax != y && absMax != z) absMax *= -1;
        if(absMax < min) min = absMax;
        if(absMax > max) max = absMax;
        String text = getResources().getString(R.string.xyz, x, y, z, min, max);

        tv.setText(text);

        if(absMax < inf) layout.setBackgroundColor(Color.GREEN);
        else if(absMax > sup) layout.setBackgroundColor(Color.RED);
        else layout.setBackgroundColor(Color.BLACK);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

}
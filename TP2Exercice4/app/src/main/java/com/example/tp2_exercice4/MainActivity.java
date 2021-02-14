package com.example.tp2_exercice4;

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

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if(!sensorManager.registerListener((SensorEventListener) this, sensor, SensorManager.SENSOR_DELAY_GAME)) {
                sensorManager.unregisterListener((SensorEventListener) this, sensor);
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double x = event.values[0];
        double y = event.values[1];
        String horizontal = getResources().getString(R.string.none);
        String vertical = getResources().getString(R.string.none);

        // Valeur positive de x qui dépasse le threshold -> Mouvement à gauche
        if(x > 1) {
            horizontal = getResources().getString(R.string.left);
        // Valeur négative de x qui dépasse le threshold -> Mouvement à droite
        }else if(x < -1) {
            horizontal = getResources().getString(R.string.right);
        }

        // Valeur positive de y qui dépasse le threshold -> Mouvement en bas
        if(y > 1) {
            vertical = getResources().getString(R.string.down);
        // Valeur négative de y qui dépasse le threshold -> Mouvement en haut
        }else if(y < -1) {
            vertical = getResources().getString(R.string.up);
        }

        tv.setText(getResources().getString(R.string.direction, horizontal, vertical));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

}
package com.example.tp2_exercice2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView motionDetectText, accelerometerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        motionDetectText = findViewById(R.id.motion_detect);
        accelerometerText = findViewById(R.id.accelerometer);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        String motionDetectState =
                (sensorManager.getDefaultSensor(Sensor.TYPE_MOTION_DETECT) != null) ?
                        getResources().getString(R.string.disponible) : getResources().getString(R.string.indisponible);
        String accelerometerState =
                (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) ?
                        getResources().getString(R.string.disponible) : getResources().getString(R.string.indisponible);

        motionDetectText.append(motionDetectState);
        accelerometerText.append(accelerometerState);
    }
}
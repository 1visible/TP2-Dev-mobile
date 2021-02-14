package com.example.tp2_exercice6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = findViewById(R.id.zoom);
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Drawable dr = getResources().getDrawable(android.R.drawable.ic_menu_search);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        Drawable d = new BitmapDrawable(getResources(), bitmap);
        image.setImageDrawable(d);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
            Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            if(!sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)) {
                sensorManager.unregisterListener(this, sensor);
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float zoomFactor = event.values[0] * 20;
        // Prevent negative scale
        int scale = Math.max(1, Math.round(300 - zoomFactor));
        Drawable dr = getResources().getDrawable(android.R.drawable.ic_menu_search);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        bitmap = Bitmap.createScaledBitmap(bitmap, scale, scale, true);
        Drawable d = new BitmapDrawable(getResources(), bitmap);
        image.setImageDrawable(d);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

}
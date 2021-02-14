package com.example.tp2_exercice5;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private double lastX, lastY, lastZ;
    private long lastUpdate;
    private boolean isFlashing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lastX = 0;
        lastY = 0;
        lastZ = 0;
        lastUpdate = 0;
        isFlashing = false;
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
        double z = event.values[2];
        double gX = x / SensorManager.GRAVITY_EARTH;
        double gY = y / SensorManager.GRAVITY_EARTH;
        double gZ = z / SensorManager.GRAVITY_EARTH;
        double gForce = Math.sqrt(gX * gX + gY * gY + gZ * gZ);
        if(gForce > 2.5) { // On prend un threshold de 2.5 pour la secousse
            // On s'assure de ne pas récupérer des données trop proches dans le temps(< 500ms)
            long dt = System.currentTimeMillis() - lastUpdate;
            if(dt <= 500)
                return;

            lastUpdate = System.currentTimeMillis();
            // On vérifie les permissions pour la caméra
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // Demande des permissions pour accéder à la caméra (et au flash)
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 50);
            }
            // On vérifie que l'appareil possède un flash
            else if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                // Service disponible uniquement pour certaines versions d'Android
                CameraManager cameraManager = null;
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    try {
                        String cameraId = cameraManager.getCameraIdList()[0];
                        isFlashing = !isFlashing;
                        cameraManager.setTorchMode(cameraId, isFlashing);
                    } catch (CameraAccessException ignored) { }
                }
            }
        }
        lastX = x;
        lastY = y;
        lastZ = z;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }



}
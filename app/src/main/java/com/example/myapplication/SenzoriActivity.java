package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class SenzoriActivity extends AppCompatActivity implements SensorEventListener, LocationListener
{
    private SensorManager mSensorManager;

    private Sensor mSensorProximity;
    private Sensor mSensorPressure;
    private Sensor mSensorLight;
    private Sensor mSensorAccelerometer;
    private LocationManager locationManager;

    private TextView loc;

    private final String[] desc = new String[4];
    private String[] list = new String[]{"PROXIMITATE","PRESIUNE","LUMINA","ACCELEROMETRU","LOCATION"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senzori);

        final TextView descriere = (TextView)findViewById(R.id.descriere);
        ListView l = (ListView)findViewById(R.id.listViewSenzori);
        loc = (TextView)findViewById(R.id.location);


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (mSensorProximity != null) {
            mSensorManager.registerListener(this, mSensorProximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorPressure != null) {
            mSensorManager.registerListener(this, mSensorPressure,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorLight != null) {
            mSensorManager.registerListener(this, mSensorLight,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorAccelerometer != null) {
            mSensorManager.registerListener(this, mSensorAccelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        ArrayList<String> ElementsArrayList = new ArrayList<String>(Arrays.asList(list));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ElementsArrayList);

        l.setAdapter(adapter);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                descriere.setText(desc[position]);
            }
        });

        if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

        if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,10,this);
    }


    @Override
    public void onSensorChanged(SensorEvent event)
    {
        int type = event.sensor.getType();

        float value = event.values[0];

        switch (type)
        {
            case Sensor.TYPE_PROXIMITY:
                desc[0] = String.valueOf(value);
                break;
            case Sensor.TYPE_PRESSURE:
                desc[1] = String.valueOf(value);
                break;
            case Sensor.TYPE_LIGHT:
                desc[2] = String.valueOf(value);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                desc[3] = "x: " + event.values[0]+" y: " + event.values[1] + " z: " + event.values[2];
                break;

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    @Override
    public void onLocationChanged(Location location)
    {
        String location_string = "Location changed: Lat: " + location.getLatitude() + " Lng: " + location.getLongitude();

        loc.setText(location_string);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {

    }

    @Override
    public void onProviderEnabled(String provider)
    {

    }

    @Override
    public void onProviderDisabled(String provider)
    {

    }
}

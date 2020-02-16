package edu.neu.madcourse.numads20_pankajbadgujar;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


public class LatitudeLongitude extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;
    private final Context context = this;
    private LocationManager lm;
    private TextView latitudeValue;
    private TextView longitudeValue;

    private final String TAG = "LOCATION_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latitude_longitude);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        latitudeValue = findViewById(R.id.latitudeValue);
        longitudeValue = findViewById(R.id.longitudeValue);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        calculateLatitudeLongitude();
    }


    private void calculateLatitudeLongitude() {

        lm = (LocationManager) getSystemService(context.LOCATION_SERVICE);

        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            startGPS();
        } else {

            getLocation();
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNetwork = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location locationPassive = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (locationGPS != null) {
                Log.i(TAG,"GPS");
                extractCoordinatesAndDisplay(locationGPS);
            } else if (locationNetwork != null) {
                Log.i(TAG,"Network");
                extractCoordinatesAndDisplay(locationNetwork);
            } else
                if (locationPassive != null) {
                Log.i(TAG,"Passive");
                extractCoordinatesAndDisplay(locationPassive);

            } else {
                Toast.makeText(this, "Cannot get your location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void extractCoordinatesAndDisplay(Location location) {
        String latitude = String.valueOf(location.getLatitude());
        String longitude = String.valueOf(location.getLongitude());

        latitudeValue.setText(latitude);
        longitudeValue.setText(longitude);
    }

    private void startGPS() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS ?")
                .setCancelable(false)
                .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                }).setNegativeButton("Do not Enable", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }
}

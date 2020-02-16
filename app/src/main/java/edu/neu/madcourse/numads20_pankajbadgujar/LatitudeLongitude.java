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

import java.util.HashMap;
import java.util.Map;

public class LatitudeLongitude extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;
    private final Context context = this;
    private LocationManager lm;

    //textview for GPS location
    private TextView latGPS;
    private TextView longiGPS;

    //textview for Network location
    private TextView latNetwork;
    private TextView longiNetwork;

    //textview for passive location
    private TextView latPassive;
    private TextView longiPassive;


    private final String TAG = "LOCATION_TAG";

    private Map<String, TextView[]> locationFieldsMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latitude_longitude);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize GPS fields
        latGPS = findViewById(R.id.latGpsVal);
        longiGPS = findViewById(R.id.lonGpsVal);

        //initialize network fields
        latNetwork = findViewById(R.id.latNetworkVal);
        longiNetwork = findViewById(R.id.lonNetworkVal);

        //initialize passive fields
        latPassive = findViewById(R.id.latPassiveVal);
        longiPassive = findViewById(R.id.lonPassiveVal);

        locationFieldsMap = new HashMap<>();
        populateLocationFieldsMap();

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        calculateLatitudeLongitude();
    }

    private void populateLocationFieldsMap() {
        locationFieldsMap.put("GPS", new TextView[]{latGPS, longiGPS});
        locationFieldsMap.put("Network", new TextView[]{latNetwork, longiNetwork});
        locationFieldsMap.put("Passive", new TextView[]{latPassive, longiPassive});
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


            renderLocation(locationGPS, "GPS");
            renderLocation(locationNetwork, "Network");
            renderLocation(locationPassive, "Passive");
        }
    }

    private void renderLocation(Location location, String locationType) {

        String latitude;
        String longitude;
        TextView[] fields = locationFieldsMap.get(locationType);

        if (location == null) {

            latitude = getText(R.string.value_not_fetched).toString();
            longitude = getText(R.string.value_not_fetched).toString();

        } else {

            latitude = String.valueOf(location.getLatitude());
            longitude = String.valueOf(location.getLongitude());
        }

        //logging results for verification
        Log.i(locationType,latitude);
        Log.i(locationType,longitude);

        try {
            fields[0].setText(latitude);
            fields[1].setText(longitude);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
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

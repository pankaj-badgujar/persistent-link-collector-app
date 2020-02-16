package edu.neu.madcourse.numads20_pankajbadgujar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;


public class Home extends AppCompatActivity {

    private final Context context = this;
    private BroadcastReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        configureReceiver();
    }

    public void startAboutActivity(View view) {
        startActivity(new Intent(this, HelloWorld.class));
    }

    public void startLinkCollectorActivity(View view) {
        startActivity(new Intent(this, LinkCollector.class));

    }

    public void startMathMagicActivity(View view) {
        startActivity(new Intent(this, MathMagic.class));
    }

    private void configureReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        filter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        receiver = new PowerChangeReceiver();
        registerReceiver(receiver, filter);

    }

    public void onLatitudeLongitudeButtonClick(View view){

        int[] values = calculateLatitudeLongitude();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder
                .setMessage(getString(R.string.latitude,values[0])+"\n\n"+
                        getString(R.string.longitude,values[1]))
                .setCancelable(false)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog latitudeLongitudeDialog = alertDialogBuilder.create();
        latitudeLongitudeDialog.show();
    }

    private int[] calculateLatitudeLongitude() {
        return new int[]{123,343};
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}

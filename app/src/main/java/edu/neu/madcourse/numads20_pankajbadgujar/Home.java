package edu.neu.madcourse.numads20_pankajbadgujar;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

public class Home extends AppCompatActivity {

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
        Toast.makeText(this,"latitude",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}

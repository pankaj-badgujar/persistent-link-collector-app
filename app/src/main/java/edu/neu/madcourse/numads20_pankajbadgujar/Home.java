package edu.neu.madcourse.numads20_pankajbadgujar;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


    public void startAboutActivity(View view){
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

    public void startLinkCollectorActivity(View view){
        Intent intent = new Intent(this,LinkCollector.class);
        startActivity(intent);

    }

}
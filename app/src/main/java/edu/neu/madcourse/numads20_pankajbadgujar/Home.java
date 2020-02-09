package edu.neu.madcourse.numads20_pankajbadgujar;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


    public void startAboutActivity(View view){
        startActivity(new Intent(this, HelloWorld.class));
    }

    public void startLinkCollectorActivity(View view){
        startActivity(new Intent(this,LinkCollector.class));

    }

    public void startMathMagicActivity(View view){
        startActivity(new Intent(this,MathMagic.class));
    }

}

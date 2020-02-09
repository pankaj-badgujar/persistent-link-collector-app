package edu.neu.madcourse.numads20_pankajbadgujar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MathMagic extends AppCompatActivity {

    private Button findPrimesBtn;
    private Button cancelSearchBtn;
    private AsyncTask findPrimesTask;
    private TextView currentStatusTxt;
    protected ListView primesListView;
    protected ArrayAdapter<Integer> arrayAdapter;
    protected List<Integer> primes;
    protected TextView primeNumbersFoundLabel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //start displaying the activity
        setContentView(R.layout.activity_math_magic);

        //adding action bar at the top of the activity
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initializing the components
        findPrimesBtn = (Button) findViewById(R.id.findPrimesBtn);
        cancelSearchBtn = (Button) findViewById(R.id.cancelSearchBtn);
        currentStatusTxt = (TextView) findViewById(R.id.currentStatusTxt);
        primesListView = (ListView) findViewById(R.id.primesList);
        primes = new ArrayList<>();
        primeNumbersFoundLabel = (TextView) findViewById(R.id.primeNumbersFoundLabel);

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, primes);

        primesListView.setAdapter(arrayAdapter);

        //keeping cancel prime search button invisible at the start
        cancelSearchBtn.setVisibility(View.INVISIBLE);

        //keeping prime numbers found label hidden at the start
        primeNumbersFoundLabel.setVisibility(View.INVISIBLE);
    }

    public void startFindPrimeTask(View view) {
        showCancelButton();
        findPrimesTask = new FindPrimesTask();
        findPrimesTask.execute();

    }

    public void cancelFindPrimeTask(View view) {
        showFindPrimesButton();
        findPrimesTask.cancel(true);
    }

    public void startWatchTime(View view) {
        final int ONE_MINUTE_INTERVAL = 60000;

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, WatchTimeReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 1, intent, 0);

        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), ONE_MINUTE_INTERVAL, pi);
        Snackbar.make(view, R.string.watch_time_started, Snackbar.LENGTH_SHORT).show();
    }

    public void cancelWatchTime(View view) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, WatchTimeReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 1, intent, 0);

        am.cancel(pi);
        Snackbar.make(view, R.string.watch_time_cancelled, Snackbar.LENGTH_SHORT).show();
    }


    private void showCancelButton() {
        findPrimesBtn.setVisibility(View.INVISIBLE);
        cancelSearchBtn.setVisibility(View.VISIBLE);
    }

    private void showFindPrimesButton() {
        cancelSearchBtn.setVisibility(View.INVISIBLE);
        findPrimesBtn.setVisibility(View.VISIBLE);
    }


    private class FindPrimesTask extends AsyncTask<Object, Integer, String> {

        @Override
        protected void onPreExecute() {
            primeNumbersFoundLabel.setVisibility(View.VISIBLE);
            primes.clear();
        }

        @Override
        protected String doInBackground(Object... voids) {
            int current = 2;
            while (true) {

                if (isCancelled()) {
                    break;
                }

                boolean isPrime = true;
                publishProgress(current);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {

                }

                for (int i = current - 1; i > 1; i--) {
                    if (current % i == 0) {
                        isPrime = false;
                        break;
                    }
                }
                if (isPrime) {
                    primes.add(current);
                }
                current++;
            }

            return null;
        }


        @Override
        protected void onProgressUpdate(Integer... current) {
            updateCurrentStatus(current[0]);
            primesListView.setAdapter(arrayAdapter);
        }

        private void updateCurrentStatus(int number) {
            String sentence = getString(R.string.currentStatus, number);
            currentStatusTxt.setText(sentence);
        }

        @Override
        protected void onCancelled() {
            currentStatusTxt.setText(R.string.searchEndedTxt);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (findPrimesTask != null) {
            findPrimesTask.cancel(true);
        }
    }

}

package edu.neu.madcourse.numads20_pankajbadgujar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MathMagic extends AppCompatActivity {

    private Button findPrimesBtn;
    private Button cancelSearchBtn;
    private AsyncTask findPrimesTask;
    private TextView currentStatusTxt;

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


        //keeping cancel prime search button invisible at the start
        cancelSearchBtn.setVisibility(View.INVISIBLE);
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

    private void showCancelButton() {
        findPrimesBtn.setVisibility(View.INVISIBLE);
        cancelSearchBtn.setVisibility(View.VISIBLE);
    }

    private void showFindPrimesButton() {
        cancelSearchBtn.setVisibility(View.INVISIBLE);
        findPrimesBtn.setVisibility(View.VISIBLE);
    }


    private class FindPrimesTask extends AsyncTask<Object, Integer, String> {

        private List<Integer> primes = new ArrayList<>();

        @Override
        protected void onCancelled() {
            currentStatusTxt.setText(R.string.searchEndedTxt);
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
                    Thread.sleep(600);
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
        }

        private void updateCurrentStatus(int number){
            String sentence = getString(R.string.currentStatus,number);
            currentStatusTxt.setText(sentence);
        }

        @Override
        protected void onPostExecute(String aVoid) {

        }
    }

}

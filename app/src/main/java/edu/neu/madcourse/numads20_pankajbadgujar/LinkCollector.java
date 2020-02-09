package edu.neu.madcourse.numads20_pankajbadgujar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LinkCollector extends AppCompatActivity {

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final List<String[]> linkNameList = new ArrayList<>();


        //creating array adapter for simple_list_item_2
        ArrayAdapter<String[]> adapter = new ArrayAdapter<String[]>(this,
                android.R.layout.simple_list_item_2, android.R.id.text1, linkNameList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                String[] entry = linkNameList.get(position);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText(entry[0]);
                text2.setText(entry[1]);

                return view;
            }
        };


        //setting adapter for the list view
        ListView listView = findViewById(R.id.linkList);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String URL = linkNameList.get(position)[1];
                Uri uri = Uri.parse("http://" + URL);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get activity_input_promptut_prompt.xml view
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                final View parentView = view;
                final View promptView = layoutInflater.inflate(R.layout.activity_input_prompt, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set activity_input_promptut_prompt.xml to be the layout file of the inputDialog builder
                alertDialogBuilder.setView(promptView);

                final EditText nameInput = (EditText) promptView.findViewById(R.id.linkNameInput);
                final EditText urlInput = (EditText) promptView.findViewById(R.id.linkUrlInput);


                // setup a dialog window
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                String name = nameInput.getText().toString();
                                String url = urlInput.getText().toString();

                                if (name.equalsIgnoreCase("") || url.equalsIgnoreCase("")) {
                                    Toast.makeText(context, "Name and URL is required! ", Toast.LENGTH_LONG).show();
                                } else {
                                    if (linkNameList.size() < 1) {
                                        TextView noLinksLabel = findViewById(R.id.noLinksSavedLabel);
                                        noLinksLabel.setVisibility(View.INVISIBLE);
                                    }

                                    linkNameList.add(new String[]{name, url});

                                    Snackbar.make(parentView, name + " is added", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();


                                }


                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create an alert dialog
                AlertDialog inputDialog = alertDialogBuilder.create();

                inputDialog.show();

            }
        });
    }


}

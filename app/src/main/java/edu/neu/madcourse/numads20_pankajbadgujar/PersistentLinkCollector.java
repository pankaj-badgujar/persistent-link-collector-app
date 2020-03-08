package edu.neu.madcourse.numads20_pankajbadgujar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


public class PersistentLinkCollector extends AppCompatActivity {

    private LinkViewModel linkViewModel;
    private final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persistent_link_collector);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final LinkAdapter adapter = new LinkAdapter();
        recyclerView.setAdapter(adapter);

        linkViewModel = ViewModelProviders.of(this).get(LinkViewModel.class);
        linkViewModel.getAllLinks().observe(this, new Observer<List<Link>>() {
            @Override
            public void onChanged(List<Link> links) {
                adapter.setLinks(links);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Link link = adapter.getLinkAt(viewHolder.getAdapterPosition());
                linkViewModel.deleteLink(link);

                Toast.makeText(PersistentLinkCollector.this,"Link deleted", Toast.LENGTH_SHORT)
                        .show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new LinkAdapter.OnLinkItemClickListener() {
            @Override
            public void onLinkItemClick(Link link) {
                String URL = link.getLinkURL();
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

                                if (name.trim().isEmpty() || url.trim().isEmpty()) {
                                    Toast.makeText(context, "Name and URL is required! ", Toast.LENGTH_LONG).show();
                                } else {
//                                    if (linkNameList.size() < 1) {
//                                        TextView noLinksLabel = findViewById(R.id.noLinksSavedLabel);
//                                        noLinksLabel.setVisibility(View.INVISIBLE);
//                                    }

                                    Link link = new Link(name, url);
                                    linkViewModel.addLink(link);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.deleteAllLinks:
                linkViewModel.deleteAllLinks();
                Toast.makeText(PersistentLinkCollector.this, "All links deleted", Toast.LENGTH_SHORT)
                        .show();
                return true;
                default:return super.onOptionsItemSelected(item);
        }
    }
}

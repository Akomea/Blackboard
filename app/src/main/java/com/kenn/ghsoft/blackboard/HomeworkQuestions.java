package com.kenn.ghsoft.blackboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class HomeworkQuestions extends AppCompatActivity {

    //a list to store all the products
    private List<Question> productList;

    //the recyclerview
    private RecyclerView recyclerView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_homework, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            case R.id.list_id:
                Intent list_intent = new Intent(this, RecordingListActivity.class);
                startActivity(list_intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);


        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<>();


        //adding some items to our list
        productList.add(
                new Question(
                        1,
                        "The Strong Donkey",
                        "Questions will go here",
                        "Translate",
                        R.drawable.happykid2,
                        R.raw.flyaway));

        productList.add(
                new Question(
                        1,
                        "The Strong Donkey",
                        "Questions will go here",
                        "Translate",
                        R.drawable.happykid2,
                        R.raw.renjia));

        productList.add(
                new Question(
                        1,
                        "The Strong Donkey",
                        "Questions will go here",
                        "Translate",
                        R.drawable.happykid2,
                        R.raw.renjia));

        //creating recyclerview adapter
        QuestionAdapter adapter = new QuestionAdapter(this, productList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

    }


    public void onclickRecord(View tv) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Would you like to record an answer?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void onclickPlay(View btv) {
        int id = recyclerView.getId();
        String btn_id = "";

        int resourceId = recyclerView.getResources().getIdentifier(btn_id, "raw", "com.kenn.ghsoft.blackboard");
        MediaPlayer mediaPlayer = MediaPlayer.create(getBaseContext(), resourceId);
        mediaPlayer.start();

        btn_id = recyclerView.getResources().getResourceEntryName(id);

        Log.i(btn_id, "onclickPlay: true");
    }
}

package com.kenn.ghsoft.blackboard;

import android.content.Intent;
import android.os.Bundle;
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
    private List<Question> questionList;

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

            case R.id.home_id:
                Intent intentHome = new Intent(this, MainActivity.class);
                startActivity(intentHome);
                return true;

            case R.id.list_id:
                Intent intentList = new Intent(this, RecordingListActivity.class);
                intentList.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentList);
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

        //initializing the questionlist
        questionList = new ArrayList<>();


        //adding some items to our list
        questionList.add(
                new Question(
                        1,
                        "Question Title",
                        "Questions will go here",
                        "Translation will go here",
                        R.drawable.happykid2
                ));

        questionList.add(
                new Question(
                        1,
                        "Question Title",
                        "Questions will go here",
                        "Translation will go here",
                        R.drawable.happykid2
                ));

        questionList.add(
                new Question(
                        1,
                        "Question Title",
                        "Questions will go here",
                        "Translation will go here",
                        R.drawable.happykid2
                ));

        //creating recyclerview adapter
        QuestionAdapter adapter = new QuestionAdapter(this, questionList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

    }


    public void onclickPlay(View btv) {

        Log.i("Card Play button", "onclickPlay: true");
    }
}

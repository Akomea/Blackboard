package com.kenn.ghsoft.blackboard;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        TextView text1 = findViewById(R.id.text_view_id);
        TextView text2 = findViewById(R.id.text_view2_id);
        TextView text3 = findViewById(R.id.text_view3_id);
        TextView text4 = findViewById(R.id.text_view4_id);

        Typeface font = Typeface.createFromAsset(getAssets(), "childs_play.ttf");
        text1.setTypeface(font);
        text2.setTypeface(font);
        text3.setTypeface(font);
        text4.setTypeface(font);

        text1.setShadowLayer(30, 1.0f, 1.0f, Color.BLACK);
        text2.setShadowLayer(30, 1.0f, 1.0f, Color.BLACK);
        text3.setShadowLayer(30, 1.0f, 1.0f, Color.BLACK);
        text4.setShadowLayer(30, 1.0f, 1.0f, Color.BLACK);
    }


    //call when user clicks Current lessons
    public void onclickCurrent(View Textview) {
        Intent current_intent = new Intent(this, CurrentLessons.class);
        current_intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(current_intent);
    }

    //call when user clicks Preview lessons
    public void onclickPreview(View Textview) {
        Intent preview_intent = new Intent(this, PreviewLessons.class);
        preview_intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(preview_intent);
    }

    //call when user clicks Games
    public void onclickGames(View Textview) {
        Intent games_intent = new Intent(this, Games.class);
        games_intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(games_intent);
    }

    //call when user clicks Settings
    public void onclickSettings(View Textview) {
        Intent settings_intent = new Intent(this, SettingsActivity.class);
        settings_intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(settings_intent);
    }
}

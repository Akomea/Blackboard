package com.kenn.ghsoft.blackboard;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.github.clans.fab.FloatingActionMenu;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class RecordingListActivity extends AppCompatActivity {

    private MultiSelector mMultiSelector = new MultiSelector();
    private RecyclerView recyclerViewRecordings;
    private ArrayList<Recording> recordingArraylist;
    private RecordingAdapter recordingAdapter;
    private TextView textViewNoRecordings;

    private FloatingActionMenu menuLabelsRight;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_list);

        menuLabelsRight = findViewById(R.id.fab_menu);
        menuLabelsRight.setClosedOnTouchOutside(true);

        recordingArraylist = new ArrayList<>();
        initViews();
        fetchRecordings();

        if (recordingArraylist.size() == 0) {
            menuLabelsRight.setVisibility(View.INVISIBLE);
        }
    }

    private void fetchRecordings() {

        File root = Environment.getExternalStorageDirectory();
        String path = root.getAbsolutePath() + "/Blackboard/Audios";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: " + files.length);
        if (files.length != 0) {

            for (int i = 0; i < files.length; i++) {

                Log.d("Files", "FileName:" + files[i].getName());
                String fileName = files[i].getName();
                String recordingUri = root.getAbsolutePath() + "/Blackboard/Audios/" + fileName;

                Recording recording = new Recording(recordingUri, fileName, false, i);
                recordingArraylist.add(recording);
            }
            textViewNoRecordings.setVisibility(View.GONE);
            recyclerViewRecordings.setVisibility(View.VISIBLE);
            setAdaptertoRecyclerView();

        } else if (files.length == 0) {
            showNoRecordingsText();
        }
    }

    public void showNoRecordingsText() {
        TextView textView1 = findViewById(R.id.textViewNoRecordings);
        textView1.setVisibility(View.VISIBLE);

        TextView textView2 = findViewById(R.id.no_rec_msg1);
        textView2.setVisibility(View.VISIBLE);

        TextView textView3 = findViewById(R.id.no_rec_msg2);
        textView3.setVisibility(View.VISIBLE);

        ImageView imageView = findViewById(R.id.no_rec_image);
        imageView.setVisibility(View.VISIBLE);
        recyclerViewRecordings.setVisibility(View.GONE);
    }

    private void setAdaptertoRecyclerView() {
        recordingAdapter = new RecordingAdapter(this, recordingArraylist);
        recyclerViewRecordings.setAdapter(recordingAdapter);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initViews() {

        /* enabling back button ***/
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recyclerViewRecordings = findViewById(R.id.recyclerViewRecordings);
        recyclerViewRecordings.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewRecordings.setHasFixedSize(true);
        textViewNoRecordings = findViewById(R.id.textViewNoRecordings);


    }

    public void onClickDeleteAll(View view) {
        File root = Environment.getExternalStorageDirectory();
        String path = root.getAbsolutePath() + "/Blackboard/Audios";
        File directory = new File(path);
        File[] files = directory.listFiles();

        for (int i = recordingArraylist.size() - 1; i >= 0; i--) {
            Recording recording = recordingArraylist.get(i);
            recordingAdapter.deleteItem(recording);
            files[i].delete();
            if (i == 0) {
                (this).showNoRecordingsText();
                menuLabelsRight.close(true);
            }
        }

    }

    public void onClickSubmitAll(View view) {
        File root = Environment.getExternalStorageDirectory();
        String path = root.getAbsolutePath() + "/Blackboard/Audios";
        File directory = new File(path);
        File[] files = directory.listFiles();
        ArrayList<Uri> audios = new ArrayList<>();

        for (int i = recordingArraylist.size() - 1; i >= 0; i--) {
            String filePath = files[i].getPath();
            audios.add(FileProvider.getUriForFile(this, "com.kenn.ghsoft.blackboard.fileprovider", new File(filePath)));
        }
        recordingAdapter.shareRecordings(audios);
        menuLabelsRight.close(true);
    }
}

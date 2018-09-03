package com.kenn.ghsoft.blackboard;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecordingListActivity extends AppCompatActivity {

    private MultiSelector mMultiSelector = new MultiSelector();
    private RecyclerView recyclerViewRecordings;
    private ArrayList<Recording> recordingArraylist;
    private RecordingAdapter recordingAdapter;
    private TextView textViewNoRecordings;
    private List<Integer> selectedIds = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_list);
        recordingArraylist = new ArrayList<>();
        initViews();
        fetchRecordings();
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

//        recyclerViewRecordings.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerViewRecordings, new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                if (isMultiselect) {
//                    //if multiple selection is enabled then select item on single click else perform normal click on item.
//                    multiSelect(position);
//                }
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//                if (!isMultiselect) {
//                    selectedIds = new ArrayList<>();
//                    isMultiselect = true;
//
//                    if (actionMode == null) {
//                        actionMode = startActionMode(RecordingListActivity.this); //show ActionMode.
//                    }
//                }
//
//                multiSelect(position);
//            }
//        }));
//    }

//    private void multiSelect(int position) {
//        Recording data = recordingAdapter.getItem(position);
//        if (data != null) {
//            if (actionMode != null) {
//                if (selectedIds.contains(data.getId()))
//                    selectedIds.remove(Integer.valueOf(data.getId()));
//                else
//                    selectedIds.add(data.getId());
//
//                if (selectedIds.size() > 0)
//                    actionMode.setTitle(String.valueOf(selectedIds.size())); //show selected item count on action mode.
//                else {
//                    actionMode.setTitle(""); //remove item count from action mode.
//                    actionMode.finish(); //hide action mode.
//                }
//                recordingAdapter.setSelectedIds(selectedIds);
//            }
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.list_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//
//            case R.id.settings:
//                Intent intent = new Intent(this, SettingsActivity.class);
//                startActivity(intent);
//                return true;
//
//            case R.id.list_search_id:
//                Log.i("Search", "Button clicked");
//                return true;
//
//            case android.R.id.home:
//                this.finish();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    public ArrayList<Recording> getRecordingArraylist() {
//        return recordingArraylist;
//    }
//
//
//    @Override
//    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
//        MenuInflater inflater = actionMode.getMenuInflater();
//        inflater.inflate(R.menu.ctx_action_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
//        return false;
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    @Override
//    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
//        switch (menuItem.getItemId()) {
//            case R.id.action_delete:
//                // Need to finish the action mode before doing the following,
//                // not after. No idea why, but it crashes.
//                actionMode.finish();
//                List<Integer> toBeRemovedItemPositions = selectedIds;
//
//                for (int i = 0; i < selectedIds.size(); i++) {
//                    Log.d("IDS", "onActionItemClicked: " + selectedIds.get(i));
//                }
//
//                int currPos;
//
//                for (int i = recordingArraylist.size() - 1; i >= 0; i--) {
//                    Recording data = recordingArraylist.get(i);
//                    if (mMultiSelector.isSelected(i, 0)) {
//
//
//                        File root = Environment.getExternalStorageDirectory();
//                        String path = root.getAbsolutePath() + "/Blackboard/Audios";
//                        File directory = new File(path);
//                        File[] files = directory.listFiles();
//                        String filename = files[i].getName();
//                        int item = recordingAdapter.getItem(i).getId();
//
//                        recordingAdapter.deleteItem(item);
//                        files[item].delete();
//                        actionMode.finish();
//                        mMultiSelector.clearSelections();
//
//                        //implement showNoRecordingsText(); if arrayList is empty
//                    }
//
////                    Log.d("DELETED FILE", "FileName:" + files[item].getName());
////                    Log.d("DATA ID: ", "Recording ID:" + data.getId());
////                    Log.d("Files", "Size: " + files.length);
//                }
//                return true;
//            case R.id.action_share:
//
//                File root = Environment.getExternalStorageDirectory();
//                String path = root.getAbsolutePath() + "/Blackboard/Audios";
//                Log.d("Files", "Path: " + path);
//                File directory = new File(path);
//                File[] files = directory.listFiles();
//                Log.d("Files", "Size: " + files.length);
//
//                for (int i = 0; i < files.length; i++) {
//
//                    Log.d("Files", "FileName:" + files[i].getName());
//                    String fileName = files[i].getName();
//                    String recordingUri = root.getAbsolutePath() + "/Blackboard/Audios/" + fileName;
//
//                    String sharePath = recordingUri;
//                    Uri uri = Uri.parse(sharePath);
//                    Intent share = new Intent(Intent.ACTION_SEND);
//                    share.setType("audio/*");
//                    share.putExtra(Intent.EXTRA_STREAM, uri);
//                    startActivity(Intent.createChooser(share, "Share Sound File"));
//                }
//
//                return true;
//
//        }
//        return false;
//    }
//
//    @Override
//    public void onDestroyActionMode(ActionMode mode) {
//        isMultiselect = false;
//        selectedIds = new ArrayList<>();
//        recordingAdapter.setSelectedIds(new ArrayList<Integer>());
//        actionMode = null;
//    }
//
//    static class UpdateRecordingsTask extends AsyncTask<Recording, Recording, RecordingAdapter> {
//
//        @Override
//        protected RecordingAdapter doInBackground(Recording... recordings) {
//            return null;
//        }
//    }


    }
}

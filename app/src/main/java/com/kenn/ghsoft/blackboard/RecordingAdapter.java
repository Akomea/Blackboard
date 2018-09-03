package com.kenn.ghsoft.blackboard;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RecordingAdapter extends RecyclerView.Adapter<RecordingAdapter.ViewHolder> {

    private Context context;
    Recording recording;
    private ArrayList<Recording> recordingArraylist;
    private ArrayList<Recording> recordingArrayList;
    private MultiSelector mMultiSelector = new MultiSelector();
    private RecordingAdapter recordingAdapter;
    private RecyclerView recyclerViewRecordings;
    private MediaPlayer mPlayer;
    private boolean isPlaying = false;
    private int last_index = -1;
    private ModalMultiSelectorCallback mDeleteMode = new ModalMultiSelectorCallback(mMultiSelector) {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            super.onCreateActionMode(actionMode, menu);
            AppCompatActivity.class.cast(context).getMenuInflater().inflate(R.menu.ctx_action_menu, menu);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.action_delete:
                    // Need to finish the action mode before doing the following,
                    // not after. No idea why, but it crashes.
                    actionMode.finish();

                    for (int i = recordingArrayList.size(); i >= 0; i--) {
                        if (mMultiSelector.isSelected(i, getItemId(i))) {
                            Recording recording = recordingArrayList.get(i);
                            deleteItem(recording);

                            actionMode.finish();

                            File root = Environment.getExternalStorageDirectory();
                            String path = root.getAbsolutePath() + "/Blackboard/Audios";
                            File directory = new File(path);
                            File[] files = directory.listFiles();
                            files[i].delete();
                            if (files.length == 1) {
                                ((RecordingListActivity) context).showNoRecordingsText();
                            }
                        }
                    }
                    mMultiSelector.clearSelections();
                    return true;

                case R.id.action_share:
                    actionMode.finish();

                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Blackboard/Audios/";
                    Log.d("Files", "Path: " + path);
                    File directory = new File(path);
                    File[] files = directory.listFiles();
                    Log.d("Files", "Size: " + files.length);
                    ArrayList<Uri> audios = new ArrayList<>();

                    for (int i = 0; i < mMultiSelector.getSelectedPositions().size(); i++) {

                        int selectedRecording = mMultiSelector.getSelectedPositions().get(i);

                        String filePath = files[selectedRecording].getPath();

                        audios.add(FileProvider.getUriForFile(context, "com.kenn.ghsoft.blackboard.fileprovider", new File(filePath)));
                    }
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Student's assignments.");
                    shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, audios);
                    shareIntent.setType("audio/*");
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    context.startActivity(Intent.createChooser(shareIntent, "Submit homework to teacher..."));
                    mMultiSelector.clearSelections();
                    return true;
            }
            return false;
        }
    };

    public RecordingAdapter(Context context, ArrayList<Recording> recordingArrayList) {
        this.context = context;
        this.recordingArrayList = recordingArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recording_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setUpData(holder, position);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setUpData(ViewHolder holder, int position) {

        Recording recording = recordingArrayList.get(position);
        holder.textViewName.setText(recording.getFileName());

        if (recording.isPlaying()) {
            holder.imageViewPlay.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
            TransitionManager.beginDelayedTransition((ViewGroup) holder.itemView);
            holder.seekBar.setVisibility(View.VISIBLE);
            holder.seekUpdation(holder);
        } else {
            holder.imageViewPlay.setImageResource(R.drawable.ic_play);
            TransitionManager.beginDelayedTransition((ViewGroup) holder.itemView);
            holder.seekBar.setVisibility(View.GONE);
        }
        holder.manageSeekBar(holder);
    }

    @Override
    public int getItemCount() {
        return recordingArrayList.size();
    }

    void deleteItem(Recording rec) {
        int pos = recordingArrayList.indexOf(rec);
        recordingArrayList.remove(rec);
        notifyDataSetChanged();
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, recordingArrayList.size());
    }

    public class ViewHolder extends SwappingHolder implements View.OnClickListener, View.OnLongClickListener {

        RelativeLayout rootView;
        ImageView imageViewPlay;
        CheckBox checkBox;
        SeekBar seekBar;
        TextView textViewName;
        ViewHolder holder;
        private String recordingUri;
        private int lastProgress = 0;
        private Handler mHandler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                seekUpdation(holder);
            }
        };

        public ViewHolder(View itemView) {
            super(itemView, mMultiSelector);

            imageViewPlay = itemView.findViewById(R.id.imageViewPlay);
            seekBar = itemView.findViewById(R.id.seekBar);
            textViewName = itemView.findViewById(R.id.textViewRecordingname);
            checkBox = itemView.findViewById(R.id.checkBox);
            rootView = itemView.findViewById(R.id.rootView);

            itemView.setOnClickListener(this);
            itemView.setLongClickable(true);
            itemView.setOnLongClickListener(this);

            imageViewPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Recording recording = recordingArrayList.get(position);

                    recordingUri = recording.getUri();

                    if (isPlaying) {
                        stopPlaying();
                        if (position == last_index) {
                            recording.setPlaying(false);
                            stopPlaying();
                            notifyItemChanged(position);
                        } else {
                            markAllPaused();
                            recording.setPlaying(true);
                            notifyItemChanged(position);
                            startPlaying(recording, position);
                            last_index = position;
                        }

                    } else {
                        if (recording.isPlaying()) {
                            recording.setPlaying(false);
                            stopPlaying();
                            Log.d("isPlayin", "True");
                        } else {
                            startPlaying(recording, position);
                            recording.setPlaying(true);
                            seekBar.setMax(mPlayer.getDuration());
                            Log.d("isPlayin", "False");
                        }
                        notifyItemChanged(position);
                        last_index = position;
                    }
                }
            });
        }

        @Override
        public void onClick(View view) {
            if (isSelectable()) {
                //if multiple selection is enabled then select item on single click else perform normal click on item.
                mMultiSelector.tapSelection(getAdapterPosition(), getItemId());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            ((AppCompatActivity) context).startSupportActionMode(mDeleteMode);
            mMultiSelector.setSelected(this, true);
            return true;
        }


        private void manageSeekBar(ViewHolder holder) {
            holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (mPlayer != null && fromUser) {
                        mPlayer.seekTo(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }

        private void markAllPaused() {
            for (int i = 0; i < recordingArrayList.size(); i++) {
                recordingArrayList.get(i).setPlaying(false);
                recordingArrayList.set(i, recordingArrayList.get(i));
            }
            notifyDataSetChanged();
        }

        private void seekUpdation(ViewHolder holder) {
            this.holder = holder;
            if (mPlayer != null) {
                int mCurrentPosition = mPlayer.getCurrentPosition();
                holder.seekBar.setMax(mPlayer.getDuration());
                holder.seekBar.setProgress(mCurrentPosition);
                lastProgress = mCurrentPosition;
            }
            mHandler.postDelayed(runnable, 100);
        }

        private void stopPlaying() {
            try {
                mPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mPlayer = null;
            isPlaying = false;
        }

        private void startPlaying(final Recording audio, final int position) {
            mPlayer = new MediaPlayer();
            try {
                mPlayer.setDataSource(recordingUri);
                mPlayer.prepare();
                mPlayer.start();
            } catch (IOException e) {
                Log.e("LOG_TAG", "prepare() failed");
            }
            //showing the pause button
            seekBar.setMax(mPlayer.getDuration());
            isPlaying = true;

            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    audio.setPlaying(false);
                    notifyItemChanged(position);
                }
            });
        }

    }

}

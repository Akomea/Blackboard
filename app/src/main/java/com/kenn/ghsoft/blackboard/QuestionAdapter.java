package com.kenn.ghsoft.blackboard;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Question> questionList;

    //getting the context and product list with constructor
    QuestionAdapter(Context mCtx, List<Question> questionList) {
        this.mCtx = mCtx;
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.cardview, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        //getting the question of the specified position
        Question question = questionList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(question.getTitle());
        holder.textViewShortDesc.setText(question.getShortdesc());
        holder.textViewTranslation.setText(String.valueOf(question.getTranslation()));
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(question.getImage()));
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewTranslation;
        ImageView imageView;
        ImageButton imageButton;
        Button translateButton;

        QuestionViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewTranslation = itemView.findViewById(R.id.textViewTranslate);
            imageView = itemView.findViewById(R.id.imageView);
            imageButton = itemView.findViewById(R.id.recButton);
            translateButton = itemView.findViewById(R.id.translate_button);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    String title = textViewTitle.getText().toString();
                    Intent intentGoRecordActivityDialog = new Intent(mCtx, MainRecordingActivity.class);
                    intentGoRecordActivityDialog.putExtra("Position", position);
                    intentGoRecordActivityDialog.putExtra("Title", title);
                    intentGoRecordActivityDialog.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    mCtx.startActivity(intentGoRecordActivityDialog);

                }
            });
            translateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    translateButton.setVisibility(View.INVISIBLE);
                    textViewTranslation.setVisibility(View.VISIBLE);
                }
            });
            textViewTranslation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    translateButton.setVisibility(View.VISIBLE);
                    textViewTranslation.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
}
package com.example.musicme.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.musicme.R;
import com.example.musicme.objects.QuestionAttributes;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    TextView titleText;
    DisplayMetrics displayMetrics;
    ImageView playButton;
    private MediaPlayer mediaPlayer;


    View answer1Box, answer2Box, answer3Box, answer4Box;
    TextView answer1Text, answer2Text, answer3Text, answer4Text, postQuizText;
    ImageView answer1Image, answer2Image, answer3Image, answer4Image;
    View postQuizView;
    Button backMenuButton;
    private int qIndex = 0;
    private int correctAns = 0;
    private List<QuestionAttributes> questionAttributes = new ArrayList<>();
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        titleText = findViewById(R.id.quiz_title);
        playButton = findViewById(R.id.play_button);
        answer1Box = findViewById(R.id.answer1);
        answer2Box = findViewById(R.id.answer2);
        answer3Box = findViewById(R.id.answer3);
        answer4Box = findViewById(R.id.answer4);

        postQuizView = findViewById(R.id.post_question_view);
        backMenuButton = findViewById(R.id.back_menu_button);
        postQuizText = findViewById(R.id.post_quiz_text);

        intent = new Intent(this, MainActivity.class);


        answer1Text = answer1Box.findViewById(R.id.answer_text);
        answer2Text = answer2Box.findViewById(R.id.answer_text);
        answer3Text = answer3Box.findViewById(R.id.answer_text);
        answer4Text = answer4Box.findViewById(R.id.answer_text);

        answer1Image = answer1Box.findViewById(R.id.answer_image);
        answer2Image = answer2Box.findViewById(R.id.answer_image);
        answer3Image = answer3Box.findViewById(R.id.answer_image);
        answer4Image = answer4Box.findViewById(R.id.answer_image);

        displayMetrics = new DisplayMetrics();



        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


        setLayout();

        setOnClickListeners();

        backMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });



    }


    private void playSound() {
        mediaPlayer.start();
    }

    private void setLayout(){
        titleText.setHeight(displayMetrics.heightPixels/13);
        titleText.setTextSize(convertToDp(displayMetrics.heightPixels/20, displayMetrics.density));
        playButton.getLayoutParams().height = (int) (displayMetrics.heightPixels/5.2);
        playButton.getLayoutParams().width = (int) (displayMetrics.heightPixels/5.2);
        int metrics =  (int) (displayMetrics.widthPixels * 0.4);

        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(metrics/5, metrics/5, metrics/5, metrics/5);
        answer1Box.setLayoutParams(layoutParams);
        answer2Box.setLayoutParams(layoutParams);
        answer3Box.setLayoutParams(layoutParams);
        answer4Box.setLayoutParams(layoutParams);


        setMetrics(metrics);

        setTextSize(metrics/20);

        setImages((int) (metrics * 0.66));

        setText();
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, Uri.parse("https://musicmestorage.blob.core.windows.net/audioclips/Alone.mp3"));

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
            }
        });

    }

    private void setMetrics(int metrics) {
        answer1Box.getLayoutParams().width = metrics;
        answer1Box.getLayoutParams().height = metrics;
        answer2Box.getLayoutParams().width = metrics;
        answer2Box.getLayoutParams().height = metrics;
        answer3Box.getLayoutParams().width = metrics;
        answer3Box.getLayoutParams().height = metrics;
        answer4Box.getLayoutParams().width = metrics;
        answer4Box.getLayoutParams().height = metrics;
    }

    private void setTextSize(int textMetrics){
        answer1Text.setTextSize(textMetrics);
        answer2Text.setTextSize(textMetrics);
        answer3Text.setTextSize(textMetrics);
        answer4Text.setTextSize(textMetrics);
    }

    private void setImages(int imageMetrics) {
        answer1Image.getLayoutParams().height = imageMetrics;
        answer1Image.getLayoutParams().width = imageMetrics;
        answer2Image.getLayoutParams().height = imageMetrics;
        answer2Image.getLayoutParams().width = imageMetrics;
        answer3Image.getLayoutParams().height = imageMetrics;
        answer3Image.getLayoutParams().width = imageMetrics;
        answer4Image.getLayoutParams().height = imageMetrics;
        answer4Image.getLayoutParams().width = imageMetrics;
    }


    private void setText() {
        answer1Text.setText(questionAttributes.get(qIndex).getQuestionNameHolder().getName1());
        answer2Text.setText(questionAttributes.get(qIndex).getQuestionNameHolder().getName2());
        answer3Text.setText(questionAttributes.get(qIndex).getQuestionNameHolder().getName3());
        answer4Text.setText(questionAttributes.get(qIndex).getQuestionNameHolder().getName4());
    }

    private void setOnClickListeners(){
        answer1Box.setOnClickListener(this);
        answer2Box.setOnClickListener(this);
        answer3Box.setOnClickListener(this);
        answer4Box.setOnClickListener(this);
    }

    private int convertToDp(int metric, float density){
        return (int) (metric / density);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.answer1:
                answerClicked(answer1Box, 1);
                break;
            case R.id.answer2:
                answerClicked(answer2Box, 2);
                break;
            case R.id.answer3:
                answerClicked(answer3Box, 3);
                break;
            case R.id.answer4:
                answerClicked(answer4Box, 4);
                break;
        }
    }

    private void answerClicked(final View view, int index){
       final MediaPlayer mediaPlayer = MediaPlayer.create(this, Uri.parse("") );
        if(questionAttributes.get(this.qIndex).getCorrectIndex() == index) {
            view.setBackgroundResource(R.drawable.answer_correct);
            this.correctAns++;
        } else {
            view.setBackgroundResource(R.drawable.answer_wrong);
        }

        new CountDownTimer(1500, 500) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (qIndex < questionAttributes.size() -1) {
                    ++qIndex;

                    view.setBackgroundResource(R.drawable.rounded_rectangle);
                    setLayout();

                    mediaPlayer.start();
                } else {
                    postQuizView.setVisibility(View.VISIBLE);
                    postQuizText.setText("You have answered " + correctAns + " correctly");
                }

            }
        }.start();


    }



}
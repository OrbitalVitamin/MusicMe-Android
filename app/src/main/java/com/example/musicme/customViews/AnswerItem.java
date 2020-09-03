package com.example.musicme.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.musicme.R;
import com.example.musicme.enums.AnswerState;

public class AnswerItem extends ConstraintLayout implements View.OnClickListener {

    public static final int[] STATE_CORRECT = {R.attr.state_correct};
    public static final int[] STATE_WRONG = {R.attr.state_wrong};




    private boolean correct;
    private String genreName;
    private TextView textView;
    private AnswerState answerState;
    private  View view;



    public AnswerItem(Context context) {
        super(context);

    }

    public AnswerItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setOnClickListener(this);
        LayoutInflater inflater = LayoutInflater.from(context);

        this.view = inflater.inflate(R.layout.answer_item, null);
        this.view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.textView = this.view.findViewById(R.id.answer_text);
        this.answerState = AnswerState.DEFAULT;
        addView(this.view);
    }

    public AnswerItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    public void setAnswerState (AnswerState answerState) {
        this.answerState = answerState;
        refreshDrawableState();

    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public void setText(String text){
        this.textView = this.view.findViewById(R.id.answer_text);
        this.textView.setText(text);
    }




    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 2);
        if(answerState == AnswerState.CORRECT) {
            mergeDrawableStates(drawableState, STATE_CORRECT);
        } else if (answerState == AnswerState.WRONG){
            mergeDrawableStates(drawableState, STATE_WRONG);
        } else {
            mergeDrawableStates(drawableState, STATE_WRONG);
            mergeDrawableStates(drawableState, STATE_CORRECT);
        }

        return  drawableState;
    }




    @Override
    public void onClick(View v) {
        if(correct){
            setAnswerState(AnswerState.CORRECT);
        } else {
            setAnswerState(AnswerState.WRONG);
        }

        refreshDrawableState();
    }
}
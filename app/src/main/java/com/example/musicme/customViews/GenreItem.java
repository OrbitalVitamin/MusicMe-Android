package com.example.musicme.customViews;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.musicme.R;
import com.example.musicme.database.SqlPlayerData;
import com.example.musicme.enums.GenreItemState;

public class GenreItem extends ConstraintLayout {
    private View view;
    private String genre, type;
    private ImageView genreImage, lockImage;
    private TextView textView;
    private GenreItemState genreItemState;


    public GenreItem(Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        this.view = inflater.inflate(R.layout.genre_item, this);
        this.view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(this.view);

    }

    public GenreItem(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    public GenreItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = LayoutInflater.from(context);
        this.view = inflater.inflate(R.layout.genre_item, this);
        this.view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(this.view);
        init();

    }

    private void init(){
        this.textView = view.findViewById(R.id.genre_name);
        this.genreImage = view.findViewById(R.id.genre_image);
        this.lockImage = view.findViewById(R.id.circle);
        this.setState();
    }

    public void setGenre(String genre) {
        this.genre = genre;
        this.textView = view.findViewById(R.id.genre_name);
        this.textView.setText(genre);
    }

    private void setState(){
       if(SqlPlayerData.genreLevelExists(this.type, this.genre)){
            this.genreItemState = GenreItemState.UNCLOCKED;
            this.lockImage.setVisibility(View.GONE);
        } else {
            this.genreItemState = GenreItemState.LOCKED;
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            this.genreImage.setColorFilter(new ColorMatrixColorFilter(matrix));
        }
    }

    public GenreItemState getGenreItemState(){
        return this.genreItemState;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }








}

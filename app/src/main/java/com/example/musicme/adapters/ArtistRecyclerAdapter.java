package com.example.musicme.adapters;


import android.app.Activity;
import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicme.R;
import com.example.musicme.database.SqlPlayerData;

import com.example.musicme.enums.GameState;
import com.example.musicme.enums.GenreItemState;

import java.util.List;

public class ArtistRecyclerAdapter extends RecyclerView.Adapter<ArtistRecyclerAdapter.MyViewHolder> {
    Context context;
    List<String> genres;
    int metrics;
    Activity activity;
    GameState type;

    public ArtistRecyclerAdapter(Context context, List<String> genres, int metrics, Activity activity, GameState type) {
        this.context = context;
        this.genres = genres;
        this.metrics = metrics;
        this.activity = activity;
        this.type = type;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView genreName, levelText;
        ImageView genreImage, lockImage;
        SqlPlayerData data;
        private GenreItemState genreItemState;
        String genre;
        View view;
        boolean locked;

        public MyViewHolder(@NonNull View view) {
            super(view);
            this.genreName = view.findViewById(R.id.genre_name);
            this.genreImage = view.findViewById(R.id.genre_image);
            this.lockImage = view.findViewById(R.id.circle);
            this.data = new SqlPlayerData(context, false);
            this.view = view;


        }

        public void init() {
            genreName.setText(this.genre);
            setState();

        }

        public void setState() {

            if (data.genreLevelExists(type.getValue(), this.genre)) {
                this.genreItemState = GenreItemState.UNCLOCKED;
                this.lockImage.setVisibility(View.GONE);
                this.locked = false;
            } else {
                this.genreItemState = GenreItemState.LOCKED;
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);
                this.genreImage.setColorFilter(new ColorMatrixColorFilter(matrix));
                this.locked = true;
            }
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
            init();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Drawable drawable = parent.getContext().getDrawable(R.drawable.rounded_rectangle);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_genre_item, parent, false);
        view.setBackground(drawable);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams((int) (this.metrics), (int) (this.metrics * 1.3));
        holder.setGenre(genres.get(position));
        holder.view.setLayoutParams(layoutParams);


    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

}



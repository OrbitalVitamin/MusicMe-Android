package com.example.musicme.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicme.R;
import com.example.musicme.database.SqlPlayerData;
import com.example.musicme.enums.GameState;
import com.example.musicme.enums.GenreItemState;
import com.example.musicme.marathon.SubMenuFragment;

import java.util.List;

public class LevelRecyclerAdapter extends RecyclerView.Adapter<LevelRecyclerAdapter.MyViewHolder> {
    Context context;
    List<String> genres;
    GameState gameState;
    int metrics;
    FragmentActivity activity;

    public LevelRecyclerAdapter(Context context, GameState gameState, List<String> genres, int metrics, FragmentActivity activity){
        this.context = context;
        this.gameState = gameState;
        this.genres = genres;
        this.metrics = metrics;
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView genreName;
        ImageView genreImage, lockImage;
        SqlPlayerData data;
        private GenreItemState genreItemState;
        String genre;
        boolean locked;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            this.genreName = view.findViewById(R.id.genre_name);
            this.genreImage = view.findViewById(R.id.genre_image);
            this.lockImage = view.findViewById(R.id.circle);
            this.data = new SqlPlayerData(context, true);

        }

       public void setState(){
            if(data.genreLevelExists(gameState.getValue(), this.genre)){
                this.genreItemState = GenreItemState.UNCLOCKED;
                this.lockImage.setVisibility(View.GONE);
                this.locked = false;
            } else if(gameState == GameState.MAIN && !(data.genreLevelExists(gameState.getValue(), this.genre))) {
                data.createLevel(this.genre);
                this.genreItemState = GenreItemState.UNCLOCKED;
            }else {
                this.genreItemState = GenreItemState.LOCKED;
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);
                this.genreImage.setColorFilter(new ColorMatrixColorFilter(matrix));
                this.locked = true;

            }
        }

        public void init(){
            genreName.setText(this.genre);
            setState();
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(locked){
                    } else if (!locked){
                        if (gameState == GameState.MAIN) {
                            Fragment fragment = new SubMenuFragment(genre, GameState.MAIN);
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.campaign_layout, fragment).commit();
                        } else {
                            Fragment fragment = new SubMenuFragment(genre, GameState.MAIN);
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.campaign_layout, fragment).commit();
                        }

                    }
                }
            });
        }

        public String getGenre() {
            return genre;

        }

        public void setGenre(String genre) {
            this.genre = genre;
            init();
        }
    }
    @NonNull
    @Override
    public LevelRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_genre_item, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull LevelRecyclerAdapter.MyViewHolder holder, int position) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams((int) (this.metrics), (int) (this.metrics*1.3));
        holder.setGenre(genres.get(position));
        holder.view.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }
}

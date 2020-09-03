package com.example.musicme.marathon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicme.R;

public class MarathonLifeRecyclerAdapter extends  RecyclerView.Adapter<MarathonLifeRecyclerAdapter.MyViewHolder>{
    int num;
    int height;
    int width;

    public MarathonLifeRecyclerAdapter(int num, int height, int width){
        this.num = num;
        this.height = height;
        this.width = width;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marathon_life, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.view.setLayoutParams(new ViewGroup.LayoutParams(this.width/this.num,(int) (this.height * 0.9)));
    }

    @Override
    public int getItemCount() {
        return this.num;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        View view;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            this.view.getLayoutParams().width = this.view.getHeight();

        }
    }
}

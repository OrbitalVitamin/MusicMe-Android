package com.example.musicme.objects;

import android.app.Application;

public class LevelStore {
    private int level;
    private int stage;
    private int stars;

    public LevelStore(int level, int stage, int stars) {
        this.level = level;
        this.stage = stage;
        this.stars = stars;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}

package com.example.musicme.objects;

import android.app.Application;

public class UserStore extends Application {
    private String username;
    private int diamonds;
    private int level;
    private int coins;
    private int marathonHighScore;
    private int sprintHighScore;
    private int lives;

    public UserStore(){}

    public UserStore(String username, int diamonds, int level, int coins, int marathonHighScore, int sprintHighScore, int lives) {
        this.username = username;
        this.diamonds = diamonds;
        this.level = level;
        this.coins = coins;
        this.marathonHighScore = marathonHighScore;
        this.sprintHighScore = sprintHighScore;
        this.lives = lives;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getDiamonds() {
        return diamonds;
    }

    public void setDiamonds(int diamonds) {
        this.diamonds = diamonds;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getMarathonHighScore() {
        return marathonHighScore;
    }

    public void setMarathonHighScore(int marathonHighScore) {
        this.marathonHighScore = marathonHighScore;
    }

    public int getSprintHighScore() {
        return sprintHighScore;
    }

    public void setSprintHighScore(int sprintHighScore) {
        this.sprintHighScore = sprintHighScore;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}

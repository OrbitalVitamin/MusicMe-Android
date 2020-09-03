package com.example.musicme.enums;

public enum GameState {
    MARATHON("Marathon"),
    SPRINT("Sprint"),
    COMPETITIVE("Competitive"),
    MAIN("Level"),
    ARTIST("Artist"),
    SUBMAIN("Level");

    public final String value;

    GameState(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}

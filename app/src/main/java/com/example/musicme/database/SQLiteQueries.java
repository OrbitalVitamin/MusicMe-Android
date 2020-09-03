package com.example.musicme.database;

public class SQLiteQueries {

    public static final String CREATE_TABLE_PLAYER = "CREATE TABLE Player (id TEXT PRIMARY KEY , username TEXT, sprintHighScore INT, nextLevelXp INT," +
            "marathonHighScore INT, lives INT, level INT, experience INT, diamonds INT, coins INT )";
    public static final String CREATE_LEVEL_TABLE = "CREATE TABLE Level (id TEXT PRIMARY KEY, totalCorrect INT, stars INT, stage INT, level INT, genre TEXT)";
    public static final String CREATE_MARATHON_TABLE = "CREATE TABLE Marathon (id TEXT PRIMARY KEY, score INT, genre TEXT)";
    public static final String CREATE_TIME_TABLE = "CREATE TABLE TIME (id TEXT PRIMARY KEY, timeRemaining INT, lastClosed INTEGER)";
    public static final String CREATE_ARTIST_TABLE = "CREATE TABLE Artist (id TEXT PRIMARY KEY, stars INT, genre TEXT)";
    public static final String CREATE_SPRINT_TABLE = "CREATE TABLE Sprint (id TEXT PRIMARY KEY, score INT, genre TEXT)";
    public static final String SELECT_TIME= "SELECT * FROM Time";

}

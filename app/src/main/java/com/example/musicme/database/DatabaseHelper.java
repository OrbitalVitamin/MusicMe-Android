package com.example.musicme.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MusicMeDatabase";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLiteQueries.CREATE_TABLE_PLAYER);
        db.execSQL(SQLiteQueries.CREATE_LEVEL_TABLE);
        db.execSQL(SQLiteQueries.CREATE_SPRINT_TABLE);
        db.execSQL(SQLiteQueries.CREATE_MARATHON_TABLE);
        db.execSQL(SQLiteQueries.CREATE_ARTIST_TABLE);
        db.execSQL(SQLiteQueries.CREATE_TIME_TABLE);

        ContentValues values = new ContentValues();
        values.put("username", "player");
        values.put("sprintHighScore", 0);
        values.put("nextLevelXp", 0);
        values.put("marathonHighScore", 0);
        values.put("lives", 5);
        values.put("experience", 0);
        values.put("diamonds", 0);
        values.put("coins", 0);

        db.insert("Player", null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createPlayer(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put("username", "player");
        values.put("sprintHighScore", 0);
        values.put("nextLevelXp", 0);
        values.put("marathonHighScore", 0);
        values.put("lives", 5);
        values.put("experience", 0);
        values.put("diamonds", 0);
        values.put("coins", 0);
        db.insert("Player", null, values);
    }
}

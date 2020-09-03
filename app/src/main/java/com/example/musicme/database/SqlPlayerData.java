package com.example.musicme.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.musicme.objects.LevelStore;
import com.example.musicme.objects.UserStore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SqlPlayerData {
    private Context context;
    private static SQLiteDatabase db;
    private DatabaseHelper databaseHelper;

    public SqlPlayerData(Context context, boolean write) {
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context);
        if(write == true) {
            db = databaseHelper.getWritableDatabase();
        }  else {
             db = databaseHelper.getReadableDatabase();
        }
    }
    public void addPlayerCoins(int gainedCoins){
        String sql = "SELECT * FROM Player WHERE username =?";
        Cursor cursor  = db.rawQuery(sql, new String[]{"player"});
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex("coins");
        int coins = cursor.getInt(columnIndex);
        ContentValues values = new ContentValues();
        values.put("coins", coins+gainedCoins);
        db.update("Player", values, "username = ?", new String[]{"player"});
    }

    public boolean usePlayerCoins(int coins){
        String sql = "SELECT * FROM Player WHERE username =?";
        Cursor cursor  = db.rawQuery(sql, new String[]{"player"});
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex("coins");
        int currentCoins = cursor.getInt(columnIndex);
        if (currentCoins >= coins){
            ContentValues values = new ContentValues();
            values.put("coins", currentCoins-coins);
            db.update("Player", values, "username = ?", new String[]{"player"});

            return true;
        } else {
            return false;
        }

    }

    public boolean updateScore(int score, String type) {
        String sql = "SELECT * FROM Player WHERE username =?";
        Cursor cursor  = db.rawQuery(sql, new String[]{"player"});
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(type);
        int currentScore = cursor.getInt(columnIndex);
        if(score > currentScore){
            ContentValues values = new ContentValues();
            values.put(type, score);
            db.update("Player", values, "username = ?", new String[]{"player"});
            return true;
        } else {
            return false;
        }
    }

    public boolean useLife(){
        String sql = "SELECT * FROM Player WHERE username =?";
        Cursor cursor  = db.rawQuery(sql, new String[]{"player"});
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex("lives");
        int currentLives = cursor.getInt(columnIndex);
        if(currentLives > 0){
            ContentValues values = new ContentValues();
            values.put("lives", currentLives-1);
            db.update("Player", values, "username = ?", new String[]{"player"});
            return true;
        } else {
            return false;
        }
    }

    public boolean genreLevelExists(String type, String genre){
        String sql = "SELECT * FROM "+type+"  WHERE genre = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{genre});
        if(cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void loadUser(UserStore userStore){
        String sql = "SELECT * FROM Player WHERE username =?";
        Cursor cursor  = db.rawQuery(sql, new String[]{"player"});

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            int nameIndex = cursor.getColumnIndex("username");
            int levelIndex = cursor.getColumnIndex("level");
            int sprintIndex = cursor.getColumnIndex("sprintHighScore");
            int marathonIndex = cursor.getColumnIndex("marathonHighScore");
            int coinIndex = cursor.getColumnIndex("coins");
            int diamondIndex = cursor.getColumnIndex("diamonds");
            int liveIndex = cursor.getColumnIndex("lives");
            userStore.setUsername(cursor.getString(nameIndex));
            userStore.setLevel(cursor.getInt(levelIndex));
            userStore.setSprintHighScore(cursor.getInt(sprintIndex));
            userStore.setMarathonHighScore(cursor.getInt(marathonIndex));
            userStore.setCoins(cursor.getInt(coinIndex));
            userStore.setDiamonds(cursor.getInt(diamondIndex));
            userStore.setLives(cursor.getInt(liveIndex));
        } else {

        }
    }



    public int getGenreScore(String type){
        String sql = "SELECT * FROM "+type+"  WHERE genre = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{type});
        int scoreIndex = cursor.getColumnIndex("score");
        return cursor.getInt(scoreIndex);
    }

    public void buyGenre(String type, String genre){
        ContentValues values = new ContentValues();
        values.put("genre", genre);

        db.insert(type, null, values);

    }

    public void createLevel(String genre){
        ContentValues values = new ContentValues();
        values.put("genre", genre);
        values.put("stars", 0);
        values.put("level", 1);
        values.put("stage", 1);
        values.put("totalCorrect", 0);

        db.insert("Level", null, values);

    }

    public void updateLevel(String genre, int stars, int level, int stage, int totalCorrect){
        ContentValues values = new ContentValues();
        values.put("stars", stars);
        values.put("level", level);
        values.put("stage", stage);
        values.put("totalCorrect", totalCorrect);


        db.update("Level", values,  "genre = ?", new String[]{genre});
    }

    public void loadLevel(LevelStore levelStore, String genre){
        String sql = "SELECT * FROM Level WHERE genre =?";
        Cursor cursor  = db.rawQuery(sql, new String[]{genre});
        cursor.moveToFirst();

        int levelIndex = cursor.getColumnIndex("level");
        int stageIndex = cursor.getColumnIndex("stage");
        int starsIndex = cursor.getColumnIndex("stars");

        levelStore.setLevel(cursor.getInt(levelIndex));
        levelStore.setStage(cursor.getInt(stageIndex));
        levelStore.setStars(cursor.getInt(starsIndex));
    }

    public int getRemainingTime(){
        Cursor cursor = db.rawQuery(SQLiteQueries.SELECT_TIME, new String[]{});
        cursor.moveToFirst();
        int timeRemainingIndex = cursor.getColumnIndex("timeRemaining");
        return cursor.getInt(timeRemainingIndex);
    }

    public static String getLastTimeClosed(){
        Cursor cursor = db.rawQuery(SQLiteQueries.SELECT_TIME, new String[]{});
        int lastClosedIndex = cursor.getColumnIndex("lastClosed");
        return cursor.getString(lastClosedIndex);
    }

    public void saveTime(int timeRemaining){
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        Cursor cursor = db.rawQuery(SQLiteQueries.SELECT_TIME, new String[]{});
        cursor.moveToFirst();
        String id = cursor.getString(0);
        ContentValues values = new ContentValues();
        values.put("lastClosed", simpleDateFormat.format(currentDate));
        values.put("timeRemaining", timeRemaining);

        db.update("Time", values, "id = ?", new String[]{id});
    }
}

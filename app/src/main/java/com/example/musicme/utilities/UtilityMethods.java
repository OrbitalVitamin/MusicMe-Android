package com.example.musicme.utilities;

import android.content.Context;
import android.text.LoginFilter;
import android.util.Log;

import com.example.musicme.objects.QuestionAttributes;
import com.example.musicme.objects.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UtilityMethods {

    public static String readFile(String filename, Context context) {

        String json;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static List<String> parseFileJson(String filename, Context context) throws IOException, JSONException {

        JSONArray jsonArray = new JSONArray(readFile(filename, context));
        List<String> data = new ArrayList<>();

        for(int i =0; i < jsonArray.length(); i++) {
            data.add(jsonArray.getString(i));
        }
        return data;
    }


}

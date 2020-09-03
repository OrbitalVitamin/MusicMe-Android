package com.example.musicme.utilities;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.musicme.objects.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InfoDownloader {
    private static RequestQueue mQueue;


    public static void SingleLinkJsonRequest(String route, Context context, final ReceiveSongListListener receiveSongListListener){
        String url = "https://musicmemarathonfunction.azurewebsites.net/api/" + route;
        mQueue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                receiveSongListListener.onSongListAvailable(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    public static void launchRequestForSingleLinkData(String url, Context context, ReceiveSongListListener receiveSongListListener) throws InterruptedException{
        SingleLinkJsonRequest(url, context, receiveSongListListener);
    }

    public static interface ReceiveSongListListener{
        void onSongListAvailable(JSONArray response);
    }

    public static Song jsonParse(JSONObject object) throws JSONException {
        JSONObject song = object.getJSONObject("song");
        String name = song.getString("name");
        String artist = song.getString("artist");

        new Song(name, artist);
        return  new Song(name, artist);
    }

    public static List<Song> parseSongResponse(JSONArray response) throws JSONException {
        List<Song> songs = new ArrayList<>();
        for (int i = 0; i < response.length(); i ++){
            songs.add(jsonParse(response.getJSONObject(i)));
        }
        return songs;
    }

    public static List<String> parseSongResponseArtist(JSONArray response) throws JSONException {
        List<String> artists = new ArrayList<>();
        for (int i = 0; i < response.length(); i ++){
            artists.add(response.getJSONObject(i).getString("name"));
        }
        return artists;
    }

}

package com.example.musicme.utilities;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.example.musicme.objects.Song;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BlobDownloader {
    private static RequestQueue mQueue;


    public static void DataRequest(final List<String> songs, String type, final Context context, final ReceiveDataListListener receiveDataListListener){
        mQueue = Volley.newRequestQueue(context);
        final List<byte[]> data = new ArrayList<>();
        final HashMap<String, byte[]> audioclips = new HashMap<>();
        final RequestCounter counter = new RequestCounter(songs.size());
        for(int i = 0; i < songs.size(); i++){
            String url = ""+songs.get(i)+type;
            final String song = songs.get(i);
            Request request = new Request(Request.Method.GET, url, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            }) {
                @Override
                protected Response parseNetworkResponse(NetworkResponse response) {
                    audioclips.put(song, response.data);
                    if(counter.decrementTillZero()){
                        receiveDataListListener.onSongListAvailable(audioclips);
                    }
                    return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
                }

                @Override
                protected void deliverResponse(Object response) {

                }

                @Override
                public int compareTo(Object o) {
                    return 0;
                }
            };
            mQueue.add(request);

        }

    }

   public static void launchRequestForData(List<String> songs, String type, Context context, ReceiveDataListListener receiveSongListListener) throws InterruptedException{
        DataRequest(songs, type, context, receiveSongListListener);
    }

    public static interface ReceiveDataListListener{
        void onSongListAvailable(HashMap<String, byte[]> audioclips);
    }

    private static Song jsonParse(JSONObject song) throws JSONException {

        String name = song.getString("name");
        String artist = song.getString("artist");

        new Song(name, artist);
        return  new Song(name, artist);
    }


}

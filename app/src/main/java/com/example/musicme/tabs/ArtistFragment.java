package com.example.musicme.tabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.musicme.R;
import com.example.musicme.adapters.ArtistRecyclerAdapter;
import com.example.musicme.adapters.GenreRecyclerAdapter;
import com.example.musicme.enums.GameState;
import com.example.musicme.objects.Song;
import com.example.musicme.utilities.InfoDownloader;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class ArtistFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private ArtistRecyclerAdapter adapter;
    private List<String> genreList = new ArrayList<>();
    private DisplayMetrics metrics;
    private EditText searchText;
    private ImageButton searchButton;

    public ArtistFragment() {
        // Required empty public constructor
    }

    public static ArtistFragment newInstance(String param1, String param2) {
        ArtistFragment fragment = new ArtistFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        metrics = new DisplayMetrics();

        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        genreList.add("K-Pop");
        genreList.add("Pop");
        genreList.add("Rock");
        genreList.add("K-Pop");
        genreList.add("Pop");
        genreList.add("Metal");
        genreList.add("EDM");
        genreList.add("K-Pop");
        genreList.add("Pop");
        genreList.add("Rock");
        genreList.add("K-Pop");
        genreList.add("Pop");
        genreList.add("Metal");
        genreList.add("EDM");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_artist, container, false);
        searchButton = root.findViewById(R.id.search_button);
        searchText = root.findViewById(R.id.artist_search);

        recyclerView = root.findViewById(R.id.area_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new ArtistRecyclerAdapter(getContext(), genreList, (int) (metrics.widthPixels * 0.35), getActivity(), GameState.ARTIST);
        recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    searchArtists(searchText.getText().toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        return root;
    }

    private void searchArtists(String text) throws InterruptedException {
        InfoDownloader.launchRequestForSingleLinkData("container/artists/" + text, getContext(), new InfoDownloader.ReceiveSongListListener() {
            @Override
            public void onSongListAvailable(JSONArray response) {
                try {
                    genreList = InfoDownloader.parseSongResponseArtist(response);
                    adapter = new ArtistRecyclerAdapter(getContext(), genreList, (int) (metrics.widthPixels * 0.35), getActivity(), GameState.ARTIST);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });

    }
}
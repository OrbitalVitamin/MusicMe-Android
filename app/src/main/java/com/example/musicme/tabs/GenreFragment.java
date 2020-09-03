package com.example.musicme.tabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicme.R;
import com.example.musicme.adapters.GenreRecyclerAdapter;
import com.example.musicme.adapters.LevelRecyclerAdapter;
import com.example.musicme.enums.GameState;
import com.example.musicme.objects.Genre;

import java.util.ArrayList;
import java.util.List;

public class GenreFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private LevelRecyclerAdapter adapter;
    private List<String> genreList = new ArrayList<>();
    private DisplayMetrics metrics;

    public GenreFragment() {
        // Required empty public constructor
    }

    public static GenreFragment newInstance(String param1, String param2) {
        GenreFragment fragment = new GenreFragment();
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
        View root = inflater.inflate(R.layout.fragment_genre, container, false);

        recyclerView = root.findViewById(R.id.genre_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);


        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new LevelRecyclerAdapter(getContext(), GameState.MAIN, genreList, (int) (metrics.widthPixels * 0.35), getActivity());

        recyclerView.setAdapter(adapter);
        recyclerView.setBackgroundResource(R.drawable.rounded_rectangle);

        return root;
    }
}
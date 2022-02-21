package com.example.musicme.marathon;

import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.example.musicme.utilities.UtilityMethods;

import org.json.JSONException;

import java.io.IOException;


public class SubMenuFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private DisplayMetrics displayMetrics = new DisplayMetrics();
    private RecyclerView recyclerView;
    private ConstraintLayout typeButton;
    private String genre;
    private GameState gameState;

    public SubMenuFragment() {
        // Required empty public constructor
    }

    public SubMenuFragment(String genre, GameState gameState) {
        this.genre = genre;
        this.gameState = gameState;
    }

    public static SubMenuFragment newInstance(String param1, String param2) {
        SubMenuFragment fragment = new SubMenuFragment();
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


        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sub_menu, container, false);
        recyclerView = root.findViewById(R.id.genre_list);
        typeButton = root.findViewById(R.id.typeButton);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        typeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MarathonQuizFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.marathon_layout, fragment).commit();
            }
        });


        this.determineList();
    }

    private void determineList(){
        if(this.gameState == GameState.MAIN) {
            try {
                setLevelGenreList();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                setGenreList();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setGenreList() throws IOException, JSONException {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.invalidateItemDecorations();
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = 110;
            }
        };

        recyclerView.addItemDecoration(itemDecoration);
        GenreRecyclerAdapter adapter = new GenreRecyclerAdapter(getContext(), UtilityMethods.parseFileJson("main.json", getContext()), (int) (displayMetrics.widthPixels * 0.35), this.gameState, recyclerView);

        recyclerView.setAdapter(adapter);

    }

    private void setLevelGenreList() throws IOException, JSONException {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.invalidateItemDecorations();
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = 110;
            }
        };

        recyclerView.addItemDecoration(itemDecoration);
        LevelRecyclerAdapter adapter = new LevelRecyclerAdapter(getContext(), this.gameState, UtilityMethods.parseFileJson(this.genre + ".json", getContext()), (int) (displayMetrics.widthPixels * 0.35), getActivity());
        recyclerView.setAdapter(adapter);
    }
}
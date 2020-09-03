package com.example.musicme.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicme.R;
import com.example.musicme.adapters.GenreRecyclerAdapter;
import com.example.musicme.adapters.LevelRecyclerAdapter;
import com.example.musicme.database.SqlPlayerData;
import com.example.musicme.enums.GameState;
import com.example.musicme.utilities.UtilityMethods;

import org.json.JSONException;

import java.io.IOException;
import java.util.Objects;

public class BuyGenreDialog extends Dialog {
    SqlPlayerData data;
    Button buyWithDiamonds, buyWithCoins;
    GameState gameState;
    String genre;
    Context context;
    RecyclerView recyclerView;
    int metrics;
    public BuyGenreDialog(@NonNull Context context, GameState gameState, String genre, RecyclerView recyclerView, int metrics) {
        super(context);
        this.context = context;
        this.gameState = gameState;
        this.genre = genre;
        this.data = new SqlPlayerData(context, true);
        this.recyclerView = recyclerView;
        this.metrics = metrics;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_genre_dialog);
        buyWithCoins = findViewById(R.id.buy_genre_button_coins);
        buyWithDiamonds = findViewById(R.id.buy_genre_button_diamonds);

        buyWithCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onBuyClick();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

        buyWithDiamonds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void onBuyClick() throws IOException, JSONException {
        if (data.usePlayerCoins(99)) {
            data.buyGenre(this.gameState.getValue(), this.genre);
            dismiss();
            setRecyclerView();

        }
    }

    private void setRecyclerView() throws IOException, JSONException {

        GenreRecyclerAdapter adapter = new GenreRecyclerAdapter(getContext(), UtilityMethods.parseFileJson("main.json", getContext()), metrics, GameState.MARATHON, recyclerView);

        recyclerView.setAdapter(adapter);


    }
}

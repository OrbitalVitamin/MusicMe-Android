package com.example.musicme.marathon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicme.R;
import com.example.musicme.adapters.GenreRecyclerAdapter;
import com.example.musicme.marathon.MarathonLifeRecyclerAdapter;
import com.example.musicme.customViews.AnswerItem;
import com.example.musicme.enums.GameState;
import com.example.musicme.enums.AnswerState;
import com.example.musicme.objects.QuestionAttributes;
import com.example.musicme.objects.Song;
import com.example.musicme.utilities.BlobDownloader;
import com.example.musicme.utilities.InfoDownloader;
import com.example.musicme.utilities.SongFilterUtilities;
import com.example.musicme.utilities.UtilityMethods;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MarathonActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maraton);
        Fragment fragment = new SubMenuFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.marathon_layout, fragment).commit();
    }
}
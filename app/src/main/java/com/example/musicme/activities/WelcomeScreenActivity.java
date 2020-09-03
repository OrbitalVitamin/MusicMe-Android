package com.example.musicme.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.musicme.R;
import com.example.musicme.database.SqlPlayerData;
import com.example.musicme.marathon.MarathonActivity;
import com.example.musicme.objects.LevelStore;
import com.example.musicme.objects.UserStore;

public class WelcomeScreenActivity extends AppCompatActivity {
    ConstraintLayout mainButton, sprintButton, marathonButton, multiplayerButton;
    Intent mainIntent, sprintIntent, marathonIntent, multiplayerIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        mainButton = findViewById(R.id.main_button);
        sprintButton = findViewById(R.id.sprint_button);
        marathonButton = findViewById(R.id.marathon_button);
        multiplayerButton = findViewById(R.id.multi_button);

         mainIntent = new Intent(this, MainActivity.class);
         sprintIntent = new Intent(this, SprintActivity.class);
         marathonIntent = new Intent(this, MarathonActivity.class);
         multiplayerIntent = new Intent(this, MultiplayerActivity.class);


        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mainIntent);
            }
        });

        marathonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(sprintIntent);
            }
        });

        marathonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(marathonIntent);
            }
        });

        multiplayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(multiplayerIntent);
            }
        });

        initUser();

    }

    private void initUser(){
        SqlPlayerData sqlPlayerData = new SqlPlayerData(this, true);
        UserStore userStore = ((UserStore) getApplicationContext());
        sqlPlayerData.loadUser(userStore);
    }
}
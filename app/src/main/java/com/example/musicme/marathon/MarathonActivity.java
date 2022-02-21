package com.example.musicme.marathon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;


import com.example.musicme.R;


public class MarathonActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maraton);
        Fragment fragment = new SubMenuFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.marathon_layout, fragment).commit();
    }
}
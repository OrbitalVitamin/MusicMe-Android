package com.example.musicme.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

import com.example.musicme.R;
import com.example.musicme.campaign.MenuFragment;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragment = new MenuFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.campaign_layout, fragment).commit();
    }


}
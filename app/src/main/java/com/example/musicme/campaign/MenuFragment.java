package com.example.musicme.campaign;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicme.R;
import com.example.musicme.activities.MainActivity;
import com.example.musicme.activities.WelcomeScreenActivity;
import com.example.musicme.adapters.SectionsPagerAdapter;
import com.example.musicme.objects.UserStore;
import com.example.musicme.tabs.ArtistFragment;
import com.example.musicme.tabs.GenreFragment;
import com.example.musicme.tabs.TimeFragment;


public class MenuFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView titleView;
    DisplayMetrics displayMetrics = new DisplayMetrics();

    ViewPager viewPager;
    ConstraintLayout tabs;


    ImageView[] tabList = new ImageView[3];

    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        titleView = root.findViewById(R.id.title);
        viewPager = root.findViewById(R.id.view_pager);
        tabs = root.findViewById(R.id.tabs);
        tabList[0] = root.findViewById(R.id.genre_tab);
        tabList[1] = root.findViewById(R.id.time_tab);
        tabList[2] = root.findViewById(R.id.area_tab);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Intent intent = new Intent(getActivity(), WelcomeScreenActivity.class);

        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(intent);
            }
        });
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new GenreFragment());
        adapter.addFragment(new ArtistFragment());
        adapter.addFragment(new TimeFragment());
        viewPager.setAdapter(adapter);
    }
}
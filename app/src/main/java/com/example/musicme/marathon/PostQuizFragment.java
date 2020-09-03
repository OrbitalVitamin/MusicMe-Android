package com.example.musicme.marathon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.musicme.R;


public class PostQuizFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private TextView postQuizText;
    private Button backToMenuButton;

    private int correct;

    public PostQuizFragment() {
        // Required empty public constructor
    }

    public PostQuizFragment(int correct) {
        this.correct = correct;
    }


    public static PostQuizFragment newInstance(String param1, String param2) {
        PostQuizFragment fragment = new PostQuizFragment();
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
        View root = inflater.inflate(R.layout.fragment_post_quiz_view, container, false);
        postQuizText = root.findViewById(R.id.score_text);
        backToMenuButton = root.findViewById(R.id.back_to_menu_button);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setPostQuizView();
    }

    private void setPostQuizView(){
        String text = "Your score is " + this.correct;
        postQuizText.setText(text);

        backToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SubMenuFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.marathon_layout, fragment);
            }
        });

    }
}
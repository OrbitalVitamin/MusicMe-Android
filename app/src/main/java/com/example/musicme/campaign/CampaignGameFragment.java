package com.example.musicme.campaign;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.musicme.R;
import com.example.musicme.customViews.AnswerItem;
import com.example.musicme.enums.AnswerState;
import com.example.musicme.marathon.MarathonLifeRecyclerAdapter;
import com.example.musicme.marathon.PostQuizFragment;
import com.example.musicme.objects.QuestionAttributes;
import com.example.musicme.objects.Song;
import com.example.musicme.utilities.BlobDownloader;
import com.example.musicme.utilities.InfoDownloader;
import com.example.musicme.utilities.RequestCounter;
import com.example.musicme.utilities.SongFilterUtilities;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CampaignGameFragment extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private int correct = 0;
    private int qIndex = 0;
    private int lives = 3;

    private View game;
    private View loading;
    private HashMap<String, byte[]> mAudioclips = new HashMap<>();
    private HashMap<String, byte[]> mHintAudioclips = new HashMap<>();


    AnswerItem[]  answerItems = new AnswerItem[4];
    File temp;
    ImageView playBttn;
    private MediaPlayer mediaPlayer;
    private List<QuestionAttributes> questionAttributes = new ArrayList<>();
    private RecyclerView lifeView;
    DisplayMetrics displayMetrics = new DisplayMetrics();

    public CampaignGameFragment() {
        // Required empty public constructor
    }


    public static CampaignGameFragment newInstance(String param1, String param2) {
        CampaignGameFragment fragment = new CampaignGameFragment();
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
        View view = inflater.inflate(R.layout.fragment_marathon_quiz, container, false);
        answerItems[0] = view.findViewById(R.id.answer1);
        answerItems[1] = view.findViewById(R.id.answer2);;
        answerItems[2] = view.findViewById(R.id.answer3);
        answerItems[3] = view.findViewById(R.id.answer4);
        playBttn = view.findViewById(R.id.play_button);
        game = view.findViewById(R.id.quiz_view);
        loading = view.findViewById(R.id.loading_screen);
        lifeView = view.findViewById(R.id.lives_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        playBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
            }
        });

        setOnClickListeners();
        try {
            startLoading();
        } catch (InterruptedException e) {

        }
    }

    private void setOnClickListeners(){
        answerItems[0].setOnClickListener(this);
        answerItems[1].setOnClickListener(this);
        answerItems[2].setOnClickListener(this);
        answerItems[3].setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.answer1:
                try {
                    answerClicked(answerItems[0], 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.answer2:
                try {
                    answerClicked(answerItems[1], 2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.answer3:
                try {
                    answerClicked(answerItems[2], 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.answer4:
                try {
                    answerClicked(answerItems[3], 4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void startLoading() throws InterruptedException {
        game.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        fetchSongs();
    }

    private void processResponse(List<Song> songList){
        questionAttributes = SongFilterUtilities.populateHolder(songList, 10);
        List<String> songs = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            songs.add(questionAttributes.get(i).getSoundfile());
        }
        try {
            retrieveAudio(songs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void retrieveAudio(List<String> songs) throws InterruptedException {
        final RequestCounter counter = new RequestCounter(2);

        BlobDownloader.launchRequestForData(songs, ".mp3", getContext(), new BlobDownloader.ReceiveDataListListener() {
            @Override
            public void onSongListAvailable(HashMap<String, byte[]> audioclips) {
                mAudioclips.putAll(audioclips);
                if(game.getVisibility() != View.VISIBLE && counter.decrementTillZero()) {
                    startQuiz();
                }
            }
        });

        BlobDownloader.launchRequestForData(songs, " (Hint).mp3", getContext(), new BlobDownloader.ReceiveDataListListener() {
            @Override
            public void onSongListAvailable(HashMap<String, byte[]> audioclips) {
                mHintAudioclips.putAll(audioclips);
                if(game.getVisibility() != View.VISIBLE && counter.decrementTillZero()) {
                    startQuiz();
                }
            }
        });
    }
    private void fetchSongs() throws InterruptedException {
        InfoDownloader.launchRequestForSingleLinkData("songs/genre/Pop/20/0", getContext(), new InfoDownloader.ReceiveSongListListener() {
            @Override
            public void onSongListAvailable(JSONArray response) {
                List<Song> songList = new ArrayList<>();

                try {
                    songList =InfoDownloader.parseSongResponse(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                processResponse(songList);
            }
        });
    }

    private void startQuiz(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                prepareAudio(mAudioclips.get(questionAttributes.get(qIndex).getSoundfile()));
                loading.setVisibility(View.GONE);
                game.setVisibility(View.VISIBLE);
                setLifeView();
                setText();
                mediaPlayer.start();
            }
        });


    }

    private void answerClicked(final AnswerItem answerItem, int index) throws InterruptedException {
        if(index == this.questionAttributes.get(this.qIndex).getCorrectIndex()){
            answerItem.setAnswerState(AnswerState.CORRECT);
            this.correct++;
        } else {
            answerItem.setAnswerState(AnswerState.WRONG);
            this.lives--;
            setLifeView();
        }

        disable();
        this.mediaPlayer.stop();

        if(this.qIndex < this.questionAttributes.size()-1) {
            prepareAudio(this.mAudioclips.get(this.questionAttributes.get(qIndex+1).getSoundfile()));
        }

        if(this.qIndex%10==7){
            fetchSongs();
        }
        new CountDownTimer(1000, 500) {

            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                if (lives > 0) {
                    ++qIndex;
                    setText();
                    answerItem.setAnswerState(AnswerState.DEFAULT);
                    enable();
                    mediaPlayer.start();
                } else {
                    Fragment fragment = new PostQuizFragment(correct);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.marathon_layout, fragment).commit();
                }
            }
        }.start();
    }

    private void setText() {
        answerItems[0].setText(questionAttributes.get(qIndex).getQuestionNameHolder().getName1());
        answerItems[1].setText(questionAttributes.get(qIndex).getQuestionNameHolder().getName2());
        answerItems[2].setText(questionAttributes.get(qIndex).getQuestionNameHolder().getName3());
        answerItems[3].setText(questionAttributes.get(qIndex).getQuestionNameHolder().getName4());
    }

    private void disable(){
        answerItems[0].setEnabled(false);
        answerItems[1].setEnabled(false);
        answerItems[2].setEnabled(false);
        answerItems[3].setEnabled(false);
    }

    private void enable(){
        answerItems[0].setEnabled(true);
        answerItems[1].setEnabled(true);
        answerItems[2].setEnabled(true);
        answerItems[3].setEnabled(true);
    }

    private void setLifeView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        lifeView.setLayoutManager(layoutManager);
        MarathonLifeRecyclerAdapter adapter = new MarathonLifeRecyclerAdapter(lives, this.displayMetrics.heightPixels/10, this.displayMetrics.widthPixels/2);
        lifeView.setAdapter(adapter);

    }

    private void prepareAudio(byte[] audioByteArray) {

        try {
            this.temp = File.createTempFile("TCL", "mp3", getActivity().getCacheDir());
            this.temp.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(this.temp);
            fos.write(audioByteArray);
            fos.close();
            mediaPlayer = new MediaPlayer();
            FileInputStream MyFile = new FileInputStream(this.temp);
            mediaPlayer.setDataSource(this.temp.getPath());
            mediaPlayer.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
package com.example.musicme.customViews;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.musicme.R;
import com.example.musicme.database.SqlPlayerData;
import com.example.musicme.objects.UserStore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class UserStatsView extends ConstraintLayout {
    private UserStore userStore;

    View view;
    TextView lives, diamonds, coins, time;
    public UserStatsView(Context context) {
        super(context);
    }

    public UserStatsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        this.view = inflater.inflate(R.layout.user_stats_view, this);
        init();
    }

    public UserStatsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){
        lives = view.findViewById(R.id.hear_text);
        diamonds = view.findViewById(R.id.dia_text);
        coins = view.findViewById(R.id.coin_text);
        time = view.findViewById(R.id.life_time);
        timer(900);
    }

    private int lifeRemainingSeconds(UserStore userStore) throws ParseException {
        if(5 > userStore.getLives()){
            return 900-(findTimeSinceClosed()%900);
        }else {
            return 900;
        }
    }

    private int findTimeSinceClosed() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date lastDate = dateFormat.parse(SqlPlayerData.getLastTimeClosed());
        assert lastDate != null;
        long lastTime = lastDate.getTime();
        long currentTime = Calendar.getInstance().getTime().getTime();
        return (int) (currentTime-lastTime);
    }

    private void setRemainingTime(long milis){
        int seconds = (int) (milis/1000);
        int minutes = seconds/60;

        seconds = seconds%60;


        String text= minutes + ":" + seconds;
        time.setText(text);
    }

    private void timer(int remainingTime){
        new CountDownTimer(remainingTime*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setRemainingTime(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                if(5 > userStore.getLives()){
                    userStore.setLives(userStore.getLives()+1);
                    if(5 >userStore.getLives()){
                        timer(900);
                    }
                }
            }
        }.start();
    }

}

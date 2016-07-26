package com.example.a6g.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static  long UPDATE_EVERY=200;
    protected TextView counter;
    protected Button start;
    protected Button stop;
    protected boolean timeRunning;
    protected long startedAt;
    protected long lastStopped;
    protected Handler handler;
    protected  UpdateTimer updateTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counter = (TextView) findViewById(R.id.timer);
        start = (Button) findViewById((R.id.btnStart));
        stop = (Button) findViewById(R.id.btnStop);

    }

    //开始方法
    public void ClickStart(View view) {
        timeRunning = true;
        EnableButtons();
        startedAt=System.currentTimeMillis();
        handler =new Handler();
        updateTimer=new UpdateTimer();
        handler.postDelayed(updateTimer,UPDATE_EVERY);
    }

    //结束方法
    public void ClickStop(View view) {
        timeRunning = false;
        EnableButtons();
        lastStopped=System.currentTimeMillis();
        handler.removeCallbacks(updateTimer);
        handler=null;
    }

    private void EnableButtons() {
        start.setEnabled(!timeRunning);
        stop.setEnabled(timeRunning);
    }

    protected void setTimeDisplay()
    {
        String display;
        long timeNow;
        long diff;
        long seconds;
        long minutes;
        long hours;
        if(timeRunning){
            timeNow=System.currentTimeMillis();
        }
        else {
            timeNow=lastStopped;
        }
        diff=timeNow-startedAt;
        if(diff<0)
            diff=0;
        seconds=diff/1000;
        minutes=seconds/60;
        hours=minutes/60;
        seconds=seconds%60;
        minutes=minutes%60;
        display=String.format("%d",hours)+":"+String.format("%02d",minutes)+":"+String.format("%02d",seconds);
        counter.setText(display);
    }
    class UpdateTimer implements Runnable{

        @Override
        public void run() {
            setTimeDisplay();
            if(handler!=null)
                handler.postDelayed(this,UPDATE_EVERY);
        }
    }

}


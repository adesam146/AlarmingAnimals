package adesam146.alarminganimals;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.Random;

import adesam146.alarminganimals.utils.Constants;

public class StopAlarmActivity extends AppCompatActivity {

    private ViewGroup mContentView;
    private Random random;
    private int mScreenHeight, mScreenWidth;
    //Setting up the colours to be picked randomly for  the background
    private int[] backgroundColours = {android.R.color.holo_blue_bright,
            android.R.color.holo_orange_light, android.R.color
            .holo_red_light, android.R.color.holo_purple, android.R.color
            .holo_green_light};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_alarm);

        random = new Random();

        mContentView = (ViewGroup) findViewById(R.id.activity_stop_alarm);

        setBackgroundColour();
        setToFullScreen();

        ViewTreeObserver viewTreeObserver = mContentView.getViewTreeObserver();
        if(viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    //Todo: This is to ensure it doesn't happen over and over
                    // again? Understand.
                    mContentView.getViewTreeObserver()
                            .removeOnGlobalLayoutListener(this);
                    mScreenWidth = mContentView.getWidth();
                    mScreenHeight = mContentView.getHeight();
                }
            });
        }

        //So that when the person clicks on the screen it goes to fullscreen
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setToFullScreen();
            }
        });

        mContentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Animal animal = new Animal(StopAlarmActivity.this, 100,
                            100);
                    animal.setX(motionEvent.getX());
                    animal.setY(mScreenHeight);
                    //Todo: animal image to be set according to value chosen
                    // by user
                    animal.setImageResource(R.drawable.rooster);
                    mContentView.addView(animal);
                    animal.appear(mScreenHeight, 1000);
                }
                return false;
            }
        });

    }

    private void setToFullScreen() {
        ViewGroup rootLayout = (ViewGroup) findViewById(R.id.activity_stop_alarm);
        rootLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setToFullScreen();
    }

    private void setBackgroundColour() {
        int chosenColour = backgroundColours[random.nextInt(backgroundColours
                .length)];
        //This was done because the getColor without a theme parameter method
        // only works for AP1 < 23 (Marshmallow)
        if(Build.VERSION.SDK_INT < Constants.ANDROID_MARSHMALLOW){
            mContentView.setBackgroundColor(getResources().getColor
                    (chosenColour));
        } else{
            mContentView.setBackgroundColor(getResources().getColor
                    (chosenColour, null));
        }
    }
}

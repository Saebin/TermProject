package com.example.termproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class FirstActivity extends Activity {
    final String TAG = "AnimationTest";
    ImageView mSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        mSplash = (ImageView) findViewById(R.id.splash);

    }

    protected void onResume() {
        super.onResume();

        Log.i("asdfasdf", "sadfsdfas22222222222222222");
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        startSplashTweenAnimation();

    }

    private void startSplashTweenAnimation() {
        Animation splash_anim = AnimationUtils.loadAnimation(this, R.anim.splash);
        mSplash.startAnimation(splash_anim);
        splash_anim.setAnimationListener(animationListener);
    }

    Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            Log.i(TAG, "onAnimationStart");
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Log.i(TAG, "onAnimationEnd!!!!!!!!!!!!!!!!!!!!!!!!!!");
            FirstActivity.this.finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            Log.i(TAG, "onAnimationRepeat");
        }
    };
}
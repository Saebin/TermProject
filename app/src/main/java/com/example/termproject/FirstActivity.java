package com.example.termproject;

import android.animation.Animator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class FirstActivity extends AppCompatActivity {
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

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //mScreenHeight = displaymetrics.heightPixels;

        startSplashTweenAnimation();

        /**
         * 아래 4가지 startRocket 애니메이션 중에 하나를 선택하여 테스트해 보세요.
         */
        // startRocketTweenAnimation();
//      startRocketObjectPropertyAnimation();
//      startRocketPropertyAnimationByXML();
//      startRocketValuePropertyAnimation();

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
            Log.i(TAG, "onAnimationEnd");
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            Log.i(TAG, "onAnimationRepeat");
        }
    };

    Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {
            Log.i(TAG, "onAnimationStart");
        }

        @Override
        public void onAnimationEnd(Animator animator) {
            Log.i(TAG, "onAnimationEnd");
        }

        @Override
        public void onAnimationCancel(Animator animator) {
            Log.i(TAG, "onAnimationCancel");
        }

        @Override
        public void onAnimationRepeat(Animator animator) {
            Log.i(TAG, "onAnimationRepeat");
        }
    };
}
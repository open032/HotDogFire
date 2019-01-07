package com.example.alexandr.fristanimationtest;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private float x;
    private float bAnimation;
    private float y;
    private float hAnimation;
    private float l;
    private float radian;
    private float screenHeight = 0f;
    private float xPosition;
    private float yPosition;
    private float centerX;
    private float centerY;
    private FrameLayout frameLayout;
    private static final long DEFAULT_ANIMATION_DURATION = 2500L;
    private ImageView catImageView;
    private static final String TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        catImageView = findViewById(R.id.cat_image_view);
        frameLayout = findViewById(R.id.frame_layout);
        frameLayout.setOnTouchListener(this);
        frameLayout.setOnClickListener(onClickListener);

    }

        @Override
        public boolean onTouch (View v, MotionEvent event){
            xPosition = event.getX();
            yPosition = event.getY();
            centerY = screenHeight - 225;

            x = centerX - xPosition;
            y = centerY - yPosition;
            l = (float) Math.sqrt((y * y) + (x * x));
            radian = (float) Math.asin(y/l);
            bAnimation = (float) Math.cos(radian) * 3500;
            hAnimation = (float) Math.sin(radian) * 3500;

            if(xPosition >= centerX){
                x = xPosition - centerX;
                y = centerY - yPosition;
                l = (float) Math.sqrt((y * y) + (x * x));

                radian = (float) Math.asin(y/l);
                bAnimation = -(float) Math.cos(radian) * 3500;
                Log.d(TAG, " bAnimation = " + bAnimation);
                hAnimation = (float) Math.sin(radian) * 3500;
            }


//            Log.d(TAG, " b = " + bLeft);
//            Log.d(TAG, " h = " + h);
//            Log.d(TAG, " l = " + l);
//            Log.d(TAG, " radian = " + radian);


            return false;
        }

    @Override
    protected void onResume() {
        super.onResume();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = Float.valueOf(displayMetrics.heightPixels);
        centerX = Float.valueOf(displayMetrics.widthPixels) / 2;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            ValueAnimator valueAnimatorX = ValueAnimator.ofFloat(0f, -bAnimation);
            Log.d(TAG, " bAnimation = " + bAnimation);
            ValueAnimator valueAnimatorY = ValueAnimator.ofFloat(0f, -hAnimation);

            valueAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float value = (float)valueAnimator.getAnimatedValue();

                    //Log.d(TAG, "myLogs" + " y = " + yPosition);
                    //Log.d(TAG, "myLogs" + " center = " + center);
                    catImageView.setTranslationY(value);

                }
            });
            valueAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float value = (float)valueAnimator.getAnimatedValue();

                    //Log.d(TAG, "myLogs" + " x = " + xPosition);
                    catImageView.setTranslationX(value);
                }
            });

            valueAnimatorY.setInterpolator(new LinearInterpolator());
            valueAnimatorY.setDuration(DEFAULT_ANIMATION_DURATION);

            valueAnimatorY.start();

            valueAnimatorX.setInterpolator(new LinearInterpolator());
            valueAnimatorX.setDuration(DEFAULT_ANIMATION_DURATION);

            valueAnimatorX.start();

        }
    };


}
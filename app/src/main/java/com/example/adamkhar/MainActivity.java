package com.example.adamkhar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adamkhar.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private RelativeLayout mRelLay;
    private float mInitialX, mInitialY;
    private int mInitialLeft, mInitialTop;
    private View mMovingView = null;
    private TextView textView;
    private Boolean boatDown = true;
    private ActivityMainBinding binding;
    private ArrayList<Integer> positions;
    private RelativeLayout.LayoutParams boatLayoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        ImageView imageView = findViewById(R.id.image);
        textView = findViewById(R.id.textView);
        mRelLay = (RelativeLayout) findViewById(R.id.relativeLayout);
        positions = new ArrayList<Integer>(6);

        for (int i = 0; i<6;i++ ) {
            positions.add(i, 1);
        }

        for (int i = 0; i < mRelLay.getChildCount(); i++)
            mRelLay.getChildAt(i).setOnTouchListener(this);

        final float factor = this.getResources().getDisplayMetrics().density;

        boatLayoutParams = (RelativeLayout.LayoutParams) binding.boat.getLayoutParams();

        binding.go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boatDown){
                    boatLayoutParams.topMargin = (int) (360 * factor);
                    binding.boat.setLayoutParams(boatLayoutParams);
                    boatDown = false;
                    Toast.makeText(MainActivity.this, "go", Toast.LENGTH_SHORT).show();
                }
                else {
                    boatLayoutParams.topMargin = (int) (480 * factor);
                    binding.boat.setLayoutParams(boatLayoutParams);
                    boatDown = true;
                }
            }
        });

        binding.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.recreate();
            }
        });
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        RelativeLayout.LayoutParams mLayoutParams;


        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mMovingView = view;
                if (view.getId() ==  R.id.boat){
                    Toast.makeText(this, "boat top margin: " + boatLayoutParams.topMargin, Toast.LENGTH_SHORT).show();
                    break;
                }
                else if (view.getId() ==  R.id.go || view.getId() ==  R.id.clear) {
                    view.callOnClick();
                    break;
                }
                mLayoutParams = (RelativeLayout.LayoutParams) mMovingView.getLayoutParams();
                mInitialX = motionEvent.getRawX();
                mInitialY = motionEvent.getRawY();
                mInitialLeft = mLayoutParams.leftMargin;
                mInitialTop = mLayoutParams.topMargin;
                break;

        case MotionEvent.ACTION_MOVE:
            if (view.getId() == R.id.boat || view.getId() ==  R.id.go || view.getId() ==  R.id.clear) {
                break;
            }
            textView.setText("vampire : " + positions.get(0));
            mLayoutParams = (RelativeLayout.LayoutParams) mMovingView.getLayoutParams();


            if (mMovingView != null) {
                if (boatDown) {
                    if (mMovingView.getId() == R.id.vampire1 && positions.get(0) != 3) {
                        mLayoutParams.leftMargin = (int) (mInitialLeft + motionEvent.getRawX() - mInitialX);
                        mLayoutParams.topMargin = (int) (mInitialTop + motionEvent.getRawY() - mInitialY);
                        if ( mLayoutParams.topMargin <= boatLayoutParams.topMargin)
                            mLayoutParams.topMargin = boatLayoutParams.topMargin ;
                        if (mLayoutParams.topMargin < boatLayoutParams.topMargin + 110 )
                            positions.set(0 , 2);
                        else
                            positions.set(0 , 1);

                        mMovingView.setLayoutParams(mLayoutParams);
                    }
                }
                else {

                }
            }
            break;

        case MotionEvent.ACTION_UP:
            mMovingView = null;
            break;
        }


        return true;
    }
}
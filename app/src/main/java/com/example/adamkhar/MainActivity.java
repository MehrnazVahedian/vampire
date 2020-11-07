package com.example.adamkhar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
    private ActivityMainBinding binding;
    private RelativeLayout.LayoutParams boatLayoutParams;
    private States states;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        ImageView imageView = findViewById(R.id.image);
        textView = findViewById(R.id.textView);
        mRelLay = (RelativeLayout) findViewById(R.id.relativeLayout);
        states = new States();



        for (int i = 0; i < mRelLay.getChildCount(); i++)
            mRelLay.getChildAt(i).setOnTouchListener(this);

        final float factor = this.getResources().getDisplayMetrics().density;

        boatLayoutParams = (RelativeLayout.LayoutParams) binding.boat.getLayoutParams();

        binding.go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (states.isBoatDown()){
                    if (states.go()) {
                        boatLayoutParams.topMargin = (int) (370 * factor);
                        binding.boat.setLayoutParams(boatLayoutParams);
                        states.setBoatDown(false);
                        for (Person person: states.inBoat)
                        {
                            mMovingView = findViewById(person.getView());
                            RelativeLayout.LayoutParams personLayoutParams = (RelativeLayout.LayoutParams) mMovingView.getLayoutParams();
                            personLayoutParams.topMargin -= 115 * factor;
                            mMovingView.setLayoutParams(personLayoutParams);
                        }

                    }
                    else
                        Toast.makeText(MainActivity.this, "برای حرکت قایق حداقل 1 و حداثر 2 سرنشین باید داشته باشیم", Toast.LENGTH_LONG).show();

                }
                else {
                    if (states.go()) {
                        boatLayoutParams.topMargin = (int) (485 * factor);
                        binding.boat.setLayoutParams(boatLayoutParams);
                        states.setBoatDown(true);
                        for (Person person: states.inBoat)
                        {
                            mMovingView = findViewById(person.getView());
                            RelativeLayout.LayoutParams personLayoutParams = (RelativeLayout.LayoutParams) mMovingView.getLayoutParams();
                            personLayoutParams.topMargin += 115 * factor;
                            mMovingView.setLayoutParams(personLayoutParams);
                        }
                    }
                    else
                        Toast.makeText(MainActivity.this, "برای حرکت قایق حداقل 1 و حداثر 2 سرنشین باید داشته باشیم", Toast.LENGTH_LONG).show();


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
            mLayoutParams = (RelativeLayout.LayoutParams) mMovingView.getLayoutParams();


            if (mMovingView != null) {
                int arrayPosition = states.getArrayPosition(mMovingView.getId());
                if (states.isBoatDown()) {
                    if (arrayPosition >= 0 &&
                            states.persons.get(arrayPosition).getPosition() != Positions.Up) {
                        mLayoutParams.leftMargin = (int) (mInitialLeft + motionEvent.getRawX() - mInitialX);
                        mLayoutParams.topMargin = (int) (mInitialTop + motionEvent.getRawY() - mInitialY);
                        if ( mLayoutParams.topMargin <= boatLayoutParams.topMargin)
                            mLayoutParams.topMargin = boatLayoutParams.topMargin ;
                        if (mLayoutParams.topMargin < boatLayoutParams.topMargin + 110 )
                            states.persons.get(arrayPosition).setPosition(Positions.Boat);
                        else
                            states.persons.get(arrayPosition).setPosition(Positions.Down);

                        mMovingView.setLayoutParams(mLayoutParams);
                    }
                }
                else {
                    if (arrayPosition >= 0 &&
                            states.persons.get(arrayPosition).getPosition() != Positions.Down) {
                        mLayoutParams.leftMargin = (int) (mInitialLeft + motionEvent.getRawX() - mInitialX);
                        mLayoutParams.topMargin = (int) (mInitialTop + motionEvent.getRawY() - mInitialY);
                        if ( mLayoutParams.topMargin >= boatLayoutParams.topMargin)
                            mLayoutParams.topMargin = boatLayoutParams.topMargin ;
                        if (mLayoutParams.topMargin > boatLayoutParams.topMargin - 20)
                            states.persons.get(arrayPosition).setPosition(Positions.Boat);
                        else
                            states.persons.get(arrayPosition).setPosition(Positions.Up);

                        mMovingView.setLayoutParams(mLayoutParams);
                    }
                }

            }
            break;

        case MotionEvent.ACTION_UP:
            if (states.gameState() == GameStates.Win) {
                Intent i = new Intent(MainActivity.this, win.class);
                startActivity(i);
                finish();
            }
            else if (states.gameState() == GameStates.GameOver)
            {
                Intent i = new Intent(MainActivity.this, GameOver.class);
                startActivity(i);
                finish();
            }

            mMovingView = null;
            break;
        }

        return true;
    }

}
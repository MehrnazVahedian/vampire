package com.example.adamkhar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.adamkhar.databinding.ActivityIntroBinding;

public class IntroActivity extends AppCompatActivity {

    ViewPagerAdapter adapter;
    Integer[] layouts;
    ActivityIntroBinding binding;
    ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_intro);

        layouts = new Integer[]{R.layout.page_one,R.layout.page_two,R.layout.page_three};

        adapter = new ViewPagerAdapter(layouts,this);
        binding.introViewPager.setAdapter(adapter);

        createDots(0);

        binding.introViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);

                if(position==layouts.length-1)
                {
                    binding.introActivityNextButton.setText("برو بریم");
                    binding.introActivitySkipButton.setVisibility( View.GONE);
                }
                else{
                    binding.introActivityNextButton.setText("خُب");
                    binding.introActivitySkipButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        binding.introActivityNextButton.setOnClickListener(v -> {
            nextSlide();
        });

        binding.introActivitySkipButton.setOnClickListener(v -> {
            // PreferenceManager(this).writePreference()
            loadHome();
        });

    }

    private void loadHome(){
        Intent i = new Intent(IntroActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void nextSlide(){
        int next_slide = binding.introViewPager.getCurrentItem() + 1 ;
        if(next_slide<layouts.length)
        {
            binding.introViewPager.setCurrentItem(next_slide);
        }
        else
        {
            loadHome();
        }
    }

    private void createDots(int currentPosition){
        binding.dotsLayout.removeAllViews();

        dots = new ImageView[layouts.length];

        for (int i = 0; i < layouts.length ; i++){
            dots[i] = new ImageView(this);

            if(i==currentPosition)
                dots[i].setImageDrawable(this.getDrawable(R.drawable.active_dots));
            else
                dots[i].setImageDrawable(this.getDrawable(R.drawable.default_dots));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5,0,5,0);

            binding.dotsLayout.addView(dots[i],params);

        }


    }
}
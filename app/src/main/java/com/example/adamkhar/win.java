package com.example.adamkhar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import com.example.adamkhar.databinding.ActivityWinBinding;

public class win extends AppCompatActivity {

    ActivityWinBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_win);


        binding.playAgainButton.setOnClickListener(v -> {
            Intent i = new Intent(win.this, MainActivity.class);
            startActivity(i);
            finish();
        });
    }

}

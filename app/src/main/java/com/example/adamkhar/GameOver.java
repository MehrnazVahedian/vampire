package com.example.adamkhar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.adamkhar.databinding.ActivityGameOverBinding;

public class GameOver extends AppCompatActivity {

    ActivityGameOverBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_game_over);

        binding.playAgainButton.setOnClickListener(v -> {
            Intent i = new Intent(GameOver.this, MainActivity.class);
            startActivity(i);
            finish();
        });
    }
}
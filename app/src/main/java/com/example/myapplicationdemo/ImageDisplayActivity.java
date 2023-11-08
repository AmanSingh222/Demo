package com.example.myapplicationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplicationdemo.databinding.ActivityImageDisplayBinding;

public class ImageDisplayActivity extends AppCompatActivity {
    ActivityImageDisplayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityImageDisplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}
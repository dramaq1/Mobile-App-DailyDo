package com.example.dailydo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.example.dailydo.databinding.ActivityMainBinding;
import com.example.dailydo.screens.CreateTaskFragment;
import com.example.dailydo.screens.MainFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_host_fragment, new MainFragment())
                .commit();
        binding.fab.setOnClickListener(v -> {
            CreateTaskFragment createTaskFragment = new CreateTaskFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, createTaskFragment)
                    .addToBackStack(null)
                    .commit();
        });

    }
}
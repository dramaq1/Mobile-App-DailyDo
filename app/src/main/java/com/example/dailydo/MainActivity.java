package com.example.dailydo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.dailydo.contract.HasCustomTitle;
import com.example.dailydo.databinding.ActivityMainBinding;
import com.example.dailydo.screens.CalendarTasksFragment;
import com.example.dailydo.screens.CreateTaskFragment;
import com.example.dailydo.screens.MainFragment;
import com.example.dailydo.screens.SettingsFragment;
import com.example.dailydo.screens.StatisticsTasksFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private Fragment getCurrentFragment() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (currentFragment != null) {
            return currentFragment;
        } else {
            throw new NullPointerException("Fragment is null");
        }
    }

    private final FragmentManager.FragmentLifecycleCallbacks fragmentListener = new FragmentManager.FragmentLifecycleCallbacks() {
        @Override
        public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState);
            updateUi();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.nav_host_fragment, new MainFragment())
                    .commit();
        }

        getSupportFragmentManager().registerFragmentLifecycleCallbacks(fragmentListener,false);
        binding.fab.setOnClickListener(v -> {
            CreateTaskFragment createTaskFragment = new CreateTaskFragment();
            launchFragment(createTaskFragment);
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportFragmentManager().unregisterFragmentLifecycleCallbacks(fragmentListener);
    }

    public void launchFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void updateUi(){
        Fragment fragment = getCurrentFragment();

        if (fragment instanceof HasCustomTitle){
            binding.toolbar.setTitle(getString(((HasCustomTitle) fragment).getTitlesRes()));
        } else {
            binding.toolbar.setTitle(getString(R.string.all_tusks));
        }

        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } else {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btn_calendar) {
            CalendarTasksFragment calendarTasksFragment = new CalendarTasksFragment();
            launchFragment(calendarTasksFragment);
        }
        if (item.getItemId() == R.id.btn_stats) {
            StatisticsTasksFragment statisticsTasksFragment = new StatisticsTasksFragment();
            launchFragment(statisticsTasksFragment);
        }
        if (item.getItemId() == R.id.btn_setting) {
            SettingsFragment settingsFragment = new SettingsFragment();
            launchFragment(settingsFragment);
        }
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
    public void hideMenuItems() {
        Menu menu = binding.toolbar.getMenu();
        menu.findItem(R.id.btn_calendar).setVisible(false);
        menu.findItem(R.id.btn_stats).setVisible(false);
        menu.findItem(R.id.btn_setting).setVisible(false);
    }
    public void showMenuItems() {
        Menu menu = binding.toolbar.getMenu();
        menu.findItem(R.id.btn_calendar).setVisible(true);
        menu.findItem(R.id.btn_stats).setVisible(true);
        menu.findItem(R.id.btn_setting).setVisible(true);
    }


}


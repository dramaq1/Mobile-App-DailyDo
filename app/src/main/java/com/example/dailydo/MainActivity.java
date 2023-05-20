package com.example.dailydo;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.example.dailydo.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private MenuItem settingsMenuItem;
    private MenuItem statisticsMenuItem;
    private MenuItem calendarMenuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        // Устанавливаем навигацию в ActionBar
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Слушатель изменений навигации для обновления заголовка тулбара
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                setTitle(destination.getLabel());
            }
        });

        if (savedInstanceState == null) {
            navController.navigate(R.id.mainFragment);
        }

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_nav_menu, menu);

        settingsMenuItem = menu.findItem(R.id.btn_setting);
        statisticsMenuItem = menu.findItem(R.id.btn_stats);
        calendarMenuItem = menu.findItem(R.id.btn_calendar);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        updateMenuVisibility(navController.getCurrentDestination().getId());
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Обработка нажатий на элементы меню
        int itemId = item.getItemId();
        if (itemId == R.id.btn_setting) {
            navController.popBackStack(R.id.mainFragment, false);
            navController.navigate(R.id.settingsFragment);
            return true;
        } else if (itemId == R.id.btn_stats) {
            navController.popBackStack(R.id.mainFragment, false);
            navController.navigate(R.id.statisticsTasksFragment);
            return true;
        } else if (itemId == R.id.btn_calendar) {
            navController.popBackStack(R.id.mainFragment, false);
            navController.navigate(R.id.calendarTasksFragment);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateMenuVisibility(int destinationId) {
        if (settingsMenuItem != null && statisticsMenuItem != null && calendarMenuItem != null) {
            if (destinationId == R.id.mainFragment) {
                // Показать элементы меню в mainFragment
                settingsMenuItem.setVisible(true);
                statisticsMenuItem.setVisible(true);
                calendarMenuItem.setVisible(true);
            } else {
                // Скрыть элементы меню в других фрагментах
                settingsMenuItem.setVisible(false);
                statisticsMenuItem.setVisible(false);
                calendarMenuItem.setVisible(false);
            }
        }
        invalidateOptionsMenu();
    }
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

}


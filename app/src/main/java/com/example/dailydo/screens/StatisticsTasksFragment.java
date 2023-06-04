package com.example.dailydo.screens;

import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.dailydo.R;
import com.example.dailydo.databinding.FragmentStatisticsTasksBinding;
import com.example.dailydo.model.Task;
import com.example.dailydo.viewmodel.TaskViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import androidx.core.content.ContextCompat;

public class StatisticsTasksFragment extends Fragment {
    FragmentStatisticsTasksBinding binding;
    private BarChart chart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatisticsTasksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TaskViewModel taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        chart = binding.chart;

        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setPinchZoom(true);
        chart.setScaleEnabled(true);
        chart.getAxisRight().setEnabled(false);

        ArrayList<BarEntry> entries = new ArrayList<>();
        BarDataSet dataSet = new BarDataSet(entries, "Выполненные задачи");
        int colorOrange = ContextCompat.getColor(requireContext(), R.color.orange);
        dataSet.setColor(colorOrange);
        dataSet.setValueTextSize(10f);

        BarData barData = new BarData(dataSet);
        chart.setData(barData);

        taskViewModel.getAllTasks().observe(getViewLifecycleOwner(), tasks -> {
            updateChart(tasks);
            displayCompletedTaskCount(tasks);
        });
    }

    private void updateChart(List<Task> tasks) {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }

        ArrayList<BarEntry> entries = new ArrayList<>();
        int[] completedTaskCount = new int[7];

        List<String> weekDates = getWeekDates();
        for (int i = 0; i < weekDates.size(); i++) {
            String date = weekDates.get(i);
            for (Task task : tasks) {
                if (task.isDone() && isSameDate(task.getDate(), date)) {
                    completedTaskCount[i]++;
                }
            }
            entries.add(new BarEntry(i, completedTaskCount[i]));
        }

        BarDataSet dataSet = (BarDataSet) chart.getData().getDataSetByIndex(0);
        if (dataSet == null) {
            dataSet = new BarDataSet(entries, "Выполненные задачи");
            int colorOrange = ContextCompat.getColor(requireContext(), R.color.orange);
            dataSet.setColor(colorOrange);
            dataSet.setValueTextSize(10f);
        } else {
            dataSet.setValues(entries);
        }

        BarData barData = new BarData(dataSet);
        chart.setData(barData);
        chart.invalidate();

        int maxCount = Arrays.stream(completedTaskCount).max().orElse(0);
        chart.getAxisLeft().setAxisMaximum(maxCount + 1);

        chart.getAxisLeft().setGranularity(1f);
        chart.getAxisLeft().setGranularityEnabled(true);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(weekDates));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
    }

    private void displayCompletedTaskCount(List<Task> tasks) {
        int completedTaskCount = 0;
        for (Task task : tasks) {
            if (task.isDone()) {
                completedTaskCount++;
            }
        }
        binding.completedTaskCount.setText(String.valueOf(completedTaskCount));
    }

    private List<String> getWeekDates() {
        List<String> weekDates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM", Locale.ENGLISH);

        for (int i = 0; i < 7; i++) {
            weekDates.add(dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.DATE, 1);
        }

        return weekDates;
    }

    private boolean isSameDate(Date date1, String date2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM", Locale.ENGLISH);
        String dateString = dateFormat.format(date1);
        return dateString.equals(date2);
    }
}





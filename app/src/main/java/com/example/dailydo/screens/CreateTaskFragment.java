package com.example.dailydo.screens;

import android.app.DatePickerDialog;
import android.graphics.Color;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.fragment.app.FragmentManager;


import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.DatePicker;
import android.widget.Toast;

import com.example.dailydo.R;
import com.example.dailydo.databinding.FragmentCreateTaskBinding;
import com.example.dailydo.model.Task;
import com.example.dailydo.viewmodel.TaskViewModel;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import yuku.ambilwarna.AmbilWarnaDialog;


public class CreateTaskFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    FragmentCreateTaskBinding binding;
    private int selectedColor;
    private Date selectedDate;
    private Date currentDate;
    private TaskViewModel taskViewModel;
    private NavController navController;
    private int selectedIconId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        taskViewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);
        navController = Navigation.findNavController(view);
        setCurrentDate();
        binding.imgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Открытие ColorPicker
                openColorPicker();
            }
        });

        binding.imgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIconPicker();
            }
        });

        binding.dateCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getChildFragmentManager(), "date picker");
            }
        });

        binding.createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.titleEdittext.getText().toString().trim();
                String description = binding.descriptionEdittext.getText().toString().trim();
                // Создание и сохранение задачи
                createAndSaveTask(name, description, selectedColor);
            }
        });
    }

    public void openColorPicker() {
        int defaultColor = R.color.light_yellow;
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(requireContext(), defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                selectedColor = color;
                binding.imgColor.setColorFilter(color);
                binding.iconColor.setColorFilter(color);
            }
        });
        colorPicker.show();
    }

    private void openIconPicker() {
        IconPickerDialogFragment dialogFragment = new IconPickerDialogFragment();
        dialogFragment.setOnIconSelectedListener(new IconPickerDialogFragment.OnIconSelectedListener() {
            @Override
            public void onIconSelected(int iconId) {
                selectedIconId = iconId;
                binding.imgIcon.setImageResource(iconId);

            }
        });
        dialogFragment.show(getChildFragmentManager(), "icon_picker_dialog");
    }

    private void setCurrentDate(){
        Calendar c = Calendar.getInstance();
        currentDate = c.getTime();
        String currentDateText = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        binding.dateTextview.setText(currentDateText);
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        selectedDate = c.getTime();
        String selectedDateText = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        binding.dateTextview.setText(selectedDateText);
    }

    private void createAndSaveTask(String name, String description, int color) {
        // Проверяем, что обязательные поля заполнены
        if (name.isEmpty()) {
            Toast.makeText(requireContext(), "Введите название задачи", Toast.LENGTH_SHORT).show();
            return;
        }

        // Создаем новую задачу
        Task task = new Task(name, description, color, selectedIconId);
        if (selectedDate == null) {
            task.setDate(currentDate);
        } else {
            task.setDate(selectedDate);
        }

        // Сохраняем задачу в базе данных через ViewModel
        taskViewModel.insert(task);
        // Очищаем поля ввода
        binding.titleEdittext.setText("");
        binding.descriptionEdittext.setText("");
        binding.imgColor.setColorFilter(Color.TRANSPARENT);


        // Показываем сообщение об успешном создании задачи
        Toast.makeText(requireContext(), "Задача создана", Toast.LENGTH_SHORT).show();

        // Закрываем текущий фрагмент и переходим на MainActivity
        navController.popBackStack(R.id.mainFragment, false);
        navController.navigate(R.id.mainFragment);

    }
}





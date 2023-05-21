package com.example.dailydo.screens;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dailydo.R;
import com.example.dailydo.databinding.FragmentTaskBinding;
import com.example.dailydo.model.Task;
import com.example.dailydo.viewmodel.TaskViewModel;

import java.text.DateFormat;
import java.util.Date;

public class TaskFragment extends Fragment {
    private NavController navController;
    private FragmentTaskBinding binding;
    private TaskViewModel taskViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        taskViewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);
        navController = Navigation.findNavController(view);


        // Получение данных о задаче из аргументов фрагмента
        Task task = getArguments().getParcelable("task");
        String selectedDateText = DateFormat.getDateInstance(DateFormat.FULL).format(task.getDate().getTime());

        if (task != null) {
            // Установка данных задачи в соответствующие поля макета
            binding.taskNameTextView.setText(task.getName());
            binding.taskDescriptionTextView.setText(task.getDescription());
            binding.iconColor.setColorFilter(task.getColorId());
            binding.imgIcon.setImageResource(task.getIconId());
            binding.taskDate.setText(selectedDateText);
            binding.timeOfDay.setText(task.getTimeOfDay());
        }

        binding.deleteTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskViewModel.delete(task);
                navController.navigate(R.id.mainFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

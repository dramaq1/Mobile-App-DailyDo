package com.example.dailydo.screens;


import android.graphics.Canvas;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dailydo.MainActivity;
import com.example.dailydo.R;
import com.example.dailydo.TaskAdapter;
import com.example.dailydo.databinding.FragmentMainBinding;
import com.example.dailydo.model.Task;
import com.example.dailydo.viewmodel.TaskViewModel;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class MainFragment extends Fragment {

    FragmentMainBinding binding;


    private TaskViewModel taskViewModel;
    private TaskAdapter taskAdapter;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);

        taskAdapter = new TaskAdapter();

        RecyclerView recyclerView = binding.recyclerViewTasks;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(taskAdapter);

        taskViewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                taskAdapter.setTasks(tasks);
            }
        });

        taskAdapter.setOnTaskClickListener(new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onTaskClick(Task task) {
                navController.navigate(R.id.taskFragment);
            }
        });


        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Task task = taskAdapter.getTasks().get(position);

                switch (direction){
                    case ItemTouchHelper.LEFT:
                        // Пометка задачи как выполненной
                        task.setDone(true);
                        // Обновление задачи в базе данных
                        taskViewModel.update(task);
                        // Обновление визуального состояния элемента списка
                        taskAdapter.notifyItemChanged(position);
                        Toast.makeText(requireContext(), "Задача выполнена", Toast.LENGTH_SHORT).show();
                        break;
                    case ItemTouchHelper.RIGHT:
                        task.setDone(false);
                        // Обновление задачи в базе данных
                        taskViewModel.update(task);
                        // Обновление визуального состояния элемента списка
                        taskAdapter.notifyItemChanged(position);
                        Toast.makeText(requireContext(), "Отмена", Toast.LENGTH_SHORT).show();
                        break;
                }

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red_cancel_color))
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_done_color))
                        .addSwipeLeftActionIcon(R.drawable.done)
                        .addSwipeRightActionIcon(R.drawable.delete)
                        .addSwipeLeftLabel("Сделано")
                        .addSwipeRightLabel("Отмена")
                        .addSwipeLeftCornerRadius(10,10)
                        .addSwipeRightCornerRadius(10,10)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        binding.fab.setOnClickListener(v -> {
            navController.navigate(R.id.action_mainFragment_to_createTaskFragment);

        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    }




package com.example.dailydo;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.dailydo.databinding.TaskItemBinding;
import com.example.dailydo.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> tasks = new ArrayList<>();
    private OnTaskClickListener onTaskClickListener;



    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TaskItemBinding binding = TaskItemBinding.inflate(inflater,parent,false);

        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.bind(task);
        holder.itemView.setOnClickListener(v -> {
            if (onTaskClickListener != null) {
                onTaskClickListener.onTaskClick(task);
            }
        });


    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }
    public void setOnTaskClickListener(OnTaskClickListener listener) {
        this.onTaskClickListener = listener;
    }


    public interface OnTaskClickListener {
        void onTaskClick(Task task);
    }


    public List<Task> getTasks() {
        return tasks;
    }

    public void removeTask(int position) {
        tasks.remove(position);
        notifyItemRemoved(position);
    }


    class TaskViewHolder extends RecyclerView.ViewHolder {
        private TaskItemBinding binding;

        TaskViewHolder(@NonNull TaskItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


        void bind(Task task) {
            binding.taskNameTextview.setText(task.getName());
            binding.iconColor.setColorFilter(task.getColorId());
            binding.imgIcon.setImageResource(task.getIconId());
            binding.taskTimeTextview.setText(task.getTimeOfDay());
            if (task.isDone()) {
                binding.taskStatusTextview.setText("Выполнено");
                binding.imgClock.setImageResource(R.drawable.circle_done);

            } else {
                binding.taskStatusTextview.setText("Ожидает выполнения");
                binding.imgClock.setImageResource(R.drawable.clock);
            }

        }
    }
}

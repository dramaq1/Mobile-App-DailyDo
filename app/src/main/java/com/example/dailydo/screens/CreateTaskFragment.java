package com.example.dailydo.screens;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import com.example.dailydo.MainActivity;
import com.example.dailydo.R;
import com.example.dailydo.contract.HasCustomTitle;
import com.example.dailydo.databinding.FragmentCreateTaskBinding;



public class CreateTaskFragment extends Fragment implements HasCustomTitle {

    FragmentCreateTaskBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            ((MainActivity) context).hideMenuItems();
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showMenuItems();
        }
    }

    @Override
    public int getTitlesRes() {
        return R.string.create_tusk;
    }

}
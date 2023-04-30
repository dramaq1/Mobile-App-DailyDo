package com.example.dailydo.screens;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dailydo.MainActivity;
import com.example.dailydo.R;
import com.example.dailydo.contract.HasCustomTitle;

public class SettingsFragment extends Fragment implements HasCustomTitle {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
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
        return R.string.settings;
    }
}
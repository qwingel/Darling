package com.example.darling.Settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.darling.R;

public class SettingsExtensionsFragment extends Fragment {
    Button btn_back;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_back = view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(view1 -> {
            SettingsFragment settingsFragment = new SettingsFragment();
            setNewFragment(settingsFragment);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings_extensions, container, false);
    }

    private void setNewFragment(Fragment fragment){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_settings, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
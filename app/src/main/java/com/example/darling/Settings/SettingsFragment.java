package com.example.darling.Settings;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.darling.AuthActivity;
import com.example.darling.Helpers.SPref;
import com.example.darling.R;

public class SettingsFragment extends Fragment {
    LinearLayout ll_extensions;
    FrameLayout fl_settings;
    SharedPreferences sPref_settings;
    Button btn_logout;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ll_extensions = view.findViewById(R.id.ll_mainSettings);
        fl_settings = view.findViewById(R.id.fl_settings);
        btn_logout = view.findViewById(R.id.btn_logout);

        ll_extensions.setOnClickListener(view1 -> {
            SettingsExtensionsFragment settingsExtensionsFragment = new SettingsExtensionsFragment();
            setNewFragment(settingsExtensionsFragment);
        });

        btn_logout.setOnClickListener(view1 -> {
            sPref_settings = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
            SharedPreferences.Editor edit = sPref_settings.edit();
            edit.clear();
            edit.apply();

            SPref.setAllDataNull();

            Intent intent = new Intent(getActivity().getApplicationContext(), AuthActivity.class);
            startActivity(intent);
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    private void setNewFragment(Fragment fragment){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_settings, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
package com.example.darling.Settings;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.darling.Helpers.SPref;
import com.example.darling.R;

public class SettingsExtensionsFragment extends Fragment {
    Button btn_back;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switch_isOnline;
    TextView tv_screen;
    SharedPreferences ssPref;
    LinearLayout ll_isOnline, ll_defScreen;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ssPref = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);

        switch_isOnline = view.findViewById(R.id.switchBtn_online);
        switch_isOnline.setChecked(SPref.getIsOnline());
        switch_isOnline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SPref.setIsOnline(!SPref.getIsOnline());
                SharedPreferences.Editor edit = ssPref.edit();
                edit.putBoolean("isOnline", SPref.getIsOnline());
                edit.apply();
            }
        });

        ll_isOnline = view.findViewById(R.id.ll_online_status);
        ll_defScreen = view.findViewById(R.id.ll_def_screen);

        ll_isOnline.setOnClickListener(view1 -> switch_isOnline.setChecked(!SPref.getIsOnline()));
        ll_defScreen.setOnClickListener(view1 -> {
            if(SPref.getDefScreen() == 3)
                SPref.setDefScreen(0);
            else
                SPref.setDefScreen(SPref.getDefScreen() + 1);

            tv_screen.setText(getNameOfScreen(SPref.getDefScreen()));

            SharedPreferences.Editor edit = ssPref.edit();
            edit.putInt("screen", SPref.getDefScreen());
            edit.apply();
        });

        tv_screen = view.findViewById(R.id.tv_screen);
        tv_screen.setText(getNameOfScreen(SPref.getDefScreen()));

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
        ft.commit();
    }

    public String getNameOfScreen(int numOfScreen){
        return "< " + new String[]{"Музыка", "Поиск", "Чаты", "Профиль"}[numOfScreen] + " >";
    }
}
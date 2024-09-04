package com.example.darling.Music;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.darling.Messenger.MessengerFragment;
import com.example.darling.R;
import com.example.darling.SettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MusicFragment extends Fragment {
    MusicLibraryFragment musicLibraryFragment = new MusicLibraryFragment();
    MusicSearchFragment musicSearchFragment = new MusicSearchFragment();
    Button btn_settings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setNewFragment(musicLibraryFragment);
        return inflater.inflate(R.layout.fragment_music, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_settings = view.findViewById(R.id.btn_settings);

        btn_settings.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        });

        BottomNavigationView bnm = view.findViewById(R.id.bottom_nav_menu);
        bnm.setSelectedItemId(R.id.bnm_library);
        bnm.setOnItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.bnm_switchToMsg){
                MessengerFragment messengerFragment = new MessengerFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.mainFrameLayout, messengerFragment);
                ft.addToBackStack(null);
                ft.commit();
                return true;
            }

            if (id == R.id.bnm_library){
                setNewFragment(musicLibraryFragment);
                return true;
            }

            if (id == R.id.bnm_musicSearch){
                setNewFragment(musicSearchFragment);
                return true;
            }

            return false;
        });
    }

    private void setNewFragment(Fragment fragment){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_music, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
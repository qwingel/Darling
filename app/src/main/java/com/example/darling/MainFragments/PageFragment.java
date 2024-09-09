package com.example.darling.MainFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.darling.Helpers.SPref;
import com.example.darling.R;
import com.example.darling.SettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class PageFragment extends Fragment {
    Button btn_settings;
    private final int[] screens = {
            R.id.bnm_library,
            R.id.bnm_musicSearch,
            R.id.bnm_messages,
            R.id.bnm_profile
        };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager2 pager = view.findViewById(R.id.pager);
        FragmentStateAdapter stateAdapter = new ViewPagerAdapter(this);
        pager.setAdapter(stateAdapter);

        btn_settings = view.findViewById(R.id.btn_settings);
        btn_settings.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.right_in, R.anim.alpha_with);
        });

        BottomNavigationView bnm = view.findViewById(R.id.bottom_nav_menu);
        bnm.setSelectedItemId(screens[SPref.getDefScreen()]);
        pager.setCurrentItem(SPref.getDefScreen());

        bnm.setOnItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == screens[0]){
                pager.setCurrentItem(0, true);
                return true;
            }

            if (id == screens[1]){
                pager.setCurrentItem(1, true);
                return true;
            }

            if (id == screens[2]){
                pager.setCurrentItem(2, true);
                return true;
            }

            if (id == screens[3]){
                pager.setCurrentItem(3, true);
                return true;
            }
            return false;
        });

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        bnm.setSelectedItemId(R.id.bnm_library);
                        break;

                    case 1:
                        bnm.setSelectedItemId(R.id.bnm_musicSearch);
                        break;

                    case 2:
                        bnm.setSelectedItemId(R.id.bnm_messages);
                        break;

                    case 3:
                        bnm.setSelectedItemId(R.id.bnm_profile);
                        break;
                }
            }
        });
    }
}
package com.example.darling.Login;

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

public class RecoveryFragment extends Fragment {
    Button btn_backToLogin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_recovery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view1, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view1, savedInstanceState);

        btn_backToLogin = view1.findViewById(R.id.btn_backToLogin);

        btn_backToLogin.setOnClickListener(view -> {
            LoginFragment loginFragment = new LoginFragment();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fl_login, loginFragment);
            ft.addToBackStack(null);
            ft.commit();
        });
    }
}
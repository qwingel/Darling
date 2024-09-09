package com.example.darling.MainFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.darling.EditMyInfoActivity;
import com.example.darling.Helpers.SPref;
import com.example.darling.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StockFragment extends Fragment {
    private int pageNumber;
    private View global_View;

    public static StockFragment newInstance(int page){
        StockFragment fragment = new StockFragment();
        Bundle args = new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        global_View = view;
    }

    @Override
    public void onResume() {
        super.onResume();

        int layout = getLayout();
        if(layout == R.layout.fragment_profile){
            onProfileFragmentCreated(global_View);
        }

//        if(layout == R.layout.fragment_messages){ onChatsFragmentCreated(view); }
        if(layout == R.layout.fragment_music_library) {
            onLibraryFragmentCreated(global_View);
        }

//        if(layout == R.layout.fragment_music_search){ onSearchFragmentCreated(view); }
    }

    private int getLayout(){
        switch (pageNumber){
            case 1:
                return R.layout.fragment_music_search;

            case 2:
                return R.layout.fragment_messages;

            case 3:
                return R.layout.fragment_profile;

            default:
                return R.layout.fragment_music_library;
        }
    }

    public void onProfileFragmentCreated(@NonNull View view){
        LinearLayout ll_profilePhoto, ll_newProfilePhoto, ll_editInfo;
        TextView tv_profile_name, tv_profile_username,
                tv_profile_email, tv_profile_description,
                tv_online;

        ll_profilePhoto = view.findViewById(R.id.ll_profile_photo);
        ll_newProfilePhoto = view.findViewById(R.id.ll_new_photo);

        ll_editInfo = view.findViewById(R.id.ll_editInfo);

        tv_profile_name = view.findViewById(R.id.tv_name);
        tv_profile_username = view.findViewById(R.id.tv_profile_username);
        tv_profile_description = view.findViewById(R.id.tv_profile_description);
        tv_profile_email = view.findViewById(R.id.tv_profile_email);
        tv_online = view.findViewById(R.id.tv_online);

        tv_profile_name.setText(SPref.getName());
        tv_profile_username.setText(SPref.getUsername());
        tv_profile_description.setText(SPref.getDescription());
        tv_profile_email.setText(SPref.getEmail());

        if(!SPref.getIsOnline())
            tv_online.setVisibility(View.INVISIBLE);

        ll_editInfo.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), EditMyInfoActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.right_in, R.anim.alpha_with);
        });
    }

    public void onLibraryFragmentCreated(@NonNull View view){
        ListView lv_musicList;
        ArrayList<String> musicList;
    }
}
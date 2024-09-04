package com.example.darling.Messenger;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.darling.R;
import com.example.darling.Helpers.SPref;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;


public class ProfileFragment extends Fragment {
    private static final String PREFS_NAME = "profile_photo";
    SharedPreferences sPref_photo = null;
    LinearLayout ll_profilePhoto, ll_newProfilePhoto;
    Drawable profile_photo;
    Context context;
    Activity activity;
    TextView tv_profile_name, tv_profile_username, tv_profile_email, tv_profile_description;
    private final static int REQUEST_PERMISSION_READ = 3954;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ll_profilePhoto = view.findViewById(R.id.ll_profile_photo);
        ll_newProfilePhoto = view.findViewById(R.id.ll_new_photo);

        context = getActivity().getApplicationContext();
        activity = getActivity();

        tv_profile_name = view.findViewById(R.id.tv_name);
        tv_profile_username = view.findViewById(R.id.tv_profile_username);
        tv_profile_description = view.findViewById(R.id.tv_profile_description);
        tv_profile_email = view.findViewById(R.id.tv_profile_email);

        tv_profile_name.setText(SPref.getName());
        tv_profile_username.setText(SPref.getUsername());
        tv_profile_description.setText(SPref.getDescription());
        tv_profile_email.setText(SPref.getEmail());

        sPref_photo = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String path = sPref_photo.getString("photo", null);

        if (path != null && !path.trim().isEmpty()) {
            System.out.println(path);
            File file = new File(URI.create(path).getPath());
            if (file.exists()) {
                setPhotoBackground(path);
            } else {
                Toast.makeText(
                        context,
                        "Ваше фото удалено",
                        Toast.LENGTH_LONG
                ).show();
            }
        }


        ll_newProfilePhoto.setOnClickListener(view1 -> {
            if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED){
                imageChooser();
            } else {
                ActivityCompat.requestPermissions(activity, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE }, REQUEST_PERMISSION_READ);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSION_READ
            && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            imageChooser();
        }
    }

    void imageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(intent);
    }
    public void setPhotoBackground(String path) {
        Uri imageUri = Uri.parse(path);
        try {
            InputStream imageStream = activity.getContentResolver().openInputStream(imageUri);
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            profile_photo = new BitmapDrawable(getResources(), selectedImage);
            ll_profilePhoto.setBackground(profile_photo);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    ActivityResultLauncher<Intent> launchSomeActivity =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null && data.getData() != null) {
                                Uri selectedImageUri = data.getData();
                                sPref_photo.edit().putString("photo", selectedImageUri.toString()).apply();
                                setPhotoBackground(selectedImageUri.toString());
                            }
                        }
                    }
            );
}
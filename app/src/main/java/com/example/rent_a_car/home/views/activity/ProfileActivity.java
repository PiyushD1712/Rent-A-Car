package com.example.rent_a_car.home.views.activity;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.rent_a_car.auth.model.Users;
import com.example.rent_a_car.auth.viewmodel.AuthViewModel;
import com.example.rent_a_car.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private AuthViewModel model;
    private ActivityProfileBinding binding;
    private FirebaseUser firebaseUser;

    private ActivityResultLauncher<String> mTakePhoto;
    private Uri imgUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(AuthViewModel.class);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        binding.idImageUser.setOnClickListener(v->mTakePhoto.launch("image/*"));

        mTakePhoto = registerForActivityResult(new ActivityResultContracts.GetContent(), o -> imgUri=o);

        binding.idSaveUserButton.setOnClickListener(v->updateUser());

        model.getUserDetails(firebaseUser.getUid()).observe(this, users-> {
                binding.setUser(users);
                Glide.with(getApplicationContext()).load(users.getImgUrl()).into(binding.idImageUser);
        });
    }

    private void updateUser(){
        Users user = new Users();
        user.setId(firebaseUser.getUid());
        user.setImgUrl(imgUri.toString());
        user.setFirstName(binding.idFirstNamePersonal.getText().toString().trim());
        user.setLastName(binding.idLastNamePersonal.getText().toString().trim());
        user.setPhoneNo(binding.idPhonePersonal.getText().toString().trim());
        user.setEmail(binding.idEmailPersonal.getText().toString().trim());
        user.setPassword(binding.idPasswordPersonal.getText().toString().trim());
        model.updateUserDetails(user);
    }
}
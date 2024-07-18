package com.example.rent_a_car.home.views;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.rent_a_car.R;
import com.example.rent_a_car.auth.model.Users;
import com.example.rent_a_car.auth.viewmodel.AuthViewModel;
import com.example.rent_a_car.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfileFragment extends Fragment {

    private AuthViewModel model;
    private FragmentProfileBinding binding;
    private FirebaseUser firebaseUser;

    private ActivityResultLauncher<String> mTakePhoto;
    private Uri imgUri;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        model = new ViewModelProvider(this).get(AuthViewModel.class);
        model.getUserDetails(firebaseUser.getUid()).observe(getViewLifecycleOwner(), new Observer<Users>() {
            @Override
            public void onChanged(Users users) {
                binding.setUser(users);
                Glide.with(getView()).load(users.getImgUrl()).into(binding.idImageUser);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.idImageUser.setOnClickListener(v->mTakePhoto.launch("image/*"));
        mTakePhoto = registerForActivityResult(new ActivityResultContracts.GetContent(),o -> imgUri=o);
        binding.idSaveUserButton.setOnClickListener(v->{
            Users user = new Users();
            user.setId(firebaseUser.getUid());
            user.setImgUrl(imgUri.toString());
            user.setFirstName(binding.idFirstNamePersonal.getText().toString().trim());
            user.setLastName(binding.idLastNamePersonal.getText().toString().trim());
            user.setPhoneNo(binding.idPhonePersonal.getText().toString().trim());
            user.setEmail(binding.idEmailPersonal.getText().toString().trim());
            user.setPassword(binding.idPasswordPersonal.getText().toString().trim());
            model.updateUserDetails(user);
        });
    }

}
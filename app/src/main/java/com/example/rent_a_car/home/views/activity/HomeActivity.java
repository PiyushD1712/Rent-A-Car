package com.example.rent_a_car.home.views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.rent_a_car.R;
import com.example.rent_a_car.auth.viewmodel.AuthViewModel;
import com.example.rent_a_car.databinding.ActivityHomeBinding;
import com.example.rent_a_car.home.views.fragment.HomeFragment;
import com.example.rent_a_car.home.views.fragment.MyCarFragment;
import com.example.rent_a_car.home.views.fragment.PersonalFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AuthViewModel model = new ViewModelProvider(this).get(AuthViewModel.class);
        FirebaseUser firebaseUser  = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            model.getUserDetails(firebaseUser.getUid()).observe(this, users-> {
                    binding.setUser(users);
                    Glide.with(getApplicationContext()).load(users.getImgUrl()).into(binding.idUserImage);
            });
        }
        binding.idUserImage.setOnClickListener(v-> startActivity(new Intent(this,ProfileActivity.class)));
        binding.idBottomNavBar.setOnItemSelectedListener(item->{
            int id = item.getItemId();
            if(id==R.id.idHome){
                replaceFrag(new HomeFragment());
            }
            if (id==R.id.idPersonal) {
                replaceFrag(new PersonalFragment());
            }
            if (id==R.id.idMyCars) {
                replaceFrag(new MyCarFragment());
            }
            return true;
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        replaceFrag(new HomeFragment());
    }
    private void replaceFrag(Fragment fragment){
        FragmentManager fmg = getSupportFragmentManager();
        FragmentTransaction frt = fmg.beginTransaction();
        frt.replace(R.id.idHomeFrame,fragment);
        frt.commit();
    }
}
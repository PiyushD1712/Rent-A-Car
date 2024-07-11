package com.example.rent_a_car.auth.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.rent_a_car.R;
import com.example.rent_a_car.databinding.ActivityAuthBinding;
import com.example.rent_a_car.home.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity {

    private ActivityAuthBinding authBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authBinding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(authBinding.getRoot());
        authBinding.idLoginAuthBtn.setOnClickListener(v->replaceFrag(new LoginFragment()));
        authBinding.idRegAuthBtn.setOnClickListener(v->replaceFrag(new RegisterFragment()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            startActivity(new Intent(this, HomeActivity.class));
        }
        else {
            replaceFrag(new LoginFragment());
        }
    }

    private void replaceFrag(Fragment fragment){
        FragmentManager fmg = getSupportFragmentManager();
        FragmentTransaction frt = fmg.beginTransaction();
        frt.replace(R.id.idFrameLayoutAuth,fragment);
        frt.commit();
    }

}
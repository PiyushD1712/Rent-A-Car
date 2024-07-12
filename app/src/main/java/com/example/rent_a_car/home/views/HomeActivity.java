package com.example.rent_a_car.home.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.rent_a_car.R;
import com.example.rent_a_car.auth.model.Users;
import com.example.rent_a_car.auth.viewmodel.AuthViewModel;
import com.example.rent_a_car.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private AuthViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(AuthViewModel.class);

        binding.idBottomNavBar.setOnItemSelectedListener(item->{
            int id = item.getItemId();
            if(id==R.id.idHome){
                replaceFrag(new HomeFragment());
            } else if (id==R.id.idPersonal) {
                replaceFrag(new PersonalFragment());
            }
            return true;
        });
    }
    private void replaceFrag(Fragment fragment){
        FragmentManager fmg = getSupportFragmentManager();
        FragmentTransaction frt = fmg.beginTransaction();
        frt.replace(R.id.idHomeFrame,fragment);
        frt.commit();
    }
    @Override
    protected void onStart() {
        super.onStart();
        model.getUserDetails(FirebaseAuth.getInstance().getCurrentUser().getUid()).observe(this, new Observer<Users>() {
            @Override
            public void onChanged(Users users) {
                binding.idUserName.setText("Hello, "+users.getLastName());
            }
        });
    }
}
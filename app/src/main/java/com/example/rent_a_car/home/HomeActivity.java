package com.example.rent_a_car.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.rent_a_car.R;
import com.example.rent_a_car.auth.viewmodel.AuthViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {
    private AuthViewModel model;
    private NavigationBarView navBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        model = new ViewModelProvider(this).get(AuthViewModel.class);
        navBar = findViewById(R.id.idBottomNavBar);
        navBar.setOnItemSelectedListener( item-> {
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
}
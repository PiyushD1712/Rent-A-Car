package com.example.rent_a_car.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;

import com.example.rent_a_car.R;
import com.example.rent_a_car.auth.viewmodel.AuthViewModel;

public class HomeActivity extends AppCompatActivity {
    private Button btn;
    private AuthViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn = findViewById(R.id.idLogout);
        model = new ViewModelProvider(this).get(AuthViewModel.class);
        btn.setOnClickListener(v->{
            model.logoutUser();
        });
    }
}
package com.example.rent_a_car.home.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.rent_a_car.R;
import com.example.rent_a_car.auth.model.Users;
import com.example.rent_a_car.auth.viewmodel.AuthViewModel;
import com.example.rent_a_car.databinding.ActivityCarBinding;
import com.example.rent_a_car.home.viewmodel.CarViewModel;

public class CarActivity extends AppCompatActivity {
    private ActivityCarBinding binding;
    private AuthViewModel authModel;
    private CarViewModel carModel;
    private String ownerId,carName,carDesc,imgUrl;
    private int carPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        authModel = new ViewModelProvider(this).get(AuthViewModel.class);
        carModel = new ViewModelProvider(this).get(CarViewModel.class);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            ownerId = bundle.getString("carOwnerId");
            carName = bundle.getString("carName");
            carDesc = bundle.getString("carDesc");
            carPrice = bundle.getInt("carPrice");
            imgUrl = bundle.getString("carImageUrl");
        }
        authModel.getUserDetails(ownerId).observe(this, new Observer<Users>() {
            @Override
            public void onChanged(Users users) {
                String owner = "Owner: "+users.getFirstName()
                        +" "+users.getLastName()+"\nEmail: "+
                        users.getEmail()+"\nPhone: "+users.getPhoneNo();
                binding.idCarOwner.setText(owner);
                binding.idCarName.setText(carName);
                binding.idCarDescription.setText(carDesc);
                binding.idCarPrice.setText(""+carPrice);
                binding.idRentCarBtn.setText("Rent Car: "+carPrice);
                Glide.with(getApplicationContext()).load(imgUrl).into(binding.idImageCar);
            }
        });
    }
}
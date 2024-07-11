package com.example.rent_a_car.auth.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.rent_a_car.auth.model.Users;
import com.example.rent_a_car.auth.repository.AuthRepo;
import com.example.rent_a_car.auth.service.UserRepoService;

public class AuthViewModel extends AndroidViewModel {

    private UserRepoService service;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        service = new UserRepoService(application);
    }

    public void registerUserViaMail(Users users){
        service.registerViaMail(users);
    }

    public void loginUserViaMail(String email,String password){
        service.loginViaMail(email, password);
    }
    public void logoutUser(){
        service.logoutUser();
    }
}

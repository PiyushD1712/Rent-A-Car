package com.example.rent_a_car.auth.service;

import android.content.Context;

import com.example.rent_a_car.auth.model.Users;
import com.example.rent_a_car.auth.repository.AuthRepo;

public class UserRepoService {
    private AuthRepo repo;
    private Context context;

    public UserRepoService(Context context) {
        this.context = context;
        this.repo = new AuthRepo(context);
    }
    public void registerViaMail(Users user){
        repo.registerUserViaMail(user);
    }
    public void loginViaMail(String email,String password){
        repo.loginUserViaMail(email, password);
    }
    public void logoutUser(){
        repo.logoutUser();
    }
}

package com.example.rent_a_car.auth.service;

import android.text.TextUtils;

import com.example.rent_a_car.auth.model.Users;

public class UserService {
    public boolean checkUser(Users user){
        return !TextUtils.isEmpty(user.getFirstName())
                && !TextUtils.isEmpty(user.getLastName())
                && !TextUtils.isEmpty(user.getEmail())
                && !TextUtils.isEmpty(user.getPassword())
                && !TextUtils.isEmpty(user.getPhoneNo());
    }
    public boolean checkMailPassword(String email,String password){
        return !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password);
    }
}

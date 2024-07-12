package com.example.rent_a_car.home.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.rent_a_car.home.model.CarRent;
import com.example.rent_a_car.home.repository.CarRepository;

public class CarViewModel extends AndroidViewModel {
    private CarRepository repository;

    public CarViewModel(Application application) {
        super(application);
        repository = new CarRepository(application);
    }

    public void addCarAll(CarRent carRent){
        repository.addCarAll(carRent);
    }
}

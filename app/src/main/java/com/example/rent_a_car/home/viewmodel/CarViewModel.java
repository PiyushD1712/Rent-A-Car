package com.example.rent_a_car.home.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.rent_a_car.home.model.CarRent;
import com.example.rent_a_car.home.repository.CarRepository;

import org.checkerframework.checker.units.qual.C;

import java.util.List;

public class CarViewModel extends AndroidViewModel {
    private CarRepository repository;

    public CarViewModel(Application application) {
        super(application);
        repository = new CarRepository(application);
    }

    public void addCarAll(CarRent carRent){
        repository.addCarAll(carRent);
    }
    public MutableLiveData<List<CarRent>> showAllCars(){
        return repository.showAllCars();
    }
    public MutableLiveData<List<CarRent>> getPersonalCars(){
        return repository.getPersonalCars();
    }
}

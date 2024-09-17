package com.example.rent_a_car.home.views.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.rent_a_car.auth.model.Users;
import com.example.rent_a_car.auth.viewmodel.AuthViewModel;
import com.example.rent_a_car.databinding.ActivityCarBinding;
import com.example.rent_a_car.home.model.Bookings;
import com.example.rent_a_car.home.viewmodel.CarViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CarActivity extends AppCompatActivity {
    final Calendar calendar = Calendar.getInstance();
    private ActivityCarBinding binding;
    private AuthViewModel authModel;
    private CarViewModel carModel;
    private String startDate,endDate;
    private int numberOfDays =0;
    private String ownerId,carName,carDesc,imgUrl,carId;
    private int carPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        authModel = new ViewModelProvider(this).get(AuthViewModel.class);
        carModel = new ViewModelProvider(this).get(CarViewModel.class);

        DatePickerDialog.OnDateSetListener dateStartListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                numberOfDays = year*365+month*30+dayOfMonth;
                System.out.println(numberOfDays);
                updateStart();
            }
        };
        DatePickerDialog.OnDateSetListener dateEndListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                numberOfDays = year*365+month*30+dayOfMonth- numberOfDays;
                System.out.println(numberOfDays);
                updateEnd();
                int totalPrice = numberOfDays*carPrice;
                String totalFare = "Total Fare: "+totalPrice;
                binding.idTotalFare.setText(totalFare);
                binding.idRentCarBtn.setText(totalFare);
            }
        };
        binding.idStartDate.setOnClickListener(v-> new DatePickerDialog(CarActivity.this,dateStartListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show());
        binding.idEndDate.setOnClickListener(v-> new DatePickerDialog(CarActivity.this,dateEndListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show());

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            carId = bundle.getString("id");
            ownerId = bundle.getString("carOwnerId");
            carName = bundle.getString("carName");
            carDesc = bundle.getString("carDesc");
            carPrice = bundle.getInt("carPrice");
            imgUrl = bundle.getString("carImageUrl");
        }
        authModel.getUserDetails(ownerId).observe(this, new Observer<Users>() {
            @Override
            public void onChanged(Users users) {
                binding.idCarName.setText(carName);
                binding.idCarDescription.setText(carDesc);
                binding.idCarPrice.setText(""+carPrice);
                binding.idRentCarBtn.setText("Rent Car: "+carPrice);
                Glide.with(getApplicationContext()).load(imgUrl).into(binding.idImageCar);
            }
        });
        binding.idRentCarBtn.setOnClickListener(v->{
            Bookings bookings = new Bookings();
            bookings.setCarId(carId);
            bookings.setRenterId(FirebaseAuth.getInstance().getCurrentUser().getUid());
            bookings.setOwnerId(ownerId);
            bookings.setStartDate(startDate);
            bookings.setEndDate(endDate);

            carModel.bookCar(bookings);
        });
    }

    private void updateStart() {
        String dateFormat = "dd/MM/yy";
        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.US);
        startDate = format.format(calendar.getTime());
        binding.idStartDate.setText(startDate);
    }
    private void updateEnd(){
        String dateFormat = "dd/MM/yy";
        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.US);
        endDate = format.format(calendar.getTime());
        binding.idEndDate.setText(endDate);
    }
}
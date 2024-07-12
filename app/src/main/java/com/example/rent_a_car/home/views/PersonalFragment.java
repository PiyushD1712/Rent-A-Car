package com.example.rent_a_car.home.views;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.rent_a_car.R;
import com.example.rent_a_car.databinding.FragmentPersonalBinding;
import com.example.rent_a_car.home.model.CarRent;
import com.example.rent_a_car.home.viewmodel.CarViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class PersonalFragment extends Fragment {
    private CarViewModel model;
    private EditText edCarName,edPrice,edDesc;
    private ImageView imageView;
    private Button btnSave;
    private FirebaseUser firebaseUser;

    private ActivityResultLauncher<String> mTakePhoto;
    private Uri imgUri;

    public PersonalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        model = new ViewModelProvider(this).get(CarViewModel.class);
        edCarName = view.findViewById(R.id.idPersonalCarEd);
        edPrice = view.findViewById(R.id.idPersonalCarPriceEd);
        edDesc = view.findViewById(R.id.idPersonalCarDescEd);
        btnSave = view.findViewById(R.id.idPersonalCarBtn);
        imageView = view.findViewById(R.id.idAddPhoto);

        mTakePhoto = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri o) {
                imgUri = o;
            }
        });

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSave.setOnClickListener(v->saveCar());
        imageView.setOnClickListener(v->mTakePhoto.launch("image/*"));

    }
    private void saveCar(){
        CarRent carRent = new CarRent();
        String carName = edCarName.getText().toString();
        int price = Integer.parseInt(edPrice.getText().toString().trim());
        String desc = edDesc.getText().toString().trim();
        carRent.setCarName(carName);
        carRent.setPrice(price);
        carRent.setImgUrl(imgUri.toString());
        carRent.setDescription(desc);
        carRent.setOwnerId(firebaseUser.getUid());
        model.addCarAll(carRent);
    }
}
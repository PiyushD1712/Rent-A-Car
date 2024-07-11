package com.example.rent_a_car.auth.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.rent_a_car.R;
import com.example.rent_a_car.auth.model.Users;
import com.example.rent_a_car.auth.viewmodel.AuthViewModel;

public class RegisterFragment extends Fragment {
    private AuthViewModel model;
    private Users users;
    private EditText edFirst,edLast,edPhone,edEmail,edPassword;
    public RegisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        model = new ViewModelProvider(this).get(AuthViewModel.class);
        users = new Users();
        edFirst = view.findViewById(R.id.idAuthRegFN);
        edLast = view.findViewById(R.id.idAuthRegLN);
        edPhone = view.findViewById(R.id.idAuthRegPhnNo);
        edEmail = view.findViewById(R.id.idAuthRegMail);
        edPassword = view.findViewById(R.id.idAuthRegPassword);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.idAuthRegBtn).setOnClickListener(v->{
            String firstName = edFirst.getText().toString().trim();
            String lastName = edLast.getText().toString().trim();
            String phone = edPhone.getText().toString().trim();
            String email = edEmail.getText().toString().trim();
            String password = edPassword.getText().toString().trim();
            users.setFirstName(firstName);
            users.setLastName(lastName);
            users.setEmail(email);
            users.setPassword(password);
            users.setPhoneNo(phone);
            model.registerUserViaMail(users);
        });
    }
}
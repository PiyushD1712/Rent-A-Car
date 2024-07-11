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
import com.example.rent_a_car.auth.viewmodel.AuthViewModel;

public class LoginFragment extends Fragment {
    private AuthViewModel model;
    private EditText edMail,edPass;
    private Button logBtn;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        model = new ViewModelProvider(this).get(AuthViewModel.class);
        edMail = view.findViewById(R.id.idAuthLogMail);
        edPass = view.findViewById(R.id.idAuthLogPassword);
        logBtn = view.findViewById(R.id.idAuthLogBtn);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logBtn.setOnClickListener(v->{
            String mail = edMail.getText().toString().trim();
            String pass = edPass.getText().toString().trim();
            model.loginUserViaMail(mail,pass);
        });
    }
}
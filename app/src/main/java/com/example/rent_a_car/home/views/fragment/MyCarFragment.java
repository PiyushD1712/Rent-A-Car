package com.example.rent_a_car.home.views.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rent_a_car.R;
import com.example.rent_a_car.home.adapters.CarListAdapter;
import com.example.rent_a_car.home.model.CarRent;
import com.example.rent_a_car.home.viewmodel.CarViewModel;

import java.util.List;


public class MyCarFragment extends Fragment {
    private CarViewModel model;
    private CarListAdapter adapter;
    private RecyclerView recyclerView;

    public MyCarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(CarViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_my_car, container, false);
        recyclerView = view.findViewById(R.id.idRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        model.getPersonalCars().observe(getViewLifecycleOwner(), new Observer<List<CarRent>>() {
            @Override
            public void onChanged(List<CarRent> carRents) {
                adapter = new CarListAdapter(getContext(),carRents);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }
}
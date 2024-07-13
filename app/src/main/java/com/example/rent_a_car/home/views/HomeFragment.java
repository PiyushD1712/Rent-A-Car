package com.example.rent_a_car.home.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<CarRent> list;
    private CarViewModel model;
    private CarListAdapter adapter;
    private RecyclerView recyclerView;

    public HomeFragment() {
        // Required empty public constructor
        list = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.idRecyclerView);
        model = new ViewModelProvider(this).get(CarViewModel.class);
        model.showAllCars().observe(getViewLifecycleOwner(), new Observer<List<CarRent>>() {
            @Override
            public void onChanged(List<CarRent> carRents) {
                list.addAll(carRents);
                adapter = new CarListAdapter(getContext(),list);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
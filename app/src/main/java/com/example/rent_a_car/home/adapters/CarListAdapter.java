package com.example.rent_a_car.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rent_a_car.R;
import com.example.rent_a_car.databinding.CarListBinding;
import com.example.rent_a_car.home.model.CarRent;
import com.example.rent_a_car.home.viewmodel.CarViewModel;

import java.util.List;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.MyViewHolder> {
    private Context context;
    private List<CarRent> list;

    public CarListAdapter(Context context, List<CarRent> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CarListBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext())
                        , R.layout.car_list
                        , parent
                        ,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CarRent carRent = list.get(position);
        holder.binding.setCar(carRent);
        Glide.with(context).load(carRent.getImgUrl()).into(holder.binding.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private CarListBinding binding;
        public MyViewHolder(CarListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

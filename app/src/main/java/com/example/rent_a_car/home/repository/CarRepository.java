package com.example.rent_a_car.home.repository;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rent_a_car.home.model.Bookings;
import com.example.rent_a_car.home.model.CarRent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CarRepository {
    private final String userCollection="users";
    private final String carCollection="cars";
    private final String bookingCollection="bookings";

    private Context context;
    private FirebaseFirestore firestore;
    private CollectionReference carReference;
    private CollectionReference bookingReference;
    private FirebaseUser firebaseUser;
    private CollectionReference userReference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private StorageReference carImageReference;


    public CarRepository(Context context) {
        this.context = context;
        firestore = FirebaseFirestore.getInstance();

        carReference = firestore.collection(carCollection);
        userReference = firestore.collection(userCollection);
        bookingReference = firestore.collection(bookingCollection);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        carImageReference = storageReference.child("cars").child("image_"+ Timestamp.now().getSeconds());
    }

    //All Cars
    public void addCarAll(@NonNull CarRent carRent){
        carImageReference.putFile(Uri.parse(carRent.getImgUrl()))
                .addOnSuccessListener(taskSnapshot->{
                carImageReference.getDownloadUrl()
                        .addOnSuccessListener(uri-> {
                            String imgUrl = uri.toString();
                            carRent.setImgUrl(imgUrl);
                            carReference.document().set(carRent)
                                    .addOnCompleteListener(task->{
                                        if(task.isSuccessful()){
                                            addCarPersonal(carRent);
                                        }
                                    })
                                    .addOnFailureListener(e->Toast.makeText(context, "Failed due to "+e.getMessage(), Toast.LENGTH_SHORT).show());
                        }).addOnFailureListener(Throwable::printStackTrace);
                })
                .addOnFailureListener(e-> {
                    e.printStackTrace();
                    Log.v("Repo",e.getMessage());});
    }
    public MutableLiveData<List<CarRent>> showAllCars(){
        List<CarRent> list = new ArrayList<>();
        MutableLiveData<List<CarRent>> mutableLiveData = new MutableLiveData<>();
        carReference.get().addOnSuccessListener(queryDocumentSnapshots->{
                for(QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                    CarRent carRent = snapshot.toObject(CarRent.class);
                    carRent.setId(snapshot.getId());
                    updateAllCarId(carRent);
                    list.add(carRent);
                }
                mutableLiveData.postValue(list);
        }).addOnFailureListener(e->{
                e.printStackTrace();
                Toast.makeText(context, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
        });
        return mutableLiveData;
    }
    private void updateAllCarId(@NonNull CarRent carRent){
        carReference.document(carRent.getId()).update("id",carRent.getId())
                .addOnSuccessListener(unused-> Log.d("CAR_REPOSITORY","Car ID ADDED"))
                .addOnFailureListener(e->e.printStackTrace());
    }

    //Personal Car
    private void addCarPersonal(CarRent carRent){
        userReference.document(firebaseUser.getUid()).collection(carCollection).document().set(carRent)
                .addOnCompleteListener(task-> {
                    if(task.isSuccessful()){
                        Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e->Toast.makeText(context, "Failed due to "+e.getMessage(), Toast.LENGTH_SHORT).show());
    }
    public MutableLiveData<List<CarRent>> getPersonalCars(){
        List<CarRent> list = new ArrayList<>();
        MutableLiveData<List<CarRent>> mutableLiveData = new MutableLiveData<>();
        userReference.document(firebaseUser.getUid()).collection(carCollection).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot snapshot: queryDocumentSnapshots){
                    CarRent carRent = snapshot.toObject(CarRent.class);
                    carRent.setId(snapshot.getId());
                    updatePersonalCarId(carRent);
                    list.add(carRent);
                }
                mutableLiveData.postValue(list);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Log.v("MYCARDATABASE","NOT FETCHED "+e.getMessage());
            }
        });
        return mutableLiveData;
    }
    private void updatePersonalCarId(@NonNull CarRent carRent){
        userReference.document(firebaseUser.getUid()).collection(carCollection).document(carRent.getId()).update("id",carRent.getId())
                .addOnSuccessListener(unused -> Log.d("Personal Car","CAR ID UPDATED"))
                .addOnFailureListener(e->e.printStackTrace());
    }

    //Bookings of the Car
    public void bookCar(Bookings bookings){
        bookingReference.document().set(bookings)
                .addOnSuccessListener(unused-> {
                    Toast.makeText(context, "Car booked", Toast.LENGTH_SHORT).show();
                    showBooks();
                })
                .addOnFailureListener(e-> e.printStackTrace());
    }
    public void showBooks(){
        bookingReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot snapshot: queryDocumentSnapshots){
                    Bookings bookings = snapshot.toObject(Bookings.class);
                    bookings.setBookingId(snapshot.getId());
                    updateBookingId(bookings);
                }
            }
        }).addOnFailureListener(e->{
            e.printStackTrace();
            Log.e("Booking","Error");
        });
    }
    private void updateBookingId(@NonNull Bookings bookings){
        bookingReference.document(bookings.getBookingId()).update("bookingId",bookings.getBookingId())
                .addOnSuccessListener(unused->Log.d("BOOKING","ID Updated"))
                .addOnFailureListener(e-> e.printStackTrace());
    }
}


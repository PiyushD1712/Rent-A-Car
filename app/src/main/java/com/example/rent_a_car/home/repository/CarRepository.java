package com.example.rent_a_car.home.repository;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.rent_a_car.home.model.CarRent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CarRepository {
    private final String userCollection="users";
    private final String carCollection="cars";
    private Context context;
    private FirebaseFirestore firestore;
    private CollectionReference carReference;
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
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        carImageReference = storageReference.child("cars").child("image_"+ Timestamp.now().getSeconds());
    }
    
    public void addCarAll(CarRent carRent){
        carImageReference.putFile(Uri.parse(carRent.getImgUrl())).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                carImageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imgUrl = uri.toString();
                        carRent.setImgUrl(imgUrl);
                        carReference.document().set(carRent).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    addCarPersonal(carRent);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Failed due to "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Log.v("Repo",e.getMessage());
            }
        });
    }

    private void addCarPersonal(CarRent carRent){
        userReference.document(firebaseUser.getUid()).collection(carCollection).document().set(carRent).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed due to "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}


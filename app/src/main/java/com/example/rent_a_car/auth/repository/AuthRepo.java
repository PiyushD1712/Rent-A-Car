package com.example.rent_a_car.auth.repository;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.rent_a_car.auth.model.Users;
import com.example.rent_a_car.auth.service.UserService;
import com.example.rent_a_car.auth.view.AuthActivity;
import com.example.rent_a_car.home.views.activity.HomeActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AuthRepo {
    private final String userCollection = "users";
    private final UserService service;
    private final Context context;
    private final FirebaseAuth auth;
    private FirebaseFirestore fireStore;
    private FirebaseUser firebaseUser;
    private CollectionReference collectionReference;
    private FirebaseStorage storage;
    private StorageReference reference;
    private StorageReference userImageReference;

    public AuthRepo(Context context){
        this.context = context;
        auth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        service = new UserService();
        firebaseUser = auth.getCurrentUser();
        collectionReference = fireStore.collection(userCollection);
        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();
        userImageReference = reference.child("users").child("user_image_"+ Timestamp.now().getSeconds());
    }

    public void registerUserViaMail(Users users){
        boolean status = service.checkUser(users);
        if(status){
            auth.createUserWithEmailAndPassword(users.getEmail(),users.getPassword())
                    .addOnCompleteListener(task-> {
                        if(task.isSuccessful()){
                            users.setId(auth.getCurrentUser().getUid());
                            collectionReference.document(auth.getCurrentUser().getUid()).set(users)
                                    .addOnSuccessListener(unused-> {
                                        Toast.makeText(context, "User Created", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(context, HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intent);
                            })
                                    .addOnFailureListener( e-> {
                                        e.printStackTrace();
                                        Toast.makeText(context, "Failed to create user "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                        }
            }).addOnFailureListener( e-> {
                    e.printStackTrace();
                    Toast.makeText(context, "Failed to create User due to "+e.getMessage(), Toast.LENGTH_SHORT).show();

            });
        }
        else{
            Toast.makeText(context, "Please fill out all the fields..", Toast.LENGTH_SHORT).show();
        }
    }

    public void loginUserViaMail(String email,String password){
        boolean status= service.checkMailPassword(email,password);
        if(status){
            auth.signInWithEmailAndPassword(email,password)
                    .addOnSuccessListener(authResult-> {
                        Intent intent =  new Intent(context, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    })
                    .addOnFailureListener(e->Toast.makeText(context, "Failed to login "+e.getMessage(), Toast.LENGTH_SHORT).show());
        }
        else{
            Toast.makeText(context, "Please fill out all the fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void logoutUser(){
        if(firebaseUser!=null){
            auth.signOut();
            Intent intent = new Intent(context, AuthActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public MutableLiveData<Users> getUserDetails(String uid){
        MutableLiveData<Users> liveData = new MutableLiveData<>();
        collectionReference.document(uid).get()
                .addOnSuccessListener(documentSnapshot-> {
                    Users user = documentSnapshot.toObject(Users.class);
                    liveData.postValue(user);
                    Log.v("Database","USER LIVE DATA FETCHED");})
                .addOnFailureListener(e-> {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                    Toast.makeText(context, "Failed due to "+e.getMessage(), Toast.LENGTH_SHORT).show();
                });
        return liveData;
    }

    public void updateUserDetails(Users user){
        boolean status = service.checkUser(user);
        if(status){
            userImageReference.putFile(Uri.parse(user.getImgUrl()))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            userImageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    user.setImgUrl(uri.toString());
                                    Map<String,Object> updates = new HashMap<>();
                                    updates.put("email",user.getEmail());
                                    updates.put("firstName",user.getFirstName());
                                    updates.put("lastName",user.getLastName());
                                    updates.put("password",user.getPassword());
                                    updates.put("phoneNo",user.getPhoneNo());
                                    updates.put("imgUrl",user.getImgUrl());
                                    System.out.print(user.getId());
                                    collectionReference.document(user.getId()).update(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            firebaseUser.updatePassword(user.getPassword())
                                                    .addOnSuccessListener(unused1-> {
                                                        Toast.makeText(context, "User Updated", Toast.LENGTH_SHORT).show();})
                                                    .addOnFailureListener(e->e.getMessage());
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            e.printStackTrace();
                                        }
                                    });
                                }
                            }).addOnFailureListener(e->e.printStackTrace());
                        }
                    })
                    .addOnFailureListener(e->e.printStackTrace());
        }
    }
}
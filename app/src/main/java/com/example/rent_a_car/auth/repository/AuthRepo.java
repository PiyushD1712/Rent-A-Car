package com.example.rent_a_car.auth.repository;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.rent_a_car.MainActivity;
import com.example.rent_a_car.auth.model.Users;
import com.example.rent_a_car.auth.service.UserService;
import com.example.rent_a_car.auth.view.AuthActivity;
import com.example.rent_a_car.home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthRepo {
    private final String userCollection = "users";
    private final UserService service;
    private final Context context;
    private final FirebaseAuth auth;
    private FirebaseFirestore fireStore;
    private FirebaseUser firebaseUser;
    private CollectionReference collectionReference;

    public AuthRepo(Context context){
        this.context = context;
        auth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        service = new UserService();
        firebaseUser = auth.getCurrentUser();
        collectionReference = fireStore.collection(userCollection);
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
}

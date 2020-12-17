package com.example.urclean.firebase;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class FirebaseConnection {

    private static FirebaseConnection singleton;
    private static FirebaseAuth mAuth;
    private static FirebaseFirestore db;
    private QuerySnapshot response;

    public static FirebaseConnection getInstance() {
        if (singleton == null) { // Si no existe conexion establecida
            singleton = new FirebaseConnection();
            mAuth = FirebaseAuth.getInstance();
            db = FirebaseFirestore.getInstance();
            Log.e("SING", "CREADO");
        }
        return singleton; //Devuelve la instancia de la conexion
    }

    public void login(Activity activity, String email, String password, final FirebaseCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        callback.onResponse(true);
                    } else {
                        callback.onResponse(false);
                    }
                });

    }

    public void register(Activity activity, String email, String password, final FirebaseCallback callback) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        callback.onResponse(true);
                    } else {
                        callback.onResponse(false);
                    }
                });

    }

    public void saveUser(String name,String username,String email, String usertype, String telefono,
                         String dni, final FirebaseCallback callback) {
        Map<String,Object> user = new HashMap<>();
        user.put("username",username);
        user.put("email",email);
        user.put("type",usertype);
        user.put("name", name);
        user.put("dni",dni);
        user.put("telefono",telefono);
        user.put("idUser", mAuth.getCurrentUser().getUid());
        db.collection("Persona")
                .add(user)
                .addOnSuccessListener(documentReference -> callback.onResponse(true))
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onResponse(false);
                    }
                });

    }


    public void getTypeUser(final FirebaseCallback callback){
        db.collection("Persona")
                .whereEqualTo("idUser", mAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            response = task.getResult();
                            callback.onResponse(true);
                        } else {
                            callback.onResponse(false);
                        }
                    }
                });
    }


    public FirebaseUser getUser(){
        return mAuth.getCurrentUser();
    }

    public QuerySnapshot getResponse() {
        return response;
    }

    public void logout(final FirebaseCallback callback) {
        mAuth.signOut();
        callback.onResponse(true);
    }


}

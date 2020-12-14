package com.example.urclean.firebase;


/*
    Interfaz que gestiona las callbacks en las llamadas a la base de datos de firebase
 */
public interface FirebaseCallback {
    void onResponse(boolean correct);
}

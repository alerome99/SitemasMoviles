package com.example.urclean;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;

public class menuBarrendero extends AppCompatActivity {

    private FirebaseConnection connection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menubarrendero);
        connection = FirebaseConnection.getInstance();
    }

    public void cerrarSes(View v){

        connection.logout(new FirebaseCallback() {
            @Override
            public void onResponse(boolean correct) {

                if(correct){
                    startActivity(new Intent(menuBarrendero.this, AccesoActivity.class));
                }
            }
        });
    }
}
package com.example.urclean;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.urclean.firebase.FirebaseConnection;

public class ModificarPerfilActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextDni;
    EditText editTextUsername;
    EditText editTextEmail;
    EditText editTextPhone;
    Button buttonConfirmar;
    private FirebaseConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        connection = FirebaseConnection.getInstance();
        editTextName = findViewById(R.id.editTextName);
        editTextDni = findViewById(R.id.editTextDni);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        buttonConfirmar = findViewById(R.id.buttonConfirmar);

        buttonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //coger el usuario y modificar sus campos
            }
        });
    }

}

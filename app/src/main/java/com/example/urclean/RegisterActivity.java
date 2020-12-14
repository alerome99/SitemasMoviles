package com.example.urclean;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.google.android.material.snackbar.Snackbar;


public class RegisterActivity extends AppCompatActivity {

    private Button buttonRegisterFinal, buttonLogin;
    private EditText username, password, password2, name, email;
    private FirebaseConnection connection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        connection = FirebaseConnection.getInstance();
        username = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editTextTextPassword);
        password2 = findViewById(R.id.editTextTextPassword2);
        name = findViewById(R.id.editTextTextName);
        email = findViewById(R.id.editTextTextGmail);
        buttonRegisterFinal=findViewById(R.id.buttonRegisterFinal);
    }

    public void onClick(View w){

        connection.register(RegisterActivity.this, email.getText().toString().trim(), password.getText().toString().trim(), new FirebaseCallback() {
            @Override
            public void onResponse(boolean correct) {

                connection.saveUser(name.getText().toString(),username.getText().toString(),email.getText().toString().trim(),null, new FirebaseCallback() {
                    @Override
                    public void onResponse(boolean correct) {
                        //if (correct) {

                        startActivity(new Intent(RegisterActivity.this, AccesoActivity.class));
                        //} else {
                        //   Snackbar.make(getView(), "No va loco", Snackbar.LENGTH_LONG).show();
                        // }
                    }
                });
               // startActivity(new Intent(RegisterActivity.this, AccesoActivity.class));
            }
        });


    }

}
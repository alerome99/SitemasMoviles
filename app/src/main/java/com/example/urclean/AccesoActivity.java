package com.example.urclean;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class AccesoActivity extends AppCompatActivity {

    private EditText email, password;
    private FirebaseConnection connection;
    private Button buttonRegister, buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso);
        connection = FirebaseConnection.getInstance();
        email  = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editTextTextPassword);

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);

    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonRegister:
                startActivity(new Intent(AccesoActivity.this, RegisterActivity.class));
                break;
            case R.id.buttonLogin:

                connection.login(AccesoActivity.this, email.getText().toString().trim(), password.getText().toString().trim(), new FirebaseCallback() {
                    @Override
                    public void onResponse(boolean correct) {
                        //if (correct) {
                         //   loading.setVisibility(View.INVISIBLE);
                        // rl.setAlpha(1);

                        // Si es true, get user type.
                        //lo cambie DANI
                            startActivity(new Intent( AccesoActivity.this, menuBarrendero.class));
                       // } else {
                        //    loading.setVisibility(View.INVISIBLE);
                          //  rl.setAlpha(1);
                           // Snackbar.make(getView(), "Email o contraseña incorrectos", Snackbar.LENGTH_LONG).show();
                       // }
                    }
                });

                break;
        }


    }

}
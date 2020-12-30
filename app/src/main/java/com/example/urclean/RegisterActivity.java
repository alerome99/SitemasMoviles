package com.example.urclean;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.google.android.material.snackbar.Snackbar;


public class RegisterActivity extends AppCompatActivity {

    private Button buttonRegisterFinal, buttonLogin;
    private EditText username, password, password2, name, email,dni,telefono;
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
        dni = findViewById(R.id.editTextTextDNI);
        telefono = findViewById(R.id.editTextTextTelefono);
        buttonRegisterFinal=findViewById(R.id.buttonRegisterFinal);

        // Controlador de input de email

        email.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s)  {
                if (email.getText().toString().length() <= 0) {
                    email.setError("Inserte el email");
                } else {
                    email.setError(null);
                }
            }
        });

        // Controlador de input de password

        username.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s)  {
                if (username.getText().toString().length() <= 0) {
                    username.setError("Inserte el email");
                } else {
                    username.setError(null);
                }
            }
        });

        // Controlador de password

        password.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s)  {
                if (password.getText().toString().length() <= 0) {
                    password.setError("Inserte el email");
                } else {
                    password.setError(null);
                }
            }
        });

        // Controlador de password 2

        password2.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s)  {
                if (password2.getText().toString().length() <= 0) {
                    password2.setError("Inserte el email");
                } else {
                    password2.setError(null);
                }
            }
        });

        name.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s)  {
                if (name.getText().toString().length() <= 0) {
                    name.setError("Inserte el email");
                } else {
                    name.setError(null);
                }
            }
        });

        //dni

        dni.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s)  {
                if (dni.getText().toString().length() <= 0) {
                    dni.setError("Inserte el email");
                } else {
                    dni.setError(null);
                }
            }
        });

        //telefono

        telefono.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s)  {
                if (telefono.getText().toString().length() <= 0) {
                    telefono.setError("Inserte el email");
                } else {
                    telefono.setError(null);
                }
            }
        });

    }


    public void onClick(View w){

        testErrorInput();
        if(email.getText().toString().trim().length() != 0 && password.getText().toString().trim().length() != 0 &&
           password2.getText().toString().trim().length() != 0 && name.getText().toString().trim().length() != 0 &&
           username.getText().toString().trim().length() != 0 && dni.getText().toString().trim().length() != 0
        && telefono.getText().toString().trim().length() != 0)
        {
            connection.register(RegisterActivity.this, email.getText().toString().trim(), password.getText().toString().trim(), correct -> {
                if (correct) {
                    connection.saveUser(name.getText().toString(), username.getText().toString().trim(), email.getText().toString().trim(),
                            "ciudadano", telefono.getText().toString().trim(), dni.getText().toString(), new FirebaseCallback() {
                        @Override
                        public void onResponse(boolean correct) {
                            if (correct) {
                                startActivity(new Intent(RegisterActivity.this, AccesoActivity.class));
                            } else {
                                Log.e("SAVE", "Respuesta vacia");
                                Snackbar.make(w, "Error al almacenar los datos", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }); // fin save user
                } else {
                    Log.e("REGISTER", "Respuesta vacia");
                    Log.e("REGE", "" + email.getText().toString().trim());
                    Log.e("REGP", "" + password.getText().toString().trim());
                    Snackbar.make(w, "Se ha producido un error en el registro", Snackbar.LENGTH_LONG).show();
                }
            }); // fin register

        } //fin comprobacion
    }

    private void testErrorInput(){

        if(email.getText().toString().trim().length() == 0 ){
            email.setError("Inserte el email");
        }

        if(password.getText().toString().trim().length() == 0 ){
            password.setError("Inserte la contraseña");
        }

        if(password2.getText().toString().trim().length() == 0 ){
            password2.setError("Inserte la contraseña");
        }

        if(name.getText().toString().trim().length() == 0 ){
            name.setError("Inserte el nombre y apellidos");
        }
        if(username.getText().toString().trim().length() == 0){
            username.setError("Inserte el nombre de usuario");
        }
        if(dni.getText().toString().trim().length() == 0){
            dni.setError("Inserte el nombre de usuario");
        }
        if(telefono.getText().toString().trim().length() == 0){
           telefono.setError("Inserte el nombre de usuario");
        }

    }

}
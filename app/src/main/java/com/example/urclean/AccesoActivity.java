package com.example.urclean;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QueryDocumentSnapshot;


public class AccesoActivity extends AppCompatActivity {

    private EditText email, password;
    private FirebaseConnection connection;
    private Button buttonRegister, buttonLogin;
    private String typeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso);
        connection = FirebaseConnection.getInstance();
        email  = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editTextTextPassword);
        typeUser="";
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);


        //Controlador de error de email

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

        // controlar

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

    }


    /*

    Switch en funcion del boton pulsado.

    -Para login establecer la vista incial si se cumplen los requisitos de este.
    -Falta establecer campos de color de error, y alguna cosa mas. Firebase ya hace la
    comprobacion de usuarios, y de demas cosas.

    Funcion login comprueba el email en la db.
    Si el login es correcto ejecuta la funcion que trae el tipo de usuario.


     */

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonRegister:
                startActivity(new Intent(AccesoActivity.this, RegisterActivity.class));
                break;
            case R.id.buttonLogin:

                if(email.getText().toString().trim().length() == 0){
                    email.setError("Inserte el email");
                }
                if(password.getText().toString().trim().length() == 0){
                    password.setError("Inserte la contraseña");
                }

                connection.login(AccesoActivity.this, email.getText().toString().trim(), password.getText().toString().trim(), new FirebaseCallback() {
                    @Override
                    public void onResponse(boolean correct) {
                        if (correct) {
                            connection.getTypeUser(new FirebaseCallback() {
                                @Override
                                public void onResponse(boolean correct) {
                                    if (correct) {
                                        if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                                             Snackbar.make(v, "No existen datos", Snackbar.LENGTH_LONG).show();
                                        } else {
                                            for (QueryDocumentSnapshot document : connection.getResponse()) {
                                                typeUser = (String) document.get("type");
                                                Log.e("NAME",""+(String) document.get("name"));
                                                Log.e("NAME",""+(String) document.get("type"));
                                            }

                                            if (typeUser.equals("ciudadano")) {

                                                startActivity(new Intent( AccesoActivity.this, MenuCiudadanoActivity.class));

                                            } else if (typeUser.equals("barrendero")){

                                                startActivity(new Intent( AccesoActivity.this, menuBarrendero.class));

                                            } else if (typeUser.equals("supervisor")){

                                                startActivity(new Intent( AccesoActivity.this, MenuSupervisorActivity.class));

                                            }else{

                                                Log.e("USERTYPE","No user type selected.");

                                            }
                                        }
                                    } else {
                                        Snackbar.make(v, "No se pueden recuperar los datos", Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            });

                       } else { // Bad login
                            Snackbar.make(v, "Email o contraseña incorrectos", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
                break;
        }
    }
}


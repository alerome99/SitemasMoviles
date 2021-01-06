package com.example.urclean;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;


public class RegisterActivity extends AppCompatActivity {

    private Button buttonRegisterFinal, buttonLogin;
    private EditText username, password, password2, name, email,dni,telefono;
    private FirebaseConnection connection;
    private Button btnCalendario;
    private Calendar c;
    private DatePickerDialog dpd;
    private TextView txtFecha;


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
        btnCalendario = findViewById(R.id.buttonFechaNac);
        txtFecha = findViewById(R.id.textViewFechaNac);

        // Para abrir calendario

        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c= Calendar.getInstance();
                int dia = c.get(Calendar.DAY_OF_MONTH);
                int mes = c.get(Calendar.MONTH);
                int anio = c.get(Calendar.YEAR);
                dpd = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        txtFecha.setText(dayOfMonth+ "/" +month+ "/" +year);
                    }
                },anio,mes,dia);
                dpd.show();
            }
        });
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
                if (password.getText().toString().length() <= 5) {
                    password.setError("Inserte la contraseña con 6 caracteres");
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
                if (password2.getText().toString().length() <= 5) {
                    password2.setError("Inserte la contraseña con 6 caracteres");
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
                    name.setError("Inserte el nombre");
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
                    dni.setError("Inserte el dni");
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
                    telefono.setError("Inserte el telefono");
                } else {
                    telefono.setError(null);
                }
            }
        });

    }


    public void onClick(View w){

        testErrorInput();
        if(email.getText().toString().trim().length() != 0 && password.getText().toString().trim().length() > 5 &&
           password2.getText().toString().trim().length() > 5 && name.getText().toString().trim().length() != 0 &&
           username.getText().toString().trim().length() != 0 && dni.getText().toString().trim().length() != 0
        && telefono.getText().toString().trim().length() != 0
                && (password.getText().toString().trim().equals(password2.getText().toString().trim())))
        {
            connection.register(RegisterActivity.this, email.getText().toString().trim(),
                    password.getText().toString().trim(), correct -> {
                if (correct) {
                    connection.saveUser(name.getText().toString(), username.getText().toString().trim(), email.getText().toString().trim(),
                            "ciudadano", telefono.getText().toString().trim(), dni.getText().toString(), txtFecha.getText().toString(), new FirebaseCallback() {
                        @Override
                        public void onResponse(boolean correct) {
                            if (correct) {
                                startActivity(new Intent(RegisterActivity.this, AccesoActivity.class));
                            } else {
                                Log.e("SAVE", "Respuesta vacia saveUser");
                                Snackbar.make(w, "Error al almacenar los datos", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }); // fin save user
                } else {
                    Log.e("REGISTER", "Respuesta vacia register");
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

        if(!password.getText().toString().trim().equals(password2.getText().toString().trim())){
            password.setError("No coinciden las contraseñas");
            password2.setError("No coinciden las contraseñas");
        }

        if(password.getText().toString().trim().length() < 5 ){
            password.setError("Inserte la contraseña con 6 caracteres minimo");
        }

        if(password2.getText().toString().trim().length() < 5 ){
            password2.setError("Inserte la contraseña  6 caracteres minimo");
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
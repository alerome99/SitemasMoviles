package com.example.urclean;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class detallesUsuario extends AppCompatActivity {
    Button buttonRegistrarBarrendero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra_informacion_usuario);
        buttonRegistrarBarrendero = findViewById(R.id.buttonRegistrarBarrendero);
        Intent intent = getIntent();
        Bundle bu = intent.getExtras();
        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextPhone = findViewById(R.id.editTextPhone);
        EditText editTextEmail = findViewById(R.id.editTextEmail);

        if(bu!=null) {
            editTextName.setText(bu.getString("NAME"));
            editTextEmail.setText(bu.getString("EMAIL"));
            editTextPhone.setText(bu.getString("PHONE"));
        }

        buttonRegistrarBarrendero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //modificar el campo tipo del usuario a barrendero
            }
        });
    }


}

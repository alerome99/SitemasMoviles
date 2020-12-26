package com.example.urclean;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class detallesBarrendero extends AppCompatActivity {
    Button buttonGrupo;
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra_informacion_barrendero);
        buttonGrupo = findViewById(R.id.buttonGrupo);
        Intent intent = getIntent();
        Bundle bu = intent.getExtras();
        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextPhone = findViewById(R.id.editTextPhone);
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        if(bu!=null) {
            editTextName.setText(bu.getString("NAME"));
            editTextEmail.setText(bu.getString("EMAIL"));
            editTextPhone.setText(bu.getString("PHONE"));
        }

        navigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case (R.id.navigation_home):
                        startActivity(new Intent(detallesBarrendero.this, Perfil_v2.class));
                        break;
                    case (R.id.navigation_incidencia_ciudadano):
                        startActivity(new Intent(detallesBarrendero.this, ListaUsuariosActivity.class));
                        //Ir a Incidencia
                        break;
                    case (R.id.navigation_list_ciudadano):
                        startActivity(new Intent(detallesBarrendero.this, ListaBarrenderosActivity.class));
                        //Ir a...?
                        break;
                    case (R.id.navigation_notifications):
                        //Ir a lista de notificaciones
                        break;
                }
            }
        });

        buttonGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mostrar toodos los grupos para que se pueda añadir a uno
            }
        });
    }


}

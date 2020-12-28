package com.example.urclean;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.urclean.firebase.FirebaseConnection;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class detallesBarrendero extends AppCompatActivity {
    Button buttonGrupo;
    BottomNavigationView navigation;
    Spinner spinner;
    private FirebaseConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra_informacion_barrendero);
        buttonGrupo = findViewById(R.id.buttonGrupo);
        Intent intent = getIntent();
        connection = FirebaseConnection.getInstance();
        Bundle bu = intent.getExtras();
        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextPhone = findViewById(R.id.editTextPhone);
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayList<String> listaGrupos = new ArrayList<>();
        connection.getGrupos(correct -> {
            if (correct) {
                if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                } else {
                    int i = 0;
                    for (QueryDocumentSnapshot document : connection.getResponse()) {
                        listaGrupos.add((String) document.get("numero"));
                    }
                    ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_dropdown_item, listaGrupos);
                    spinner.setAdapter(adaptador);
                }
            }
        });
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
                        startActivity(new Intent(detallesBarrendero.this, AddGrupoActivity.class));
                        //Ir a lista de notificaciones
                        break;
                }
            }
        });

        buttonGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //añadir una persona al grupo que este
                spinner.getSelectedItem().toString();
            }
        });
    }


}

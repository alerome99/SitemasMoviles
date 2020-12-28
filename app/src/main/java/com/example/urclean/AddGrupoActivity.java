package com.example.urclean;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class AddGrupoActivity extends AppCompatActivity{

    EditText editTextTextNumeroGrupo;
    BottomNavigationView navigation;
    Button buttonAddGrupo;
    private FirebaseConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_add_grupo);
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        editTextTextNumeroGrupo = findViewById(R.id.editTextTextNumeroGrupo);
        //Añadir grupo
        buttonAddGrupo = findViewById(R.id.buttonAddGrupo);
        connection = FirebaseConnection.getInstance();

        navigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case (R.id.navigation_home):
                        startActivity(new Intent(AddGrupoActivity.this, Perfil_v2.class));
                        break;
                    case (R.id.navigation_incidencia_ciudadano):
                        startActivity(new Intent(AddGrupoActivity.this, ListaUsuariosActivity.class));
                        //Ir a Incidencia
                        break;
                    case (R.id.navigation_list_ciudadano):
                        startActivity(new Intent(AddGrupoActivity.this, ListaBarrenderosActivity.class));
                        //Ir a...?
                        break;
                    case (R.id.navigation_notifications):
                        startActivity(new Intent(AddGrupoActivity.this, AddGrupoActivity.class));
                        //Ir a lista de notificaciones
                        break;
                }
            }
        });

        buttonAddGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(editTextTextNumeroGrupo.getText().toString() + "holaaaaaaaaaaaaaaaaaaaaaaa");
                connection.saveGrupo(editTextTextNumeroGrupo.getText().toString() , new FirebaseCallback() {
                    @Override
                    public void onResponse(boolean correct) {
                        if (correct) {
                            startActivity(new Intent(AddGrupoActivity.this, AddGrupoActivity.class));
                        } else {
                            Log.e("SAVE", "Respuesta vacia");
                            Snackbar.make(v, "Error al almacenar los datos", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }); // fin save use
            }
        });

        super.onCreate(savedInstanceState);
    }
}

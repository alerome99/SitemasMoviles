package com.example.urclean;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuSupervisorActivity extends AppCompatActivity {

    BottomNavigationView navigation;
    Button Logoutsupervisor;

    private FirebaseConnection connection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menubarrendero);
        connection = FirebaseConnection.getInstance();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        Logoutsupervisor = findViewById(R.id.Logoutsupervisor);

        navigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case (R.id.navigation_home):
                        startActivity(new Intent(MenuSupervisorActivity.this, Perfil_v2.class));
                        break;
                    case (R.id.navigation_incidencia_ciudadano):
                        startActivity(new Intent(MenuSupervisorActivity.this, ListaUsuariosActivity.class));
                        //Ir a Incidencia
                        break;
                    case (R.id.navigation_list_ciudadano):
                        //Ir a...?
                        break;
                    case (R.id.navigation_notifications):
                        //Ir a lista de notificaciones
                        break;
                    case (R.id.navigation_tareas):
                        //Vista tareas
                        break;
                }
            }
        });
        /*
        Logoutsupervisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuSupervisorActivity.this, AccesoActivity.class));
                //coger el usuario de la base de datos, modificar sus campos en local y cambiarlo en el firebase
            }
        });*/
    }

    public void cerrarSes(View v){

        connection.logout(new FirebaseCallback() {
            @Override
            public void onResponse(boolean correct) {

                if(correct){
                    startActivity(new Intent(MenuSupervisorActivity.this, AccesoActivity.class));
                }
            }
        });

    }
}

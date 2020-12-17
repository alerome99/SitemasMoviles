package com.example.urclean;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;


import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.urclean.firebase.FirebaseCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;

public class menuBarrendero extends AppCompatActivity  {

    BottomNavigationView navigation;



    private FirebaseConnection connection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menubarrendero);
        connection = FirebaseConnection.getInstance();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        Log.e("TAREA", "antes de barra");
        navigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                Log.e("TAREA", "despues de barra");
                switch(item.getItemId()){
                    case (R.id.navigation_home):
                        startActivity(new Intent(menuBarrendero.this, Perfil.class));
                        break;
                    case (R.id.navigation_incidencia_ciudadano):
                        startActivity (new Intent(menuBarrendero.this, IncidenciaActivity.class));

                        break;
                    case (R.id.navigation_list_ciudadano):
                        //Ir a...?
                        break;
                    case (R.id.navigation_notifications):
                        //Ir a lista de notificaciones
                        break;
                    case (R.id.navigation_tareas):
                        startActivity (new Intent(menuBarrendero.this, tareasBarrendero.class));
                        break;

                }

            }
        });




    }

    public void cerrarSes(View v){

        connection.logout(new FirebaseCallback() {
            @Override
            public void onResponse(boolean correct) {

                if(correct){
                    startActivity(new Intent(menuBarrendero.this, AccesoActivity.class));
                }
            }
        });

    }
}
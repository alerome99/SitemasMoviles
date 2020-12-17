package com.example.urclean;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuSupervisorActivity extends AppCompatActivity {

    BottomNavigationView navigation;



    private FirebaseConnection connection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menubarrendero);
        connection = FirebaseConnection.getInstance();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        navigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case (R.id.navigation_home):
                        startActivity(new Intent(MenuSupervisorActivity.this, Perfil_v2.class));
                        break;
                    case (R.id.navigation_incidencia_ciudadano):
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

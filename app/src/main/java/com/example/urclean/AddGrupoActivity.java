package com.example.urclean;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddGrupoActivity extends AppCompatActivity{

    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_add_grupo);
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        //Añadir grupo

        navigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
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
        super.onCreate(savedInstanceState);
    }
}

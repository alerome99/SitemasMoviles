package com.example.urclean;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.fragments.AddGrupoFragment;
import com.example.urclean.fragments.IncidenciaCiudadanoFragment;
import com.example.urclean.fragments.ListaBarrenderosFragment;
import com.example.urclean.fragments.ListaTareasFragment;
import com.example.urclean.fragments.ListaUsuariosFragment;
import com.example.urclean.fragments.MenuBarrenderoFragment;
import com.example.urclean.fragments.MenuSupervisorFragment;
import com.example.urclean.fragments.NotificacionesCiudadanoFragment;
import com.example.urclean.fragments.PerfilCiudadanoFragment;
import com.example.urclean.fragments.PerfilFragment;
import com.example.urclean.fragments.QuejasFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.urclean.firebase.FirebaseConnection;

public class MenuSupervisorActivity extends AppCompatActivity  {

    BottomNavigationView navigation;

    private FirebaseConnection connection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menusupervisor);
        connection = FirebaseConnection.getInstance();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(navListener);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new PerfilCiudadanoFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_perfil:
                            selectedFragment = new PerfilCiudadanoFragment();
                            break;
                        case R.id.nav_incidencias:
                            selectedFragment = new IncidenciaCiudadanoFragment();
                            break;
                        case R.id.nav_menu_principal:
                            selectedFragment = new MenuSupervisorFragment();
                            break;
                        case R.id.nav_notificaciones:
                            //selectedFragment = new AddGrupoFragment();
                            selectedFragment = new NotificacionesCiudadanoFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}
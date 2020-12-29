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
import com.example.urclean.fragments.IncidenciaCiudadanoFragment;
import com.example.urclean.fragments.ListaTareasFragment;
import com.example.urclean.fragments.MenuBarrenderoFragment;
import com.example.urclean.fragments.NotificacionesCiudadanoFragment;
import com.example.urclean.fragments.PerfilCiudadanoFragment;
import com.example.urclean.fragments.PerfilFragment;
import com.example.urclean.fragments.QuejasFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.urclean.firebase.FirebaseConnection;

public class menuBarrendero extends AppCompatActivity  {

    BottomNavigationView navigation;

    private FirebaseConnection connection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menubarrendero);
        connection = FirebaseConnection.getInstance();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation_barrendero);
        navigation.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.containerFragmentBarrendero,
                    new MenuBarrenderoFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            selectedFragment = new PerfilFragment();
                            break;
                        case R.id.navigation_tareas:
                            selectedFragment = new ListaTareasFragment();
                            break;
                        case R.id.navigation_list_ciudadano:
                            selectedFragment = new MenuBarrenderoFragment();
                            break;
                        case R.id.navigation_notifications:
                            //selectedFragment = new NotificacionesCiudadanoFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.containerFragmentBarrendero,
                            selectedFragment).commit();

                    return true;
                }
            };
}
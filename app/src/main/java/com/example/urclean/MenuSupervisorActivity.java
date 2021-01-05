package com.example.urclean;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.fragments.IncidenciaCiudadanoFragment;
import com.example.urclean.fragments.ListaNotificacionesSupervisorFragment;
import com.example.urclean.fragments.MenuSupervisorFragment;
import com.example.urclean.fragments.PerfilCiudadanoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuSupervisorActivity extends AppCompatActivity  {

    BottomNavigationView navigation;

    private FirebaseConnection connection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menusupervisor);
        connection = FirebaseConnection.getInstance();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setSelectedItemId(R.id.nav_menu_principal);
        navigation.setOnNavigationItemSelectedListener(navListener);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MenuSupervisorFragment()).commit();
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
                            selectedFragment = new ListaNotificacionesSupervisorFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).addToBackStack(null).commit();

                    return true;
                }
            };
}
package com.example.urclean;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.urclean.fragments.IncidenciaCiudadanoFragment;
import com.example.urclean.fragments.NotificacionesCiudadanoFragment;
import com.example.urclean.fragments.PerfilCiudadanoFragment;
import com.example.urclean.fragments.QuejasFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuCiudadanoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuciudadano);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_barrendero);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
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
                        case R.id.nav_quejas:
                            selectedFragment = new QuejasFragment();
                            break;
                        case R.id.nav_notificaciones:
                            selectedFragment = new NotificacionesCiudadanoFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}
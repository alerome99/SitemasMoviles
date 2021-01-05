package com.example.urclean;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.urclean.fragments.IncidenciaCiudadanoFragment;
import com.example.urclean.fragments.MenuCiudadanoFragment;
import com.example.urclean.fragments.NotificacionesCiudadanoFragment;
import com.example.urclean.fragments.PerfilCiudadanoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuCiudadanoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuciudadano);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_ciudadano);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            String dir = bundle.getString("dir");
            String cod = bundle.getString("cod");
            String tipo = bundle.getString("tipo");
            String asunto = bundle.getString("asunto");
            String descripcion = bundle.getString("descripcion");

            Bundle args = new Bundle();
            args.putString("dir", dir);
            args.putString("cod", cod);
            args.putString("tipo", tipo);
            args.putString("asunto", asunto);
            args.putString("descripcion", descripcion);

            IncidenciaCiudadanoFragment fragment = new IncidenciaCiudadanoFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    fragment).commit();
        }else if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MenuCiudadanoFragment()).commit();
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
                            selectedFragment = new MenuCiudadanoFragment();
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
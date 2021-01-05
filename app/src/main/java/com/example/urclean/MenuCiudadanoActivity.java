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
            String usuario = bundle.getString("usuario");

            Bundle args = new Bundle();
            args.putString("dir", dir);
            args.putString("cod", cod);
            args.putString("tipo", tipo);
            args.putString("asunto", asunto);
            args.putString("descripcion", descripcion);
            args.putString("usuario", usuario);

            IncidenciaCiudadanoFragment fragment = new IncidenciaCiudadanoFragment();
            fragment.setArguments(args);

            bottomNav.setSelectedItemId(R.id.nav_incidencias);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    fragment).addToBackStack(null).commit();
        }else if (savedInstanceState == null) {
            bottomNav.setSelectedItemId(R.id.nav_menu);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MenuCiudadanoFragment()).addToBackStack(null).commit();
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
                            Bundle b = new Bundle();
                            b.putString("usuario","ciudadano");
                            selectedFragment = new IncidenciaCiudadanoFragment();
                            selectedFragment.setArguments(b);
                            break;
                        case R.id.nav_menu:
                            selectedFragment = new MenuCiudadanoFragment();
                            break;
                        case R.id.nav_notificaciones:
                            selectedFragment = new NotificacionesCiudadanoFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).addToBackStack(null).commit();

                    return true;
                }
            };
}
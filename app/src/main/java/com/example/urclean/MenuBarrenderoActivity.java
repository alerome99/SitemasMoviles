package com.example.urclean;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.fragments.IncidenciaCiudadanoFragment;
import com.example.urclean.fragments.ListaNotificacionesBarrenderoFragment;
import com.example.urclean.fragments.ListaTareasFragment;
import com.example.urclean.fragments.MenuBarrenderoFragment;
import com.example.urclean.fragments.PerfilCiudadanoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuBarrenderoActivity extends AppCompatActivity  {

    BottomNavigationView navigation;

    private FirebaseConnection connection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menubarrendero);
        connection = FirebaseConnection.getInstance();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation_barrendero);
        navigation.setOnNavigationItemSelectedListener(navListener);

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

            navigation.setSelectedItemId(R.id.navigation_list_ciudadano);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    fragment).addToBackStack(null).commit();
        }else if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.navigation_list_ciudadano);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
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
                            selectedFragment = new PerfilCiudadanoFragment();
                            break;
                        case R.id.navigation_tareas:
                            selectedFragment = new ListaTareasFragment();
                            break;

                        case R.id.navigation_incidencia_ciudadano:
                            Bundle b = new Bundle();
                            b.putString("usuario","barrendero");
                            selectedFragment = new IncidenciaCiudadanoFragment();
                            selectedFragment.setArguments(b);
                            break;

                        case R.id.navigation_list_ciudadano:
                            selectedFragment = new MenuBarrenderoFragment();
                            break;
                        case R.id.navigation_notifications:
                            selectedFragment = new ListaNotificacionesBarrenderoFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).addToBackStack(null).commit();

                    return true;
                }
            };

}
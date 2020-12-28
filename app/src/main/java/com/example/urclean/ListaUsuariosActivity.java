package com.example.urclean;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.urclean.firebase.FirebaseConnection;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

public class ListaUsuariosActivity extends AppCompatActivity {

    BottomNavigationView navigation;
    private ListView lvLista;
    String[] datos;
    String[][] extra;
    String[] prueba = {"Interestelar", "Christopher Nolan"};
    private ArrayAdapter<String> adaptador;

    private FirebaseConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        lvLista = findViewById(R.id.lvLista);
        connection = FirebaseConnection.getInstance();
        connection.getUsuarios(correct -> {
            if (correct) {
                if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                } else {
                    int i = 0;
                    datos = new String [connection.getResponse().size()];
                    extra = new String [connection.getResponse().size()][3];
                    for (QueryDocumentSnapshot document : connection.getResponse()) {
                        datos[i] = (String) document.get("username");
                        extra[i][0] = (String) document.get("name");
                        extra[i][1] = (String) document.get("email");
                        extra[i][2] = (String) document.get("telefono");
                        i++;
                    }
                    adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,datos);
                    lvLista.setAdapter(adaptador);
                    lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent detalles = new Intent(view.getContext(), detallesUsuario.class);
                            detalles.putExtra("NAME", extra[position][0]);
                            detalles.putExtra("EMAIL", extra[position][1]);
                            detalles.putExtra("PHONE", extra[position][2]);
                            startActivity(detalles);
                        }
                    });
                }
            }
        });
        navigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case (R.id.navigation_home):
                        startActivity(new Intent(ListaUsuariosActivity.this, Perfil_v2.class));
                        break;
                    case (R.id.navigation_incidencia_ciudadano):
                        startActivity(new Intent(ListaUsuariosActivity.this, ListaUsuariosActivity.class));
                        //Ir a Incidencia
                        break;
                    case (R.id.navigation_list_ciudadano):
                        startActivity(new Intent(ListaUsuariosActivity.this, ListaBarrenderosActivity.class));
                        //Ir a...?
                        break;
                    case (R.id.navigation_notifications):
                        startActivity(new Intent(ListaUsuariosActivity.this, AddGrupoActivity.class));
                        //Ir a lista de notificaciones
                        break;
                }
            }
        });
        //adaptador = new Adaptador_lista(this, prueba);
        //lvLista.setAdapter(adaptador);
    }
}

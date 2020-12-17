package com.example.urclean;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.urclean.firebase.FirebaseConnection;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

public class ListaUsuariosActivity extends AppCompatActivity {

    private ListView lvLista;
    String[] datos;
    String[] prueba = {"Interestelar", "Christopher Nolan"};
    private ArrayAdapter<String> adaptador;

    private FirebaseConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);
        lvLista = findViewById(R.id.lvLista);
        connection = FirebaseConnection.getInstance();
        connection.getUsuarios(correct -> {
            if (correct) {
                if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                } else {
                    int i = 0;
                    datos = new String [connection.getResponse().size()];
                    for (QueryDocumentSnapshot document : connection.getResponse()) {
                        datos[i] = (String) document.get("username");
                        i++;
                    }
                    adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,datos);
                    lvLista.setAdapter(adaptador);
                    lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            System.out.println("pruebaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                        }
                    });
                }
            }
        });
        //adaptador = new Adaptador_lista(this, prueba);
        //lvLista.setAdapter(adaptador);
    }
}

package com.example.urclean;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.model.Tarea;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class tareasBarrendero extends AppCompatActivity  {

    private FirebaseConnection connection;
    private String grupoUser;
    private ListView lista;
    private ArrayList<Tarea> tareas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tareas);

        tareas = new ArrayList<Tarea>();
        grupoUser = "";
        Context context = this;
        lista = findViewById(R.id.listViewTaras);
        //Context context = getApplicationContext();


        connection = FirebaseConnection.getInstance();

        connection.getPersona(new FirebaseCallback() {
            @Override
            public void onResponse(boolean correct) {
                if (correct) {
                    if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                        Log.e("vacio", "eta vacio");
                    } else {
                        for (QueryDocumentSnapshot document : connection.getResponse()) {
                            grupoUser = (String) document.get("grupo");
                            //Log.e("NAME",""+(String) document.get("name"));
                            Log.e("NAME",""+(String) document.get("grupo"));

                        }

                        // Variar para conseguir solo las de sin realizar o del usuario??

                        connection.getTarea(grupoUser, new FirebaseCallback() {
                            @Override
                            public void onResponse(boolean correct) {
                                if (correct){
                                    if (connection.getResponse().isEmpty() || connection.getResponse() == null) {

                                    }else{

                                        // Rellenar arraylist de tareas.
                                        //for que recorre las tareas de ese grupo.
                                        for (QueryDocumentSnapshot document : connection.getResponse()){


                                            String name = (String) document.get("Nombre");
                                            String calle=(String) document.get("Calle");
                                            String descripcion = (String) document.get("Descripcion");
                                            String estado= "SinRealizar";
                                            String responsable ="Alguien";
                                            tareas.add(new Tarea(name,descripcion,estado,responsable,calle));
                                        }

                                        AdapterTarea adaptador = new AdapterTarea(tareasBarrendero.this, tareas);
                                        lista.setAdapter(adaptador);

                                        lista.setOnItemClickListener( new AdapterView.OnItemClickListener(){

                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                Intent intent = new Intent( tareasBarrendero.this, menuBarrendero.class);
                                                //intent.putExtra("ObjetoTarea",tareas.get(position));
                                                startActivity(intent);

                                            }
                                        });
                                    }

                                }
                            }
                        });
                    }

                } else {
                    Log.e("ffail", "fail conection");
                }
            }
        });



    }
}

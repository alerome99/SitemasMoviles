package com.example.urclean;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.model.Tarea;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class tareasBarrendero extends AppCompatActivity  {

    private FirebaseConnection connection;
    private String grupoUser;
    private String idPersona;
    private ListView lista;
    private ArrayList<Tarea> tareas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tareas);

        tareas = new ArrayList<Tarea>();
        grupoUser = "";
        idPersona="";
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
                            idPersona = (String) document.get("idUser");

                            //Log.e("NAME",""+(String) document.get("name"));
                            Log.e("NAME",""+(String) document.get("grupo"));

                        }

                        // Variar para conseguir solo las de sin realizar o del usuario??
                        // Zona A sin realizar + las del usuario EnCurso.

                        connection.getTarea(grupoUser, new FirebaseCallback() {
                            @Override
                            public void onResponse(boolean correct) {
                                if (correct){
                                    if (connection.getResponse().isEmpty() || connection.getResponse() == null) {

                                    }else{

                                        // Rellenar arraylist de tareas.
                                        //for que recorre las tareas de ese grupo.

                                        Log.e("IDP", ""+idPersona);

                                        for (QueryDocumentSnapshot document : connection.getResponse()) {


                                            String name = (String) document.get("Nombre");
                                            String calle = (String) document.get("Calle");
                                            String descripcion = (String) document.get("Descripcion");
                                            String estado = (String) document.get("Estado");
                                            String responsable = (String) document.get("Responsable");
                                            String id = (String) document.get("Id");

                                            //Las que tiene activas se ponen antes.
                                            //Habria que mirar la eficiencia, pues este metodo
                                            //del connect coge las ya realizadas.

                                            Log.e("EST", estado);


                                            if (estado.equals("EnCurso") && responsable.equals(idPersona)) {

                                                Log.e("RESP", "Tarea con responsable.");

                                                tareas.add(0, new Tarea(name, descripcion, estado, responsable, calle, id));

                                            } else if (estado.equals("SinRealizar")) {

                                                tareas.add(new Tarea(name, descripcion, estado, responsable, calle, id));

                                            }
                                        }


                                        AdapterTarea adaptador = new AdapterTarea(tareasBarrendero.this, tareas);
                                        lista.setAdapter(adaptador);

                                        lista.setOnItemClickListener( new AdapterView.OnItemClickListener(){

                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                if(tareas.get(position).getEstado().equals("SinRealizar")) {
                                                    Intent intent = new Intent(tareasBarrendero.this, Tarea_Concreta.class);
                                                    intent.putExtra("ObjetoTarea", tareas.get(position));
                                                    startActivity(intent);

                                                }else if(tareas.get(position).getEstado().equals("EnCurso")){

                                                    Intent intent = new Intent(tareasBarrendero.this, ResponsableActivity.class);
                                                    intent.putExtra("ObjetoTarea", tareas.get(position));
                                                    startActivity(intent);

                                                } else {
                                                    Log.e("feil", "Feil en el estado");
                                                }
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

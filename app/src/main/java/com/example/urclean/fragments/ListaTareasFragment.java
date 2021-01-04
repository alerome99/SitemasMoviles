package com.example.urclean.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.urclean.AdapterTarea;
import com.example.urclean.R;
import com.example.urclean.ResponsableActivity;
import com.example.urclean.Tarea_Concreta;
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.model.Tarea;
import com.example.urclean.tareasBarrendero;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;


public class ListaTareasFragment extends Fragment {

    private FirebaseConnection connection;
    private String grupoUser;
    private String idPersona;
    private ListView lista;
    private ArrayList<Tarea> tareas;


    public ListaTareasFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connection = FirebaseConnection.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View v = inflater.inflate(R.layout.fragment_lista_tareas, container, false);

        tareas = new ArrayList<Tarea>();
        grupoUser = "";
        idPersona="";
        lista = v.findViewById(R.id.listViewTaras);

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


                                        AdapterTarea adaptador = new AdapterTarea(ListaTareasFragment.this.getActivity(), tareas);
                                        lista.setAdapter(adaptador);

                                        lista.setOnItemClickListener( new AdapterView.OnItemClickListener(){

                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                Fragment selectedFragment;

                                                if(tareas.get(position).getEstado().equals("SinRealizar")) {

                                                    Log.e("SINR","SinRealizar");

                                                    selectedFragment = new TareaConcretaFragment();
                                                    Bundle bundle = new Bundle();
                                                    bundle.putSerializable("ObjetoTarea", tareas.get(position));
                                                    selectedFragment.setArguments(bundle);
                                                    getActivity().getSupportFragmentManager().beginTransaction().
                                                            replace(R.id.fragment_container, selectedFragment)
                                                    .addToBackStack(null).commit();

                                                }else if(tareas.get(position).getEstado().equals("EnCurso")){

                                                    Log.e("ENC","En curso");

                                                    selectedFragment = new TareaResponsableFragment();
                                                    Bundle bundle = new Bundle();
                                                    bundle.putSerializable("ObjetoTarea", tareas.get(position));
                                                    selectedFragment.setArguments(bundle);

                                                    getActivity().getSupportFragmentManager().beginTransaction().
                                                            replace(R.id.fragment_container, selectedFragment).
                                                            addToBackStack(null).commit();

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


        return v;
    }
}
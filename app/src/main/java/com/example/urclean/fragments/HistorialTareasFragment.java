package com.example.urclean.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.urclean.AdapterTarea;
import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.model.Tarea;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;


public class HistorialTareasFragment extends Fragment {

    private FirebaseConnection connection;
    private String grupoUser;
    private String idPersona;
    private ListView lista;
    private ArrayList<Tarea> tareas;


    public HistorialTareasFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connection = FirebaseConnection.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.fragment_historial_tareas, container, false);

        tareas = new ArrayList<Tarea>();
        grupoUser = "";
        idPersona="";
        lista = v.findViewById(R.id.listaHistorialTareas);

        Log.e("HTP", "aaa");

        connection.getTareasPersona( new FirebaseCallback() {

            @Override
            public void onResponse(boolean correct) {

                if(correct) {

                    if (connection.getResponse().isEmpty() || connection.getResponse() == null) {

                    } else {

                        for (QueryDocumentSnapshot document : connection.getResponse()) {


                            String name = (String) document.get("Nombre");
                            String calle = (String) document.get("Calle");
                            String descripcion = (String) document.get("Descripcion");
                            String estado = (String) document.get("Estado");
                            String responsable = (String) document.get("Responsable");
                            String id = (String) document.get("Id");

                            tareas.add(new Tarea(name, descripcion, estado, responsable, calle, id));

                        }

                        AdapterTarea adaptador = new AdapterTarea(HistorialTareasFragment.this.getActivity(), tareas);
                        lista.setAdapter(adaptador);

                    }
                } else{

                }

            }
        });

      return v;
    }
}
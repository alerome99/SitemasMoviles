package com.example.urclean.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

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
    private TextView num;
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
        num = v.findViewById(R.id.NumHistorialTareas);

        Log.e("HTP", "aaa");

        connection.getTareasPersona( new FirebaseCallback() {

            @Override
            public void onResponse(boolean correct) {

                if(correct) {

                    if (connection.getResponse().isEmpty() || connection.getResponse() == null) {

                    } else {

                        String name = "";
                        String calle = "";
                        String descripcion = "";
                        String estado = "";
                        String responsable = "";
                        String id = "";
                        String grupo = "";
                        String email ="";

                        for (QueryDocumentSnapshot document : connection.getResponse()) {


                            name = (String) document.get("Nombre");
                            calle = (String) document.get("Calle");
                            descripcion = (String) document.get("Descripcion");
                            estado = (String) document.get("Estado");
                            responsable = (String) document.get("Responsable");
                            id = (String) document.get("Id");
                            grupo = (String) document.get("Grupo");
                            email = (String) document.get("email");

                            tareas.add(new Tarea(name, descripcion, estado, responsable, calle, id,grupo, email));

                        }

                        num.setText(""+tareas.size());
                        AdapterTarea adaptador = new AdapterTarea(HistorialTareasFragment.this.getActivity(), tareas);
                        lista.setAdapter(adaptador);


                    }
                } else{

                }

            }
        });

      return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
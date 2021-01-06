package com.example.urclean.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.urclean.AdapterHistorialTareas;
import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.model.Tarea;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class HistorialIncidenciasFragment  extends Fragment {
    private ArrayList<Tarea> tareas;
    private FirebaseConnection connection;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_quejas, container, false);

        connection = FirebaseConnection.getInstance();

        listView = view.findViewById(R.id.listViewQuejas);
        tareas = new ArrayList<>();

        connection.getIncidenciaPorEmail(correct -> {
            if (correct) {
                if (connection.getResponse().isEmpty() || connection.getResponse() == null) {

                } else {
                    String name = "";
                    String descripcion = "";
                    String estado = "";
                    String responsable = "";
                    String calle = "";
                    String id = "";
                    String grupo = "";
                    String email = "";

                    for (QueryDocumentSnapshot document : connection.getResponse()) {
                        name = (String) document.get("Nombre");
                        descripcion = (String) document.get("Descripcion");
                        estado = (String) document.get("Estado");
                        responsable = (String) document.get("Responsable");
                        calle = (String) document.get("Calle");
                        id = (String) document.get("Id");
                        grupo = (String) document.get("Grupo");
                        email = (String) document.get("email");

                        Tarea t = new Tarea(name,descripcion,estado,responsable,calle,id,grupo,email);
                        tareas.add(t);

                    }
                    AdapterHistorialTareas adaptador = new AdapterHistorialTareas(getActivity(), tareas);
                    listView.setAdapter(adaptador);
                }
            }
        });

        return view;
    }
}

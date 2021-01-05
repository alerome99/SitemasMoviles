package com.example.urclean.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.urclean.AdapterHistorialDesperfectos;
import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.model.Desperfecto;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class HistorialDesperfectosFragment extends Fragment {

    private ArrayList<Desperfecto> desperfectos;
    private FirebaseConnection connection;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_quejas, container, false);

        connection = FirebaseConnection.getInstance();

        listView = view.findViewById(R.id.listViewQuejas);
        desperfectos = new ArrayList<>();

        connection.getDesperfectoPorEmail(correct -> {
            if (correct) {
                if (connection.getResponse().isEmpty() || connection.getResponse() == null) {

                } else {
                    String descripcion = "";
                    String email = "";
                    String titulo = "";
                    String direccion = "";
                    String estado = "";

                    for (QueryDocumentSnapshot document : connection.getResponse()) {
                        descripcion = (String) document.get("descripcion");
                        email = (String) document.get("email");
                        titulo = (String) document.get("titulo");
                        direccion = (String) document.get("direccion");
                        estado = (String) document.get("estado");

                        Desperfecto d = new Desperfecto(descripcion,email,titulo,direccion);
                        d.setEstado(estado);
                        desperfectos.add(d);

                    }
                    AdapterHistorialDesperfectos adaptador = new AdapterHistorialDesperfectos(getActivity(), desperfectos);
                    listView.setAdapter(adaptador);
                }
            }
        });

        return view;
    }
}
package com.example.urclean.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.urclean.AdapterHistorialQuejas;
import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.model.Queja;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class HistorialQuejasFragment extends Fragment {

    private ArrayList<Queja> quejas;
    private FirebaseConnection connection;
    private ListView listViewQuejas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_quejas, container, false);

        connection = FirebaseConnection.getInstance();

        listViewQuejas = view.findViewById(R.id.listViewQuejas);
        quejas = new ArrayList<>();

        connection.getQuejaPorEmail(correct -> {
            if (correct) {
                if (connection.getResponse().isEmpty() || connection.getResponse() == null) {

                } else {
                    String email = "";
                    String descripcion = "";
                    String estado = "";
                    String titulo = "";
                    for (QueryDocumentSnapshot document : connection.getResponse()) {
                        email = (String) document.get("email");
                        descripcion = (String) document.get("descripcion");
                        estado = (String) document.get("estado");
                        titulo = (String) document.get("titulo");

                        Queja q = new Queja(descripcion, email, titulo);
                        q.setEstado(estado);
                        quejas.add(q);

                    }
                    AdapterHistorialQuejas adaptador = new AdapterHistorialQuejas(getActivity(), quejas);
                    listViewQuejas.setAdapter(adaptador);
                }
            }
        });

        return view;
    }
}
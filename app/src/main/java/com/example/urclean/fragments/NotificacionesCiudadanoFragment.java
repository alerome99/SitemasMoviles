package com.example.urclean.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseConnection;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;


public class NotificacionesCiudadanoFragment extends Fragment {

    private FirebaseConnection connection;
    private ListView lista;
    private String email;
    private ArrayAdapter adaptador;
    private ArrayList<String> quejas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_notificaciones, container, false);

        lista = view.findViewById(R.id.listViewNotificaciones);
        connection = FirebaseConnection.getInstance();
        quejas = new ArrayList<String>();


        connection.getNotificacionesQuejas(correct ->{
            if(correct){
                if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                } else {
                    for (QueryDocumentSnapshot document : connection.getResponse()) {
                        String titulo = (String) document.get("titulo");
                        String estado = (String) document.get("estado");
                        quejas.add("La queja "+titulo+" ha sido "+estado);
                    }
                    adaptador = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1 , quejas);
                    lista.setAdapter(adaptador);
                }
            }
        });

        connection.getNotificacionesIncidencias(correct -> {
            if(correct){
                if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                } else {
                    for (QueryDocumentSnapshot document : connection.getResponse()) {
                        String asunto = (String) document.get("Nombre");
                        String estado = (String) document.get("Estado");
                        quejas.add("La incidencia "+asunto+" está "+estado);
                    }
                    adaptador = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1 , quejas);
                    lista.setAdapter(adaptador);
                }
            }
        });



        return view;
    }
}
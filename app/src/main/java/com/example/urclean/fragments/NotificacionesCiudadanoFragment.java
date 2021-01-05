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
import com.example.urclean.model.estadoQueja;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;


public class NotificacionesCiudadanoFragment extends Fragment {

    private FirebaseConnection connection;
    private ListView listViewLista;
    private ArrayAdapter adaptador;
    private ArrayList<String> lista;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_notificaciones, container, false);

        listViewLista = view.findViewById(R.id.listViewNotificaciones);
        connection = FirebaseConnection.getInstance();
        lista = new ArrayList<>();


        connection.getNotificacionesQuejas(correct ->{
            if(correct){
                if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                } else {
                    for (QueryDocumentSnapshot document : connection.getResponse()) {
                        String titulo = (String) document.get("titulo");
                        String estado = (String) document.get("estado");
                        if(estado.equals(estadoQueja.RECIBIDA.toString())) {
                            lista.add("La queja " + titulo + " ha sido recibida");
                        }else if(estado.equals(estadoQueja.SOLUCIONADA.toString())) {
                            lista.add("La queja " + titulo + " ha sido solucionada");
                        }
                        adaptador = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1 , lista);
                        listViewLista.setAdapter(adaptador);
                    }
                }
            }
        });

        connection.getNotificacionesIncidencias(correct -> {
            if(correct){
                if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                } else {
                    for (QueryDocumentSnapshot document : connection.getResponse()) {
                        String titulo = (String) document.get("Nombre");
                        String estado = (String) document.get("Estado");
                        if(estado.equals("EnCurso")) {
                            lista.add("La incidencia " + titulo + " esta en curso");
                        }else if(estado.equals("Completada")) {
                            lista.add("La incidencia " + titulo + " esta solucionada");
                        }
                        adaptador = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1 , lista);
                        listViewLista.setAdapter(adaptador);
                    }
                }
            }
        });

        connection.getNotificacionesDesperfectos(correct ->{
            if(correct){
                if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                } else {
                    for (QueryDocumentSnapshot document : connection.getResponse()) {
                        String titulo = (String) document.get("titulo");
                        String estado = (String) document.get("estado");
                        if(estado.equals(estadoQueja.RECIBIDA.toString())) {
                            lista.add("El desperfecto " + titulo + " ha sido recibido");
                        }else if(estado.equals(estadoQueja.SOLUCIONADA.toString())) {
                            lista.add("El desperfecto " + titulo + " ha sido solucionado");
                        }
                        adaptador = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1 , lista);
                        listViewLista.setAdapter(adaptador);
                    }
                }
            }
        });

        return view;
    }
}
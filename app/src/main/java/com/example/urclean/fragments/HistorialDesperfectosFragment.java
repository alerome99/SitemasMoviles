package com.example.urclean.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class HistorialDesperfectosFragment extends Fragment {

    private ArrayList<String> desperfectos;
    private FirebaseConnection connection;
    private ListView lista;
    private String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historial_desperfectos, container, false);

        connection = FirebaseConnection.getInstance();

        desperfectos = new ArrayList<String>();
        lista = view.findViewById(R.id.listViewHistorialDesperfectos);

        connection.getPersona(new FirebaseCallback() {
            @Override
            public void onResponse(boolean correct) {
                if (correct) {
                    if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                        Log.e("vacio", "esta vacio");
                    } else {
                        for (QueryDocumentSnapshot document : connection.getResponse()) {
                            email = (String) document.get("email");

                        }

                        connection.getDesperfecto(email, new FirebaseCallback() {
                            @Override
                            public void onResponse(boolean correct) {
                                if (correct){
                                    if (connection.getResponse().isEmpty() || connection.getResponse() == null) {

                                    }else{

                                        // Rellenar arraylist de desperfectos.
                                        //for que recorre los desperfectos.
                                        for (QueryDocumentSnapshot document : connection.getResponse()) {
                                            String descripcion = (String) document.get("descripcion");
                                            String direccion = (String) document.get("direccion");
                                            desperfectos.add("Dirección: " + direccion + "\nDescripcion: " + descripcion);
                                        }


                                        ArrayAdapter adaptador = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1 , desperfectos);
                                        lista.setAdapter(adaptador);

                                        lista.setOnItemClickListener( new AdapterView.OnItemClickListener(){
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                            }
                                        });
                                    }

                                }
                            }
                        });
                    }

                } else {
                    Log.e("fail", "fail conection");
                }
            }
        });

        return view;
    }
}
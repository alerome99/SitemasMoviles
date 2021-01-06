package com.example.urclean.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;


public class ListaNotificacionesCiudadanoFragment extends Fragment {

    EditText searchFilter;
    private FirebaseConnection connection;
    private ListView listViewNotificaciones;
    private ArrayAdapter adaptador;
    private ArrayList<String> lista;
    private String[] notificaciones;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_notificaciones, container, false);

        listViewNotificaciones = view.findViewById(R.id.listViewNotificaciones);
        connection = FirebaseConnection.getInstance();
        lista = new ArrayList<>();
        searchFilter = view.findViewById(R.id.searchFilter);

        connection.getNotificacionesCiudadano(correct -> {
            if (correct) {
                if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                } else {
                    String tipo = "";
                    String estado = "";
                    String titulo = "";
                    String id = "";
                    int i = 0;
                    notificaciones = new String [connection.getResponse().size()];
                    for (QueryDocumentSnapshot document : connection.getResponse()) {
                        tipo = (String) document.getString("tipo");
                        estado = (String) document.getString("estado");
                        titulo = (String) document.getString("titulo");
                        id = (String) document.getString("id");
                        if (tipo.equals("Queja")) {
                            if (estado.equals("RECIBIDA")) {
                                lista.add("La queja " + titulo + " ha sido recibida");
                            } else if (estado.equals("SOLUCIONADA")) {
                                lista.add("La queja " + titulo + " ha sido solucionada");
                            }
                        } else if (tipo.equals("Desperfecto")) {
                            if (estado.equals("RECIBIDA")) {
                                lista.add("El desperfecto " + titulo + " ha sido recibido");
                            } else if (estado.equals("SOLUCIONADA")) {
                                lista.add("El desperfecto " + titulo + " ha sido solucionado");
                            }
                        } else {
                            if (estado.equals("EnCurso")) {
                                lista.add("La incidencia " + titulo + " esta en curso");
                            } else if (estado.equals("Completada")) {
                                lista.add("La incidencia " + titulo + " esta solucionada");
                            }
                        }
                        notificaciones[i]=id;
                        i++;
                    }
                    adaptador = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, lista);
                    listViewNotificaciones.setAdapter(adaptador);

                    listViewNotificaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            connection.leerNotificacionCiudadano(notificaciones[position], new FirebaseCallback() {
                                @Override
                                public void onResponse(boolean correct) {
                                    if(correct){
                                        Snackbar.make(view, "Notificacion marcada como leida", Snackbar.LENGTH_LONG).show();
                                    }else{
                                        Snackbar.make(view, "Ha habido un error", Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });

        searchFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (ListaNotificacionesCiudadanoFragment.this).adaptador.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        return view;
    }
}
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

import androidx.fragment.app.Fragment;

import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseConnection;
import com.google.firebase.firestore.QueryDocumentSnapshot;


public class ListaUsuariosFragment extends Fragment {

    EditText searchFilter;
    private ListView lvLista;
    String[] datos;
    String[][] extra;
    private ArrayAdapter<String> adaptador;
    private FirebaseConnection connection;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        connection = FirebaseConnection.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lista_clientes, container, false);
        searchFilter = v.findViewById(R.id.searchFilter);
        lvLista = v.findViewById(R.id.lvLista);


        connection.getUsuarios(correct -> {
            if (correct) {
                if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                } else {
                    int i = 0;
                    datos = new String [connection.getResponse().size()];
                    extra = new String [connection.getResponse().size()][3];
                    for (QueryDocumentSnapshot document : connection.getResponse()) {
                        datos[i] = (String) document.get("username");
                        extra[i][0] = (String) document.get("name");
                        extra[i][1] = (String) document.get("email");
                        extra[i][2] = (String) document.get("telefono");
                        i++;
                    }
                    adaptador = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,datos);
                    lvLista.setAdapter(adaptador);
                    lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Fragment selectedFragment;
                            selectedFragment = new DetallesUsuarioFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("EMAIL", extra[position][1]);
                            bundle.putString("NAME", extra[position][0]);
                            bundle.putString("TELEFONO", extra[position][2]);
                            selectedFragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction().
                                    replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
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
                (ListaUsuariosFragment.this).adaptador.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                connection.getUsuarios(correct -> {
                    if (correct) {
                        if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                        } else {
                            int i = 0;
                            datos = new String [connection.getResponse().size()];
                            extra = new String [connection.getResponse().size()][3];
                            for (QueryDocumentSnapshot document : connection.getResponse()) {
                                if(document.get("username").toString().contains(s)){
                                    datos[i] = (String) document.get("username");
                                    extra[i][0] = (String) document.get("name");
                                    extra[i][1] = (String) document.get("email");
                                    extra[i][2] = (String) document.get("telefono");
                                    i++;
                                }
                            }
                            lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Fragment selectedFragment;
                                    selectedFragment = new DetallesBarrenderoFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("EMAIL", extra[position][1]);
                                    bundle.putString("NAME", extra[position][0]);
                                    bundle.putString("TELEFONO", extra[position][2]);
                                    selectedFragment.setArguments(bundle);
                                    getActivity().getSupportFragmentManager().beginTransaction().
                                            replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
                                }
                            });
                        }
                    }
                });
            }
        });

        return v;
    }


}
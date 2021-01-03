package com.example.urclean.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.urclean.AdapterQueja;
import com.example.urclean.R;
import com.example.urclean.ResponsableActivity;
import com.example.urclean.Tarea_Concreta;
import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.model.Queja;
import com.example.urclean.tareasBarrendero;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;


public class QuejasSupervisorFragment extends Fragment {

    private ListView listViewQuejas;
    private BottomNavigationView bottom_navigation;

    private FirebaseConnection connection;

    private ArrayList<Queja> quejas;

    public QuejasSupervisorFragment() {
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lista_quejas, container, false);

        listViewQuejas = v.findViewById(R.id.listViewQuejas);
        bottom_navigation = v.findViewById(R.id.bottom_navigation);
        quejas = new ArrayList<>();

        connection.getQuejas(correct -> {
            if (correct){
                if (connection.getResponse().isEmpty() || connection.getResponse() == null){

                }else{
                    String email = "";
                    String descripcion = "";
                    String estado = "";
                    String titulo = "";
                    for (QueryDocumentSnapshot document : connection.getResponse()){
                        email = (String) document.get("email");
                        descripcion = (String) document.get("descripcion");
                        estado = (String) document.get("estado");
                        titulo = (String) document.get("titulo");
                        if(estado!=null && !estado.equals("SOLUCIONADA")) {
                            Queja q = new Queja(descripcion, email, titulo);
                            q.setEstado(estado);
                            quejas.add(q);
                        }
                    }
                    AdapterQueja adaptador = new AdapterQueja(getActivity(), quejas);
                    listViewQuejas.setAdapter(adaptador);

                    listViewQuejas.setOnItemClickListener( new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Fragment selectedFragment;
                            selectedFragment = new DetallesQuejaFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("TITULO", quejas.get(position).getTitulo());
                            bundle.putString("DESCRIPCION", quejas.get(position).getDescripcion());
                            bundle.putString("EMAIL", quejas.get(position).getEmail());
                            bundle.putString("ESTADO", quejas.get(position).getEstado().toString());
                            selectedFragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction().
                                    replace(R.id.fragment_container, selectedFragment).commit();
                        }
                    });
                }
            }else{

            }
        });


        return v;
    }


}
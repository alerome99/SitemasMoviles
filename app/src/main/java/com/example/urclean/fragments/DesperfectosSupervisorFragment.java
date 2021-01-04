package com.example.urclean.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.urclean.AdapterDesperfecto;
import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.model.Desperfecto;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class DesperfectosSupervisorFragment extends Fragment {

    private ListView listViewQuejas;
    private BottomNavigationView bottom_navigation;

    private FirebaseConnection connection;

    private ArrayList<Desperfecto> desperfectos;

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
        desperfectos = new ArrayList<>();

        connection.getDesperfectos(correct -> {
            if (correct){
                if (connection.getResponse().isEmpty() || connection.getResponse() == null){

                }else{
                    String email = "";
                    String descripcion = "";
                    String estado = "";
                    String titulo = "";
                    String direccion = "";
                    for (QueryDocumentSnapshot document : connection.getResponse()){
                        email = (String) document.get("email");
                        descripcion = (String) document.get("descripcion");
                        estado = (String) document.get("estado");
                        titulo = (String) document.get("titulo");
                        direccion = (String) document.get("direccion");
                        if(estado!=null && !estado.equals("SOLUCIONADA")) {
                            Desperfecto d = new Desperfecto(descripcion, email, titulo, direccion);
                            d.setEstado(estado);
                            desperfectos.add(d);
                        }
                    }
                    AdapterDesperfecto adaptador = new AdapterDesperfecto(getActivity(), desperfectos);
                    listViewQuejas.setAdapter(adaptador);

                    listViewQuejas.setOnItemClickListener( new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Fragment selectedFragment;
                            selectedFragment = new DetallesDesperfectoFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("TITULO", desperfectos.get(position).getTitulo());
                            bundle.putString("DESCRIPCION", desperfectos.get(position).getDescripcion());
                            bundle.putString("EMAIL", desperfectos.get(position).getEmail());
                            bundle.putString("ESTADO", desperfectos.get(position).getEstado().toString());
                            bundle.putString("DIRECCION", desperfectos.get(position).getDireccion().toString());
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
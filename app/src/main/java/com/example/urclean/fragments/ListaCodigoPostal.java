package com.example.urclean.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.urclean.AdapterGrupo;
import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.model.CodigoPostal;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;


public class ListaCodigoPostal extends Fragment {

    private ListView listViewQuejas;
    private BottomNavigationView bottom_navigation;

    private FirebaseConnection connection;

    private ArrayList<CodigoPostal> codigos;

    public ListaCodigoPostal() {
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
        codigos = new ArrayList<>();

        connection.getCodigosPostales(correct -> {
            if (correct){
                if (connection.getResponse().isEmpty() || connection.getResponse() == null){

                }else{
                    String codigo = "";
                    String zona = "";
                    for (QueryDocumentSnapshot document : connection.getResponse()){
                        zona = (String) document.get("zona");
                        codigo = (String) document.get("codigo");
                        CodigoPostal c = new CodigoPostal(zona, codigo);
                        codigos.add(c);
                    }
                    AdapterGrupo adaptador = new AdapterGrupo(getActivity(), codigos);
                    listViewQuejas.setAdapter(adaptador);

                    listViewQuejas.setOnItemClickListener( new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Fragment selectedFragment;
                            selectedFragment = new AddGrupoFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("CODIGO", codigos.get(position).getCodigo());
                            selectedFragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction().
                                    replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
                        }
                    });
                }
            }else{

            }
        });


        return v;
    }


}
package com.example.urclean.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.urclean.AdapterTarea;
import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.model.Tarea;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;


public class ListaTareasFragment extends Fragment {

    private FirebaseConnection connection;
    private String grupoUser;
    private String idPersona;
    private ListView lista;
    private ArrayList<Tarea> tareas;


    public ListaTareasFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connection = FirebaseConnection.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View v = inflater.inflate(R.layout.fragment_lista_tareas, container, false);

        tareas = new ArrayList<Tarea>();
        grupoUser = "";
        idPersona="";
        lista = v.findViewById(R.id.listViewTaras);

        connection.getPersona(new FirebaseCallback() {
            @Override
            public void onResponse(boolean correct) {
                if (correct) {
                    if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                    } else {
                        for (QueryDocumentSnapshot document : connection.getResponse()) {
                            grupoUser = (String) document.get("Grupo");
                            idPersona = (String) document.get("idUser");

                        }

                        // Variar para conseguir solo las de sin realizar o del usuario??
                        // Zona A sin realizar + las del usuario EnCurso.

                        connection.getTarea(grupoUser, new FirebaseCallback() {
                            @Override
                            public void onResponse(boolean correct) {
                                if (correct){
                                    if (connection.getResponse().isEmpty() || connection.getResponse() == null) {

                                    }else{

                                        // Rellenar arraylist de tareas.
                                        //for que recorre las tareas de ese grupo.

                                        String name = "";
                                        String calle = "";
                                        String descripcion = "";
                                        String estado = "";
                                        String responsable = "";
                                        String id = "";
                                        String grupo = "";
                                        String email ="";
                                        String lat="";
                                        String lng="";

                                        for (QueryDocumentSnapshot document : connection.getResponse()) {


                                            name = (String) document.get("Nombre");
                                            calle = (String) document.get("Calle");
                                            descripcion = (String) document.get("Descripcion");
                                            estado = (String) document.get("Estado");
                                            responsable = (String) document.get("Responsable");
                                            id = (String) document.get("Id");
                                            grupo = (String) document.get("Grupo");
                                            email = (String) document.get("email");
                                            lat = (String) document.get("lat");
                                            lng =  (String) document.get("lng");


                                            //Las que tiene activas se ponen antes.
                                            //Habria que mirar la eficiencia, pues este metodo
                                            //del connect coge las ya realizadas.

                                            if (estado.equals("EnCurso") && responsable.equals(idPersona)) {


                                    

                                                tareas.add(0, new Tarea(name, descripcion, estado, responsable, calle, id,grupo,email,lat,lng));


                                            } else if (estado.equals("SinAsignar")) {

                                                tareas.add(new Tarea(name, descripcion, estado, responsable, calle, id,grupo,email,lat,lng));

                                            }
                                        }


                                        AdapterTarea adaptador = new AdapterTarea(ListaTareasFragment.this.getActivity(), tareas);
                                        lista.setAdapter(adaptador);

                                        lista.setOnItemClickListener( new AdapterView.OnItemClickListener(){

                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                Fragment selectedFragment;

                                                if(tareas.get(position).getEstado().equals("SinAsignar")) {
                                                    selectedFragment = new TareaConcretaFragment();
                                                    Bundle bundle = new Bundle();
                                                    bundle.putSerializable("ObjetoTarea", tareas.get(position));
                                                    selectedFragment.setArguments(bundle);
                                                    getActivity().getSupportFragmentManager().beginTransaction().
                                                            replace(R.id.fragment_container, selectedFragment)
                                                    .addToBackStack(null).commit();
                                                }else if(tareas.get(position).getEstado().equals("EnCurso")){
                                                    selectedFragment = new TareaResponsableFragment();
                                                    Bundle bundle = new Bundle();
                                                    bundle.putSerializable("ObjetoTarea", tareas.get(position));
                                                    selectedFragment.setArguments(bundle);

                                                    getActivity().getSupportFragmentManager().beginTransaction().
                                                            replace(R.id.fragment_container, selectedFragment).
                                                            addToBackStack(null).commit();
                                                } else {
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });
                    }
                } else {
                }
            }
        });


        return v;
    }
}
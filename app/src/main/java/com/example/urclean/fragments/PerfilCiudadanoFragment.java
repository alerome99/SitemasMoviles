package com.example.urclean.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseConnection;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class PerfilCiudadanoFragment extends Fragment implements View.OnTouchListener{

    private FirebaseConnection connection;
    TextView textViewEmail;
    TextView textViewName;
    TextView textViewUsername;
    TextView testViewPhone;
    TextView textViewDni;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil_ciudadano, container, false);

        textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewName = view.findViewById(R.id.textViewName);
        textViewUsername = view.findViewById(R.id.textViewUsername);
        testViewPhone = view.findViewById(R.id.textViewTelefono);
        textViewDni = view.findViewById(R.id.textViewDni);

        connection = FirebaseConnection.getInstance();
        connection.getPersona(correct -> {
            if (correct){
                if (connection.getResponse().isEmpty() || connection.getResponse() == null){

                }else{
                    String email = "";
                    String name = "";
                    String username = "";
                    String phone = "";
                    String dni = "";
                    for (QueryDocumentSnapshot document : connection.getResponse()){
                        email = (String) document.get("email");
                        name = (String) document.get("name");
                        username = (String) document.get("username");
                        phone = (String) document.get("telefono");
                        dni = (String) document.get("dni");
                    }
                    textViewEmail.setText(email);
                    textViewName.setText(name);
                    textViewUsername.setText(username);
                    testViewPhone.setText(phone);
                    textViewDni.setText(dni);
                }
            }else{

            }
        });

        view.findViewById(R.id.botonEditar).setOnTouchListener(this);

        return view;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Fragment selectedFragment = new EditarPerfilCiudadanoFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        return false;
    }
}
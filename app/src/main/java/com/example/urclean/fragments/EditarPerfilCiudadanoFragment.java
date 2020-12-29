package com.example.urclean.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseConnection;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class EditarPerfilCiudadanoFragment extends Fragment implements View.OnTouchListener{

    private EditText editTextUsername, editTextName, editTextEmail, editTextDni, editTextTelefono;
    private FirebaseConnection connection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editar_perfil_ciudadano, container, false);

        connection = FirebaseConnection.getInstance();

        editTextUsername = view.findViewById(R.id.EditTextUsername);
        editTextName = view.findViewById(R.id.EditTextName);
        editTextEmail = view.findViewById(R.id.EditTextEmail);
        editTextDni = view.findViewById(R.id.EditTextDni);
        editTextTelefono = view.findViewById(R.id.EditTextTelefono);

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
                    editTextUsername.setText(username);
                    editTextName.setText(name);
                    editTextTelefono.setText(phone);
                    editTextDni.setText(dni);
                    editTextEmail.setText(email);
                }
            }else{

            }
        });


        view.findViewById(R.id.botonCancelar).setOnTouchListener(this);
        view.findViewById(R.id.botonAceptar).setOnTouchListener(this);

        return view;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Fragment selectedFragment;
        switch (view.getId()) {
            case R.id.botonCancelar:
                selectedFragment = new PerfilCiudadanoFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                break;
            case R.id.botonAceptar:
                connection.modificarPersona(editTextUsername.getText().toString(), editTextName.getText().toString(), editTextDni.getText().toString(),
                        editTextTelefono.getText().toString(), editTextEmail.getText().toString());
                selectedFragment = new PerfilCiudadanoFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                break;
        }
        return false;
    }
}
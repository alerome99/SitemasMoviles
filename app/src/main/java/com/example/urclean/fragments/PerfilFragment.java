package com.example.urclean.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseConnection;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;


public class PerfilFragment extends Fragment {

    private FirebaseConnection connection;
    TextView textViewEmail;
    TextView textViewName;
    TextView textViewUsername;
    TextView testViewPhone;
    TextView textViewDni;
    EditText editTextPhone;

    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        connection = FirebaseConnection.getInstance();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);


        textViewEmail = v.findViewById(R.id.textViewEmail);
        textViewName = v.findViewById(R.id.textViewName);
        textViewUsername = v.findViewById(R.id.textViewUsername);
        testViewPhone = v.findViewById(R.id.textViewTelefono);
        textViewDni = v.findViewById(R.id.textViewDni);


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


        return v;
    }


}
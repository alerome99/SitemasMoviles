package com.example.urclean;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.urclean.firebase.FirebaseConnection;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Perfil_v2 extends AppCompatActivity {
    private FirebaseConnection connection;
    EditText editTextName;
    EditText editTextDni;
    EditText editTextPhone;
    EditText editTextEmail;
    EditText editTextUsername;
    Button buttonModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_personal_v2);

        editTextName = findViewById(R.id.editTextName);
        editTextDni = findViewById(R.id.editTextDni);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUsername = findViewById(R.id.editTextUsername);
        buttonModificar = findViewById(R.id.buttonModificar);

        connection = FirebaseConnection.getInstance();

        buttonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Perfil_v2.this, ModificarPerfilActivity.class));
            }
        });

        connection.getTypeUser(correct -> {
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
                    System.out.println(username);
                    editTextEmail.setText(email);
                    editTextName.setText(name);
                    editTextUsername.setText(username);
                    editTextPhone.setText(phone);
                    editTextDni.setText(dni);
                    editTextEmail.setFocusable(false);
                    editTextName.setFocusable(false);
                    editTextUsername.setFocusable(false);
                    editTextPhone.setFocusable(false);
                    editTextDni.setFocusable(false);
                }
            }else{

            }
        });

        super.onCreate(savedInstanceState);
    }
}

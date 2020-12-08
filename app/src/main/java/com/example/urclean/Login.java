package com.example.urclean;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.urclean.model.userClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    private List<userClient> listaPersona = new ArrayList<userClient>();

    Button buttonRegister, buttonLogin;
    EditText username, password;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin=findViewById(R.id.buttonLogin);
        buttonRegister=findViewById(R.id.buttonRegister);

        username = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editTextTextPassword);

        inicializacionFireBase();
        listaDatos();

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, signUp.class));
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!usuarioExiste()){
                    Snackbar.make(v, "WRONG USERNAME OR PASSWORD", Snackbar.LENGTH_SHORT).show();
                    //Toast.makeText(getBaseContext(), "wrong username or password", Toast.LENGTH_LONG).show();
                }else{
                    if(esAdministrador()){
                        //startActivity(new Intent(Login.this, MenuAdministradorActivity.class));
                    }
                    if(esBarrendero()){
                        startActivity(new Intent(Login.this, menuBarrendero.class));
                    }
                    if(esCiudadano()){
                        startActivity(new Intent(Login.this, MenuCiudadanoActivity.class));
                    }
                }
            }
        });
    }

    private boolean usuarioExiste() {
        String u = username.getText().toString();
        String c = password.getText().toString();
        for(int i = 0; i<listaPersona.size(); i++){
            if(listaPersona.get(i).getUsername().equals(u)){
                if(listaPersona.get(i).getPassword().equals(c)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean esAdministrador() {
        String u = username.getText().toString();
        String c = password.getText().toString();
        for(int i = 0; i<listaPersona.size(); i++) {
            if (listaPersona.get(i).getUsername().equals(u)) {
                if (listaPersona.get(i).getPassword().equals(c)) {
                    if (listaPersona.get(i).getTipoUsuario().equals("userAdministrador")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean esBarrendero() {
        String u = username.getText().toString();
        String c = password.getText().toString();
        for(int i = 0; i<listaPersona.size(); i++) {
            if (listaPersona.get(i).getUsername().equals(u)) {
                if (listaPersona.get(i).getPassword().equals(c)) {
                    if (listaPersona.get(i).getTipoUsuario().equals("userBarrendero")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean esCiudadano() {
        String u = username.getText().toString();
        String c = password.getText().toString();
        for(int i = 0; i<listaPersona.size(); i++) {
            if (listaPersona.get(i).getUsername().equals(u)) {
                if (listaPersona.get(i).getPassword().equals(c)) {
                    if (listaPersona.get(i).getTipoUsuario().equals("userCliente")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void listaDatos() {
        databaseReference.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaPersona.clear();
                for(DataSnapshot objSnap : snapshot.getChildren()){
                    userClient p = objSnap.getValue(userClient.class);
                    listaPersona.add(p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void inicializacionFireBase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}
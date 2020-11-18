package com.example.urclean;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.example.urclean.model.userClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class signUp extends AppCompatActivity {

    private List<userClient> listaPersona = new ArrayList<userClient>();

    Button buttonRegisterFinal, buttonLogin;
    EditText username, password, password2, name, gmail;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editTextTextPassword);
        password2 = findViewById(R.id.editTextTextPassword2);
        name = findViewById(R.id.editTextTextName);
        gmail = findViewById(R.id.editTextTextGmail);

        inicializacionFireBase();
        listaDatos();



        buttonRegisterFinal=findViewById(R.id.buttonRegisterFinal);
        buttonRegisterFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String u = username.getText().toString();
                String c = password.getText().toString();
                String c2 = password2.getText().toString();
                String n = name.getText().toString();
                String g = gmail.getText().toString();
                if(u.equals("") || c.equals("") || c2.equals("") || n.equals("") || g.equals("")){
                    faltanDatos();
                }else{
                    boolean bandera = comprobarExiste();
                    if(bandera==true){
                    }else {
                        if (!c.equals(c2)) {
                            password2.setError("Password must be the same as the one entered in the previous field");
                        } else {
                            userClient persona = new userClient();
                            persona.setId(UUID.randomUUID().toString());
                            persona.setGmail(g);
                            persona.setName(n);
                            persona.setPassword(c);
                            persona.setUsername(u);
                            databaseReference.child("Persona").child(persona.getId()).setValue(persona);
                            startActivity(new Intent(signUp.this, Login.class));
                        }
                    }
                }
            }
        });
    }

    private boolean comprobarExiste() {
        boolean bandera = false;
        String u = username.getText().toString();
        String g = gmail.getText().toString();
        for(int i = 0; i < listaPersona.size(); i++){
            if(u.equals(listaPersona.get(i).getUsername())){
                username.setError("User entered already exists");
                bandera = true;
            }
            if(g.equals(listaPersona.get(i).getGmail())){
                gmail.setError("Gmail entered already exists");
                bandera = true;
            }
        }
        return bandera;
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

    private void faltanDatos() {
        String u = username.getText().toString();
        String c = password.getText().toString();
        String c2 = password2.getText().toString();
        String n = name.getText().toString();
        String g = gmail.getText().toString();
        if (u.equals("")) {
            username.setError("Required");
        }
        if (c.equals("")) {
            password.setError("Required");
        }
        if (c2.equals("")) {
            password2.setError("Required");
        }
        if (n.equals("")) {
            name.setError("Required");
        }
        if (g.equals("")) {
            gmail.setError("Required");
        }
    }
}
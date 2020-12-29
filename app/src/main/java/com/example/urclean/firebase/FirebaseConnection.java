package com.example.urclean.firebase;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class FirebaseConnection {

    private static FirebaseConnection singleton;
    private static FirebaseAuth mAuth;
    private static FirebaseFirestore db;
    private QuerySnapshot response;
    private DatabaseReference databaseReference;

    public static FirebaseConnection getInstance() {
        if (singleton == null) { // Si no existe conexion establecida
            singleton = new FirebaseConnection();
            mAuth = FirebaseAuth.getInstance();
            db = FirebaseFirestore.getInstance();
            Log.e("SING", "CREADO");
        }
        return singleton; //Devuelve la instancia de la conexion
    }

    public void login(Activity activity, String email, String password, final FirebaseCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        callback.onResponse(true);
                    } else {
                        callback.onResponse(false);
                    }
                });

    }

    public void register(Activity activity, String email, String password, final FirebaseCallback callback) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        callback.onResponse(true);
                    } else {
                        callback.onResponse(false);
                    }
                });

    }

    public void saveUser(String name,String username,String email, String usertype, String telefono,
                         String dni, final FirebaseCallback callback) {
        Map<String,Object> user = new HashMap<>();
        user.put("username",username);
        user.put("email",email);
        user.put("type",usertype);
        user.put("name", name);
        user.put("dni",dni);
        user.put("telefono",telefono);
        //user.put("idUser", mAuth.getUid());
        db.collection("Persona")
                .add(user)
                .addOnSuccessListener(documentReference -> callback.onResponse(true))
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onResponse(false);
                    }
                });

    }

    public void saveGrupo(String numero, final FirebaseCallback callback) {
        Map<String,Object> grupo = new HashMap<>();
        grupo.put("numero",numero);
        //grupo.put("idUser", mAuth.getCurrentUser().getUid());
        db.collection("Grupo")
                .add(grupo)
                .addOnSuccessListener(documentReference -> callback.onResponse(true))
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onResponse(false);
                    }
                });
    }


    public void getPersona(final FirebaseCallback callback){
        db.collection("Persona")
                .whereEqualTo("idUser", mAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            response = task.getResult();
                            callback.onResponse(true);
                        } else {
                            callback.onResponse(false);
                        }
                    }
                });
    }

    public void getGrupos(final FirebaseCallback callback){
        db.collection("Grupo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            response = task.getResult();
                            callback.onResponse(true);
                        } else {
                            callback.onResponse(false);
                        }
                    }
                });
    }

    public void getUsuarios(final FirebaseCallback callback){
        db.collection("Persona")
                .whereEqualTo("type", "ciudadano")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            response = task.getResult();
                            callback.onResponse(true);
                        } else {
                            callback.onResponse(false);
                        }
                    }
                });
    }

    public void getBarrenderos(final FirebaseCallback callback){
        db.collection("Persona")
                .whereEqualTo("type", "barrendero")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            response = task.getResult();
                            callback.onResponse(true);
                        } else {
                            callback.onResponse(false);
                        }
                    }
                });
    }

    public void getUsuarioPorEmail(String email, final FirebaseCallback callback){
        db.collection("Persona")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            response = task.getResult();
                            callback.onResponse(true);
                        } else {
                            callback.onResponse(false);
                        }
                    }
                });
    }

    public void getTarea(String grupoUser,final FirebaseCallback callback){

        db.collection("Tareas")
                .whereEqualTo("Grupo", grupoUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.e("TAREA", "dentro de tarea");
                            response = task.getResult();
                            callback.onResponse(true);
                        } else {
                            Log.e("TAREA", "no ha ido a la tarea");
                            callback.onResponse(false);
                        }
                    }
                });
    }


    public void modificarPersona(String username, String name, String dni, String tlf, String email) {
        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("email", email);
        user.put("name", name);
        user.put("dni", dni);
        user.put("telefono", tlf);


        //db.collection("Persona").document(mAuth.getCurrentUser().getUid()).update(user);

        db.collection("Persona")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult().getDocuments().get(0);
                            db.collection("Persona").document(doc.getId()).update(user);
                        }
                    }
                });
    }

    public void asignarResponsable(String id, FirebaseCallback callback){

        Map<String,Object> responsable = new HashMap<>();
        responsable.put("Responsable",""+mAuth.getUid());
        responsable.put("Estado","EnCurso");

        Log.e("ASIGNAR","dentro de asignar");
        Log.e("IDUSER",""+mAuth.getUid());
        Log.e("ASIGNAR2","Fin asignar");

        DocumentReference ref = db.collection("Tareas").document(id);
        ref.update(responsable)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void documentReference)  {
                        callback.onResponse(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onResponse(false);
                    }
                });

    }

    public void asignarGrupo(String id, String gr, FirebaseCallback callback){

        Map<String,Object> barrendero = new HashMap<>();
        barrendero.put("Grupo",gr);

        DocumentReference ref = db.collection("Persona").document(id);
        ref.update(barrendero)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void documentReference)  {
                        callback.onResponse(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onResponse(false);
                    }
                });

    }

    public void convertirBarrendero(String id, FirebaseCallback callback){

        Map<String,Object> persona = new HashMap<>();
        persona.put("type","barrendero");

        DocumentReference ref = db.collection("Persona").document(id);
        ref.update(persona)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                    public void onSuccess(Void documentReference)  {
                        callback.onResponse(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onResponse(false);
                    }
                });

    }

    public void modificarDatos(String id, String username, String tlf, String email, FirebaseCallback callback){

        Map<String,Object> persona = new HashMap<>();
        persona.put("telefono", tlf);
        persona.put("username", username);
        persona.put("email", email);

        DocumentReference ref = db.collection("Persona").document(id);
        ref.update(persona)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void documentReference)  {
                        callback.onResponse(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onResponse(false);
                    }
                });
    }

    public void saveDesperfecto(String direccion, String descripcion, final FirebaseCallback callback){
        Map<String,Object> desperfecto = new HashMap<>();
        desperfecto.put("direccion", direccion);
        desperfecto.put("descripcion", descripcion);
        desperfecto.put("email", mAuth.getCurrentUser().getEmail());
        db.collection("Desperfecto").add(desperfecto)
                .addOnSuccessListener(documentReference -> callback.onResponse(true))
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onResponse(false);
                    }
                });;
    }

    public void saveQueja(String descripcion, final FirebaseCallback callback){
        Map<String,Object> queja = new HashMap<>();
        queja.put("descripcion", descripcion);
        queja.put("email", mAuth.getCurrentUser().getEmail());
        db.collection("Queja").add(queja)
                .addOnSuccessListener(documentReference -> callback.onResponse(true))
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onResponse(false);
                    }
                });;
    }

    public FirebaseUser getUser(){
        return mAuth.getCurrentUser();
    }

    public QuerySnapshot getResponse() {
        return response;
    }

    public void logout(final FirebaseCallback callback) {
        mAuth.signOut();
        callback.onResponse(true);
    }


}

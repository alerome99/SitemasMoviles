package com.example.urclean.firebase;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.urclean.model.Grupo;
import com.example.urclean.model.Notificacion;
import com.example.urclean.model.Queja;
import com.example.urclean.model.Respuesta;
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
                         String dni, String fecha, final FirebaseCallback callback) {
        Map<String,Object> user = new HashMap<>();
        user.put("username",username);
        user.put("email",email);
        user.put("type",usertype);
        user.put("name", name);
        user.put("dni",dni);
        user.put("telefono",telefono);
        user.put("idUser", mAuth.getUid());
        user.put("fecha",fecha);
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

    public void saveGrupo(Grupo g, final FirebaseCallback callback) {
        Map<String,Object> grupo = new HashMap<>();
        grupo.put("numero",g.getNumero());
        grupo.put("codigo", g.getCodigoPostal());
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

    public void saveTarea(String email,String direccion, String codPostal, String descripcion, final FirebaseCallback callback){
        Map<String,Object> tarea = new HashMap<>();
        tarea.put("direccion", direccion);
        tarea.put("codPostal", codPostal);
        tarea.put("descripcion", descripcion);
        tarea.put("email", email);

        db.collection("Tareas")
                .add(tarea)
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

    public void getQuejas(final FirebaseCallback callback){
        db.collection("Queja")
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

    public void getCodigosPostales(final FirebaseCallback callback){
            db.collection("CodigoPostal")
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

    public void getNotificacionesSupervisor(final FirebaseCallback callback){
        db.collection("Notificacion")
                .whereEqualTo("estado", "ENPROCESO")
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

    public void getNotificacionSupervisorPorEmail(String email, final FirebaseCallback callback){
        db.collection("Notificacion")
                .whereEqualTo("estado", "ENPROCESO").whereEqualTo("email", email)
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

    public void getNotificacionBarrenderosPorEmail(String email, final FirebaseCallback callback){
        db.collection("Respuesta")
                .whereEqualTo("estado", "NOVISTO").whereEqualTo("emailBarrendero", email)
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

    public void getNotificacionesBarrenderos(final FirebaseCallback callback){
        db.collection("Respuesta")
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

    public void getNotificacionesQuejas(String email,final FirebaseCallback callback){
        db.collection("Queja")
                .whereEqualTo("email",email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            response = task.getResult();
                            callback.onResponse(true);
                        }else{
                            callback.onResponse(false);
                        }
                    }
                });
    }

    public void getCurrentUser(final FirebaseCallback callback){
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

    public void getQuejaPorEmailTitulo(String email, String titulo, final FirebaseCallback callback){
        db.collection("Queja")
                .whereEqualTo("email", email).whereEqualTo("titulo", titulo)
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

    public void getTareasPersona(final FirebaseCallback callback){

        Log.e("GTP", "dentro");
        db.collection("Tareas")
                .whereEqualTo("Responsable",getUser().getUid())
                .whereEqualTo("Estado","Completada")
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

    public void getQueja(String email, final FirebaseCallback callback){
        db.collection("Queja")
                .whereEqualTo("email",email)
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

    public void getDesperfecto(String email, final FirebaseCallback callback){
        db.collection("Desperfecto")
                .whereEqualTo("email",email)
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

    public void getIncidenciaLimpieza(String email, final FirebaseCallback callback){
        db.collection("Tareas")
                .whereEqualTo("email",email)
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

    public void actualizarEstadoNotificacionBarrendero(String id, String est, FirebaseCallback callback){

        Map<String,Object> notificacion = new HashMap<>();
        notificacion.put("estado",est);

        DocumentReference ref = db.collection("Notificacion").document(id);
        ref.update(notificacion)
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

    public void actualizarEstadoRespuesta(String id, FirebaseCallback callback){

        Map<String,Object> respuesta = new HashMap<>();
        respuesta.put("estado","VISTO");

        DocumentReference ref = db.collection("Respuesta").document(id);
        ref.update(respuesta)
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

    public void addFoto(String id, String path, FirebaseCallback callback){

        Map<String,Object> usuario = new HashMap<>();
        usuario.put("Foto",path);

        DocumentReference ref = db.collection("Persona").document(id);
        ref.update(usuario)
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

    public void cambiarEstadoQueja(String id, String estado, FirebaseCallback callback){

        Map<String,Object> queja = new HashMap<>();
        if(estado.equals("1")){
            queja.put("estado", "RECIBIDA");
        }
        if(estado.equals("2")){
            queja.put("estado", "SOLUCIONADA");
        }

        DocumentReference ref = db.collection("Queja").document(id);
        ref.update(queja)
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

    public void saveDesperfecto(String direccion, String lat, String lng, String descripcion, final FirebaseCallback callback){
        Map<String,Object> desperfecto = new HashMap<>();
        desperfecto.put("direccion", direccion);
        desperfecto.put("lat", lat);
        desperfecto.put("lng", lng);
        desperfecto.put("descripcion", descripcion);
        desperfecto.put("email", mAuth.getCurrentUser().getEmail());
        db.collection("Desperfecto").add(desperfecto)
                .addOnSuccessListener(documentReference -> callback.onResponse(true))
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onResponse(false);
                    }
                });
    }

    public void saveQueja(Queja q, final FirebaseCallback callback){
        Map<String,Object> queja = new HashMap<>();
        q.setEmail(mAuth.getCurrentUser().getEmail());
        queja.put("descripcion", q.getDescripcion());
        queja.put("titulo", q.getTitulo());
        queja.put("email", q.getEmail());
        queja.put("estado", q.getEstado().toString());
        db.collection("Queja").add(queja)
                .addOnSuccessListener(documentReference -> callback.onResponse(true))
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onResponse(false);
                    }
                });
    }

    public void crearRespuesta(Respuesta r, final FirebaseCallback callback){
        Map<String,Object> respuesta = new HashMap<>();
        respuesta.put("justifacion", r.getRazon());
        respuesta.put("respuesta", r.getRespuesta());
        respuesta.put("email", r.getEmail());
        respuesta.put("emailBarrendero", r.getEmailBarrendero());
        respuesta.put("estado", r.getEstado().toString());
        db.collection("Respuesta").add(respuesta)
                .addOnSuccessListener(documentReference -> callback.onResponse(true))
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onResponse(false);
                    }
                });
    }

    public void crearNotificacion(Notificacion n, final FirebaseCallback callback){
        Map<String,Object> notificacion = new HashMap<>();
        notificacion.put("razon", n.getDescripcion());
        notificacion.put("grupo", n.getGrupo());
        notificacion.put("email", n.getEmail());
        notificacion.put("estado", n.getEstado());
        notificacion.put("grupoAnterior", n.getGrupoAnterior());
        notificacion.put("estado",n.getEstado().toString());
        db.collection("Notificacion").add(notificacion)
                .addOnSuccessListener(documentReference -> callback.onResponse(true))
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onResponse(false);
                    }
                });;
    }

    public void marcarCompletadaTarea(String id, FirebaseCallback callback){

        DocumentReference ref = db.collection("Tareas").document(id);
        ref.update("Estado","Completada")
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

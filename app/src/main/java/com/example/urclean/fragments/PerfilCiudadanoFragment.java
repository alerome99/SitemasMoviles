package com.example.urclean.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class PerfilCiudadanoFragment extends Fragment implements View.OnTouchListener{

    private FirebaseConnection connection;
    TextView textViewEmail;
    TextView textViewName;
    TextView textViewUsername;
    TextView testViewPhone;
    TextView textViewDni;
    ImageView imageViewPhoto;

    private StorageReference mStorage;
    private static final int GALLERY_INTENT = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil_ciudadano, container, false);

        textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewName = view.findViewById(R.id.textViewName);
        textViewUsername = view.findViewById(R.id.textViewUsername);
        testViewPhone = view.findViewById(R.id.textViewTelefono);
        textViewDni = view.findViewById(R.id.textViewDni);
        imageViewPhoto = view.findViewById(R.id.imageViewPhoto);
        mStorage = FirebaseStorage.getInstance().getReference();


        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });
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
                    String url  = "";
                    for (QueryDocumentSnapshot document : connection.getResponse()){
                        email = (String) document.get("email");
                        name = (String) document.get("name");
                        username = (String) document.get("username");
                        phone = (String) document.get("telefono");
                        dni = (String) document.get("dni");
                        url = (String) document.get("Foto");
                    }
                    if(url!=null){
                        Uri path = Uri.parse(url);
                        //imageViewPhoto.setImageURI(path);
                        Picasso.get().load(path).into(imageViewPhoto);
                        //Glide.with(getActivity()).load(path).fitCenter().centerCrop().into(imageViewPhoto);
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

    private void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GALLERY_INTENT){
            Uri path=data.getData();
            StorageReference filePath = mStorage.child("fotos").child(path.getLastPathSegment());
            final UploadTask uploadTask = filePath.putFile(path);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();

                            }
                            return filePath.getDownloadUrl();

                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Picasso.get().load(task.getResult().toString()).into(imageViewPhoto);
                                String pathS = task.getResult().toString();
                                connection.getUsuarioPorEmail(textViewEmail.getText().toString(), correct -> {
                                    if (correct){
                                        if (connection.getResponse().isEmpty() || connection.getResponse() == null){
                                        }else{
                                            String id = "";
                                            for (QueryDocumentSnapshot document : connection.getResponse()) {
                                                id = (String) document.getId();
                                            }
                                            connection.addFoto(id, pathS, new FirebaseCallback() {
                                                @Override
                                                public void onResponse(boolean correct) {
                                                    if (correct) {
                                                    } else {
                                                    }
                                                }
                                            });
                                        }
                                    }else{
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
                }
            });
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Fragment selectedFragment = new EditarPerfilCiudadanoFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
        return false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
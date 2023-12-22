package com.example.fastliv.cotroller;

import static android.content.ContentValues.TAG;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.example.fastliv.MainActivity;
import com.example.fastliv.model.Utilisateur;

import com.example.fastliv.ui.Inscription;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.Executor;

public class UtilisateurBDD {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    public void addUserToCollection(Utilisateur u){
        Utilisateur userWithoutPassword = new Utilisateur(u.getEmail(),
                u.getTelephone(),u.getRole(), u.getImmatriculation());
        Task<DocumentReference> dbCol = db.collection("utilisateurs")
                .add(userWithoutPassword)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d("djily", "Ajouté avec success");
                        }else {

                            Log.d("djily", "Ajout echoué");
                        }
                    }
                });
    }


}

package com.example.fastliv.cotroller;

import static android.content.ContentValues.TAG;


import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fastliv.MainActivity;
import com.example.fastliv.model.Utilisateur;

import com.example.fastliv.ui.Connexion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;



public class UtilisateurBDD {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();




    public void addUserToCollection(Utilisateur u){
        Utilisateur userWithoutPassword = new Utilisateur(u.getEmail(),
                u.getTelephone(),u.getRole(), u.getImmatriculation(), u.getStatutChauffeur());
        db.collection("utilisateurs")
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

        /*
        db.collection("utilisateurs").document("Clients")
                .set(userWithoutPassword, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("djily", "Ajouté avec success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("djily", "Ajout echoué");
                    }
                });
         */
    }




}

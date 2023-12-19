package com.example.fastliv.cotroller;

import static android.content.ContentValues.TAG;

import android.util.Log;


import com.example.fastliv.model.Utilisateur;

import com.google.firebase.firestore.FirebaseFirestore;

public class UtilisateurBDD {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Create a new user with a first and last name
    public void addUtilisateur(Utilisateur u){
        db.collection("utilisateurs").document("user1")
                .set(u)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot written with ID: " ))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }
}

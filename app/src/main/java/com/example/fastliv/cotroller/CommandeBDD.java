package com.example.fastliv.cotroller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fastliv.model.Commande;
import com.example.fastliv.model.Produit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandeBDD {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public CommandeBDD(){}



    public void addCommandeToCollection(Commande c){

        db.collection("commandes")
                .add(c)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d("djily", "Commande jouté avec success");
                        }else {

                            Log.d("djily", "Ajout commande echoué");
                        }
                    }
                });
    }

    public void addProductToPanier(Produit produit, Context context) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference panierRef = db.collection("paniers").document(userId);
        Map<String, Object> produitToAdd = new HashMap<>();
        produitToAdd.put("nom", produit.getNom());
        produitToAdd.put("quantite", produit.getQuantite());
        produitToAdd.put("prix", produit.getPrix());
        produitToAdd.put("image", produit.getImage());

        panierRef.update("produits", FieldValue.arrayUnion(produitToAdd))
                .addOnSuccessListener(aVoid ->  Toast.makeText(context, "Produit ajouté", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> {
                    if (e instanceof FirebaseFirestoreException &&
                            ((FirebaseFirestoreException) e).getCode() == FirebaseFirestoreException.Code.NOT_FOUND) {
                        Map<String, Object> newPanier = new HashMap<>();
                        List<Map<String, Object>> produits = new ArrayList<>();
                        produits.add(produitToAdd);
                        newPanier.put("produits", produits);
                        panierRef.set(newPanier)
                                .addOnSuccessListener(aVoid -> Log.d("djily", "Nouveau panier créé et produit ajouté."))
                                .addOnFailureListener(e2 -> Log.e("djily", "Erreur lors de la création du panier.", e2));
                    } else {
                        Log.e("ClientActivity", "Erreur lors de l'ajout du produit au panier.", e);
                    }
                });
    }

}

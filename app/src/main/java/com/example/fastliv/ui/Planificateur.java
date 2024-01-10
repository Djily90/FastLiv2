package com.example.fastliv.ui;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastliv.R;
import com.example.fastliv.cotroller.CommandeAdapter;
import com.example.fastliv.cotroller.ProductAdapter;
import com.example.fastliv.model.Commande;
import com.example.fastliv.model.Produit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Planificateur extends AppCompatActivity {

    private RecyclerView revCommandes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planificateur);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Commande> listCommandes = new ArrayList<Commande>();
        db.collection("commandes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("djily", document.getId() + " Date => " + document.get("dateLivraison"));
                                listCommandes.add(new Commande(document.get("emailClient").toString(),document.get("idClient").toString(), (GeoPoint) document.get("adresse"),document.get("statut").toString(), document.getTimestamp("dateLivraison").toDate()));
                            }
                            revCommandes = findViewById(R.id.revCommandes);
                            revCommandes.setLayoutManager(new LinearLayoutManager(Planificateur.this));
                            revCommandes.setAdapter(new CommandeAdapter(Planificateur.this, listCommandes));
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    public void goToPlanification() {
        Intent myInt = new Intent(Planificateur.this, Inscription.class);
        startActivity(myInt);
        //         startActivity(new Intent(Client.this, Panier.class));
    }


}
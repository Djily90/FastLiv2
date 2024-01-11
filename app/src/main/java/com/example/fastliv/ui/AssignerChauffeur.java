package com.example.fastliv.ui;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastliv.MainActivity;
import com.example.fastliv.R;
import com.example.fastliv.cotroller.ChauffeurAdapter;
import com.example.fastliv.model.Commande;
import com.example.fastliv.model.Utilisateur;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AssignerChauffeur extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView revChauffeur;
    ImageView btnRetourToPlanif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigner_chauffeur);

        btnRetourToPlanif = findViewById(R.id.btn_retour_to_planificateur);
        btnRetourToPlanif.setOnClickListener(this);



        Intent i= getIntent();
        Double lati = Double.valueOf(i.getStringExtra("adresseLivraison").split(",")[0]);
        Double longi = Double.valueOf(i.getStringExtra("adresseLivraison").split(",")[1]);
        GeoPoint adr = new GeoPoint(lati, longi);
       Commande commandeSelectionner = new Commande(
                i.getStringExtra("emailClient"),
                i.getStringExtra("idClient"),
                adr,
                i.getStringExtra("statutCommande"),
                Calendar.getInstance().getTime(),
               i.getStringExtra("emailChauffeur"));



       // i.getStringExtra("name");
       // Log.d("djily", "date liv recu => " + commandeSelectionner.getAdresse().toString());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Utilisateur> listChauffeurs = new ArrayList<Utilisateur>();
        db.collection("utilisateurs")
                .whereEqualTo("role", "Chauffeur")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //listChauffeurs.add(new Chauffeur(document.get("email").toString(),"df", document.get("numero"), document.get("role"), document.get("immatriculation"), document.get("statutChauffeur")));

                                Log.d("djily", "email chauffeur => " + document.get("email").toString());
                                listChauffeurs.add(new Utilisateur(
                                        "",
                                        (String) document.get("email"),
                                        (String) document.get("telephone"),
                                        (String) document.get("role"),
                                        (String) document.get("immatriculation"),
                                        (String) document.get("statutChauffeur")
                                ));
                            }
                            revChauffeur = findViewById(R.id.revAssignerChauffeur);
                            revChauffeur.setLayoutManager(new LinearLayoutManager(AssignerChauffeur.this));
                            revChauffeur.setAdapter(new ChauffeurAdapter(AssignerChauffeur.this, listChauffeurs, commandeSelectionner));
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }


    @Override
    public void onClick(View v) {
        if (v == btnRetourToPlanif){
            Intent myInt = new Intent(AssignerChauffeur.this, Planificateur.class);
            startActivity(myInt);
        }
    }
}
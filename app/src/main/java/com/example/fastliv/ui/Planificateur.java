package com.example.fastliv.ui;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastliv.MainActivity;
import com.example.fastliv.R;
import com.example.fastliv.cotroller.CommandeAdapter;
import com.example.fastliv.cotroller.ProductAdapter;
import com.example.fastliv.model.Commande;
import com.example.fastliv.model.Produit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Planificateur extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView revCommandes;
    ImageView btnLogOutPlanificateur;
    FirebaseAuth myAuth;
    TextView textViewTitrePlanif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planificateur);

        btnLogOutPlanificateur = findViewById(R.id.btn_logOut_planificateur);
        btnLogOutPlanificateur.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent myInt = new Intent(Planificateur.this, MainActivity.class);
                startActivity(myInt);
            }
        });

        textViewTitrePlanif = findViewById(R.id.titre_planificateur);
        textViewTitrePlanif.setOnClickListener(this);

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
                              //  com.google.firebase.firestore.GeoPoint geoPointFirestore = (com.google.firebase.firestore.GeoPoint) document.get("adresse");
                              //  org.osmdroid.util.GeoPoint geoPointOsmdroid = new org.osmdroid.util.GeoPoint(geoPointFirestore.getLatitude(), geoPointFirestore.getLongitude());
                                listCommandes.add(new Commande((String) document.get("emailClient")
                                        ,(String) document.get("idClient")
                                        ,(GeoPoint) document.get("adresse")
                                        ,(String) document.get("statut"),
                                        document.getTimestamp("dateLivraison").toDate()
                                        ,(String) document.get("emailChauffeur")));
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

    @Override
    public void onClick(View v) {
        if (v == textViewTitrePlanif) {

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("utilisateurs")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    if (document.get("email").toString().
                                            equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                                        Intent myIntent = new Intent(v.getContext(), updateUser.class);
                                        myIntent.putExtra("telephone", document.get("telephone").toString());
                                        myIntent.putExtra("immatriculation", document.get("immatriculation").toString());
                                        myIntent.putExtra("email", document.get("email").toString());
                                        myIntent.putExtra("statutChauffeur", document.get("statutChauffeur").toString());



                                        startActivity(myIntent);


                                    }
                                }
                            } else {
                                Log.d("djily", "Error getting documents: ", task.getException());
                            }
                        }
                    });

        }

    }


}
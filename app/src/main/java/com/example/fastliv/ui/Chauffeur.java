package com.example.fastliv.ui;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
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
import com.example.fastliv.cotroller.ChauffeurAdapter;
import com.example.fastliv.cotroller.LivraisonAdapter;
import com.example.fastliv.model.Commande;
import com.example.fastliv.model.Livraison;
import com.example.fastliv.model.Utilisateur;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import com.google.firebase.firestore.QueryDocumentSnapshot;import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Chauffeur extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView revLivraison;
    TextView textViewTitreChauffeur;
    ImageView btnRetourToMainActivity, voirMap;
    List<Livraison> listLivraisons ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livraison);
        listLivraisons = new ArrayList<Livraison>();
        String getCurrentUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        textViewTitreChauffeur = findViewById(R.id.titre_assignerChauffeur);
        textViewTitreChauffeur.setOnClickListener(this);

        btnRetourToMainActivity = findViewById(R.id.btn_deconnecter_chauffeur);
        btnRetourToMainActivity.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent myInt = new Intent(Chauffeur.this, MainActivity.class);
                startActivity(myInt);
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("livraison")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //Log.d("djily", "email chauffeur => " + document.get("email").toString());
                               // com.google.firebase.firestore.GeoPoint geoPointFirestore = (com.google.firebase.firestore.GeoPoint) document.get("adresse");
                              //  org.osmdroid.util.GeoPoint geoPointOsmdroid = new org.osmdroid.util.GeoPoint(geoPointFirestore.getLatitude(), geoPointFirestore.getLongitude());
                                if(document.get("emailChauffeur").toString().equals(getCurrentUserEmail)
                                        || document.get("statutLivraison").toString().equals("en cours")){
                                    listLivraisons.add(new Livraison(
                                            (String) document.get("statutLivraison"),
                                            (GeoPoint) document.get("adresse"),
                                            (String) document.get("emailClient"),
                                            (String) document.get("emailChauffeur")
                                    ));
                                }

                            }
                            revLivraison = findViewById(R.id.revChauffeur);
                            revLivraison.setLayoutManager(new LinearLayoutManager(Chauffeur.this));
                            revLivraison.setAdapter(new LivraisonAdapter(Chauffeur.this, listLivraisons));
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        voirMap = findViewById(R.id.btnVoirMap);
        voirMap.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent myInt = new Intent(Chauffeur.this, MapsActivity2.class);

                List<GeoPoint> listGeopoints = new ArrayList<GeoPoint>();
                String emailChauffeur = "";

                for (Livraison l : listLivraisons){

                    if(l.getStatutLivraison().equals("accepté")){
                        listGeopoints.add(l.getAdresse());
                        emailChauffeur = l.getEmailChauffeur();
                    }
                }

               // com.google.firebase.firestore.GeoPoint geoPointFirestore = (com.google.firebase.firestore.GeoPoint) document.get("adresse");
                //org.osmdroid.util.GeoPoint geoPointOsmdroid = new org.osmdroid.util.GeoPoint(geoPointFirestore.getLatitude(), geoPointFirestore.getLongitude());

                ArrayList<com.google.firebase.firestore.GeoPoint> geoPointFirestore = (ArrayList<com.google.firebase.firestore.GeoPoint>) listGeopoints;
                ArrayList<org.osmdroid.util.GeoPoint> geoPointOsmdroid = new ArrayList<org.osmdroid.util.GeoPoint>();
                for (com.google.firebase.firestore.GeoPoint geoPoint: geoPointFirestore){
                    geoPointOsmdroid.add(new org.osmdroid.util.GeoPoint(geoPoint.getLatitude(), geoPoint.getLongitude()));
                }
                myInt.putParcelableArrayListExtra("ListGeoPoints", geoPointOsmdroid);
                myInt.putExtra("emailChauffeur", emailChauffeur);
                startActivity(myInt);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == textViewTitreChauffeur) {

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
                                        myIntent.putExtra("uuid", document.get("uuid").toString());
                                        myIntent.putExtra("role", document.get("role").toString());
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
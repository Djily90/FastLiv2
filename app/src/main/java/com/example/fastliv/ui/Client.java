package com.example.fastliv.ui;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fastliv.MainActivity;
import com.example.fastliv.R;
import com.example.fastliv.cotroller.ProductAdapter;
import com.example.fastliv.model.Produit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Client extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView revProducts;
    ImageView btnGoToPanier, btnSignOut;
    private FirebaseAuth mAuth;
    TextView textViewTitreClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        btnGoToPanier = findViewById(R.id.btnPanier);
        btnGoToPanier.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent myInt = new Intent(Client.this, Panier.class);
                startActivity(myInt);
            }
        });


        btnSignOut = findViewById(R.id.btn_deconnexion_espace_client);
        btnSignOut.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent myInt5 = new Intent(Client.this, Connexion.class);
                startActivity(myInt5);
            }
        });

        textViewTitreClient = findViewById(R.id.titre_client);
        textViewTitreClient.setOnClickListener(this);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Produit> listProduits = new ArrayList<Produit>();
        db.collection("produits")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("djily", document.getId() + " => " + document.get("quantité"));
                                listProduits.add(new Produit(document.get("nom").toString(), document.get("quantité").toString(),document.get("image").toString(),document.get("prix").toString()));
                            }
                            revProducts = findViewById(R.id.revProducts);
                            revProducts.setLayoutManager(new LinearLayoutManager(Client.this));
                            revProducts.setAdapter(new ProductAdapter(Client.this, listProduits));
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        if (v == textViewTitreClient) {

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
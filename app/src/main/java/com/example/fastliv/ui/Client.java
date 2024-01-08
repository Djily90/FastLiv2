package com.example.fastliv.ui;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.fastliv.R;
import com.example.fastliv.cotroller.ProductAdapter;
import com.example.fastliv.model.Produit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Client extends AppCompatActivity {

    private RecyclerView revProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);



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


}
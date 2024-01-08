package com.example.fastliv;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.fastliv.model.Produit;
import com.example.fastliv.ui.Connexion;
import com.example.fastliv.ui.Inscription;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnInscription, btnConnexion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInscription = findViewById(R.id.btn_inscription);
        btnInscription.setOnClickListener(this);

        btnConnexion = findViewById(R.id.btn_connexion);
        btnConnexion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnInscription){
            Intent myIntent1 = new Intent(MainActivity.this, Inscription.class);
            startActivity(myIntent1);
        }

        if (v == btnConnexion){
            Intent myIntent = new Intent(MainActivity.this, Connexion.class);
            startActivity(myIntent);




        }
    }

}
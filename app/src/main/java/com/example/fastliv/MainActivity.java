package com.example.fastliv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fastliv.ui.Connexion;
import com.example.fastliv.ui.Inscription;

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
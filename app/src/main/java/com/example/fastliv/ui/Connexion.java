package com.example.fastliv.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.fastliv.MainActivity;
import com.example.fastliv.R;

import java.sql.Connection;

public class Connexion extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);



    }

    public void goBack() {
        Intent myIntent1 = new Intent(Connexion.this, MainActivity.class);
        startActivity(myIntent1);


    }

}
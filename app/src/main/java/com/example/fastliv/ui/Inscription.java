package com.example.fastliv.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fastliv.MainActivity;
import com.example.fastliv.R;

public class Inscription extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);


    }


    public void goBack() {
        Intent myIntent1 = new Intent(Inscription.this, MainActivity.class);
        startActivity(myIntent1);


    }
}
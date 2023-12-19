package com.example.fastliv.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fastliv.MainActivity;
import com.example.fastliv.R;
import com.example.fastliv.cotroller.UtilisateurBDD;
import com.example.fastliv.model.Utilisateur;

public class Inscription extends AppCompatActivity  implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    Button btnInscrire;
    EditText inputEmail;
    EditText inputPassword;
    EditText inputPhone;
    String inputRole;
    EditText inputImmatriculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        btnInscrire = findViewById(R.id.btn_inscrire);
        btnInscrire.setOnClickListener(this);

        Spinner sp = findViewById(R.id.inscrire_role);
        sp.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this
                , R.array.Roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        inputEmail = findViewById(R.id.inscrire_email);
        inputPassword = findViewById(R.id.inscrire_password);
        inputPhone = findViewById(R.id.inscrire_phone);
        inputRole = findViewById(R.id.inscrire_role).toString();
        inputImmatriculation = findViewById(R.id.inscrire_immatriculation);



    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String[] choix = getResources().getStringArray(R.array.Roles);
        inputRole = choix[position];
        if (inputRole.equals("Chauffeur")) {
            inputImmatriculation.setVisibility(View.VISIBLE);
        }else {
            inputImmatriculation.setVisibility(View.INVISIBLE);
            inputImmatriculation.setText("");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if(v == btnInscrire) {
            Log.d("success", inputEmail.getText().toString());
            Log.d("success", inputPassword.getText().toString());
            Log.d("success", inputPhone.getText().toString());
            Log.d("success", inputRole);
            Log.d("success", inputImmatriculation.getText().toString());

            Utilisateur user = new Utilisateur(inputEmail.getText().toString(),
                    inputPassword.getText().toString(), inputPhone.getText().toString(),
                    inputRole, inputImmatriculation.getText().toString());

            UtilisateurBDD userBDD = new UtilisateurBDD();
            userBDD.addUtilisateur(user);
        }
    }


    public void goBack() {
        Intent myIntent1 = new Intent(Inscription.this, MainActivity.class);
        startActivity(myIntent1);


    }
}
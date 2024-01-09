package com.example.fastliv.ui;

import static androidx.core.content.ContextCompat.startActivity;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.example.fastliv.MainActivity;
import com.example.fastliv.R;
import com.example.fastliv.cotroller.UtilisateurBDD;
import com.example.fastliv.model.Utilisateur;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Inscription extends AppCompatActivity  implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    Button btnInscrire;
    EditText inputEmail;
    EditText inputPassword;
    EditText inputPhone;
    String inputRole;
    EditText inputImmatriculation;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    private UtilisateurBDD utilisateurBDD;
    // [END declare_auth]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        utilisateurBDD = new UtilisateurBDD();
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

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

            int sizePassword = inputPassword.getText().toString().length();

            if (inputEmail.getText().toString().isEmpty()){
                inputEmail.setError("Email ne peut pas être nul");
            }
            else if (inputPassword.getText().toString().isEmpty()){
                inputPassword.setError("Mot de passe ne peut pas être nul");
            }
            else if (sizePassword < 6){
                inputPassword.setError("Mot de passe doit avoir 6 caratères");
            }
            else if (inputPhone.getText().toString().isEmpty()){
                inputPhone.setError("Téléphone ne peut pas être nul");
            }
            else if (inputImmatriculation.getText().toString().isEmpty() && inputRole.equals("Chauffeur")){
                inputImmatriculation.setError("Immatriculation ne peut pas être nul");
            }
            else {
                Utilisateur user = new Utilisateur(inputEmail.getText().toString(),
                inputPhone.getText().toString(),
                inputRole, inputImmatriculation.getText().toString());
                createUser(user, inputPassword.getText().toString());
            }

        }
    }

    public void createUser(Utilisateur u, String password){
        mAuth.createUserWithEmailAndPassword(u.getEmail(), password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            Toast.makeText(Inscription.this, "Utilisateur ajouté", Toast.LENGTH_SHORT).show();
                            u.setUuid(user.getUid());
                            utilisateurBDD.addUserToCollection(u);
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("djily", "createUserWithEmail:success");
                            Log.d("djily", user.getEmail());
                            Intent myIntent1 = new Intent(Inscription.this, MainActivity.class);
                            startActivity(myIntent1);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Inscription.this, "Erreur de l'ajout", Toast.LENGTH_SHORT).show();
                            Log.w("djily", "createUserWithEmail:failed");
                        }
                    }
                });
    }


    public void goBack() {
        Intent myIntent1 = new Intent(Inscription.this, MainActivity.class);
        startActivity(myIntent1);
    }
}
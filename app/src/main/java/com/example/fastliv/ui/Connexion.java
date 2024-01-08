package com.example.fastliv.ui;

import static android.content.ContentValues.TAG;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Connexion extends AppCompatActivity  implements View.OnClickListener{

    Button btnConnecter;
    EditText inputEmail;
    EditText inputPassword;


    // [START declare_auth]
    private FirebaseAuth mAuth;
    private UtilisateurBDD utilisateurBDD;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    // [END declare_auth]
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        utilisateurBDD = new UtilisateurBDD();
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btnConnecter = findViewById(R.id.btn_connecte);
        btnConnecter.setOnClickListener(this);



        inputEmail = findViewById(R.id.connexion_email);
        inputPassword = findViewById(R.id.connexion_password);




    }

    @Override
    public void onClick(View v) {
        if(v == btnConnecter) {

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

            else {

                loginUser(inputEmail.getText().toString(), inputPassword.getText().toString());
            }

        }
    }

    public void loginUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("djily", "signInWithEmail:success");

                            Log.d("djily", user.getUid());
                            getUserByUuid(user.getUid());

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("djily", "signInWithEmail:failure", task.getException());

                        }
                    }
                });
    }

    public void getUserByUuid(String uuid){
        db.collection("utilisateurs")
                .whereEqualTo("uuid", uuid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String role="";
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("djily", document.getId() + " => " + document.getString("role"));
                                role = document.getString("role");
                            }
                            if (role.equals("Client")){
                                Log.d("djily", "yesssssssss");
                                Intent myInt = new Intent(Connexion.this, Client.class);
                                startActivity(myInt );
                            }
                        } else {
                            Log.d("djily", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    public void goBack() {
        Intent myIntent1 = new Intent(Connexion.this, MainActivity.class);
        startActivity(myIntent1);


    }

}
package com.example.fastliv.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastliv.R;
import com.example.fastliv.cotroller.PanierAdapter;
import com.example.fastliv.model.Commande;
import com.example.fastliv.model.Produit;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Panier extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView revProducts;
    Button btnSupprimer, validerCommande;
    EditText inputAdresse;
    TextView textViewDateLivraison;
    List<Produit> panierList = new ArrayList<Produit>();
    ImageView btnRetourToPanier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);

        inputAdresse = findViewById(R.id.input_adresse);
        validerCommande = findViewById(R.id.btn_valider_commande);
        validerCommande.setOnClickListener(this);

        textViewDateLivraison = findViewById(R.id.textViewDateChoisie);
        textViewDateLivraison.setOnClickListener(this);

        btnRetourToPanier = findViewById(R.id.btn_retour_panier);
        btnRetourToPanier.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent myInt = new Intent(Panier.this, Client.class);
                startActivity(myInt);
            }
        });


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        DocumentReference panierRef = db.collection("paniers").document(userId);

        panierRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                List<Map<String, Object>> produits = (List<Map<String, Object>>) documentSnapshot.get("produits");
                if (produits != null) {

                    // panierList.clear(); // Clear the current list
                    for (Map<String, Object> produitMap : produits) {
                        Produit produit = new Produit();
                        produit.setNom((String) produitMap.get("nom"));
                        produit.setImage((String) produitMap.get("image"));
                        produit.setPrix((String) produitMap.get("prix"));
                        produit.setQuantite((String) produitMap.get("quantite"));


                        panierList.add(produit);


                    }
                    revProducts = findViewById(R.id.revPaniers);
                    revProducts.setLayoutManager(new LinearLayoutManager(Panier.this));
                    revProducts.setAdapter(new PanierAdapter(Panier.this, panierList));


                }
            }
        }).addOnFailureListener(e -> {
            Log.e("PanierActivity", "Error loading panier", e);
        });


        }

    @Override
    public void onClick(View v) {
        if(v == validerCommande) {
            if (inputAdresse.getText().toString().isEmpty()){
                inputAdresse.setError("Adresse ne peut pas être nul");
            }
            else {

                GeoPoint geoPoint;
                try {
                    geoPoint = getAdresseFinal(inputAdresse.getText().toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                // Convertissez la date de livraison de String en Date
                String dateLivraisonString = textViewDateLivraison.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date dateLivraison;
                try {
                    dateLivraison = sdf.parse(dateLivraisonString);
                } catch (ParseException e) {
                    Toast.makeText(this, "Format de date invalide.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Vérifiez que la date de livraison est dans le futur
                if (dateLivraison != null && dateLivraison.before(new Date())) {
                    Toast.makeText(this, "La date de livraison doit être dans le futur.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Créez la commande avec la date et l'adresse converties
                Commande commande = new Commande();
                commande.setAdresse(geoPoint);
                commande.setStatut("en cours");
                commande.setIdClient(userId);
                commande.setIdClient(userEmail);
                commande.setProduits(panierList);
                commande.setDateLivraison(dateLivraison);

                AddCommandeToFirebase(commande);
            }

        }
        if (v == textViewDateLivraison){
            showDatePickerDialog();
        }
    }

    private GeoPoint getAdresseFinal(String locationName) throws IOException {
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        List<Address> address = geoCoder.getFromLocationName(locationName, 1);
        double latitude = address.get(0).getLatitude();
        double longitude = address.get(0).getLongitude();
        GeoPoint adr = new GeoPoint(latitude, longitude);

        return adr;
    }

    private void showDatePickerDialog() {
        // Get the current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        // Set the date on the TextView
                        String selectedDate = String.format("%02d/%02d/%04d", day, month + 1, year);
                        textViewDateLivraison.setText(selectedDate);
                    }
                }, year, month, day);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }

    private void AddCommandeToFirebase(Commande commande) {

        // Récupérez l'utilisateur actuellement connecté
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "Aucun utilisateur connecté.", Toast.LENGTH_SHORT).show();
            return; // Arrêter la méthode si aucun utilisateur n'est connecté
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("commandes")
                .add(commande)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Commande ajoutée avec succès.", Toast.LENGTH_LONG).show();

                    db.collection("paniers").document(commande.getIdClient())
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Panier.this, "Panier vidée", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Panier.this, Client.class));
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Panier.this, "Panier non vidé", Toast.LENGTH_SHORT).show();
                                }
                            });

                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erreur lors de l'ajout de la commande.", Toast.LENGTH_LONG).show();

                });
    }

    public void goToClient() {
        Intent myInt = new Intent(Panier.this, com.example.fastliv.ui.Client.class);
        startActivity(myInt);
        //         startActivity(new Intent(Client.this, Panier.class));
    }


}



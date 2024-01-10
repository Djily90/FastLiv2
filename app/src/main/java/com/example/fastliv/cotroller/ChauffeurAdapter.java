package com.example.fastliv.cotroller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastliv.R;
import com.example.fastliv.model.Commande;
import com.example.fastliv.model.Livraison;
import com.example.fastliv.model.Utilisateur;
import com.example.fastliv.ui.Client;
import com.example.fastliv.ui.Panier;
import com.example.fastliv.ui.Planificateur;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChauffeurAdapter extends RecyclerView.Adapter<ChauffeurAdapter.ChauffeurViewHolder> {

    private Context context;
    private List<Utilisateur> chauffeurs;
    private ArrayAdapter<CharSequence> dataAdapter;
    private ChauffeurAdapter chauffeurAdapter;
    private Commande commandeChoisi;

    public ChauffeurAdapter(Context context, List<Utilisateur> chauffeurs, Commande commandeSelectionner) {
        this.context = context;
        this.chauffeurs = chauffeurs;
        this.commandeChoisi = commandeSelectionner;


    }

    @NonNull
    @Override
    public ChauffeurViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chauffeur_item, parent, false);
        return new ChauffeurViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChauffeurViewHolder holder, int position) {
        Utilisateur c = chauffeurs.get(position);
        holder.tvEmalChauffeur.setText(c.getEmail());
        holder.tvStatutChauffeur.setText(c.getStatutChauffeur());
        holder.tvNumeroChauffeur.setText(c.getTelephone());


    }

    @Override
    public int getItemCount() {
        return chauffeurs.size();
    }

    public class ChauffeurViewHolder extends RecyclerView.ViewHolder {

        TextView tvEmalChauffeur, tvStatutChauffeur, tvNumeroChauffeur;
        Button btnAssignerChauffeur;
        Activity activity;

        Planificateur planificateur;
        FirebaseFirestore db = FirebaseFirestore.getInstance();




        public ChauffeurViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmalChauffeur = (TextView) itemView.findViewById(R.id.tvEmailChauffeur);
            tvStatutChauffeur = (TextView) itemView.findViewById(R.id.tvStatutChauffeur);
            tvNumeroChauffeur = (TextView) itemView.findViewById(R.id.tvNumeroChauffeur);
            btnAssignerChauffeur = (Button) itemView.findViewById(R.id.btn_assigner_chauffeur);
            activity = new Activity();
            List<Utilisateur> listChauffeurs = new ArrayList<Utilisateur>();
            planificateur = new Planificateur();



            btnAssignerChauffeur.setOnClickListener(new View.OnClickListener() {


               @Override
                public void onClick(View v) {
                   Log.d("djily", " adresse commade choisi => "+commandeChoisi.getAdresse());
                    Utilisateur chauffeurassigner = new Utilisateur();
                    for (Utilisateur u : listChauffeurs){
                        if (u.getEmail() == tvEmalChauffeur.getText().toString()){
                            chauffeurassigner.setEmail(u.getEmail());
                            chauffeurassigner.setImmatriculation(u.getImmatriculation());
                            chauffeurassigner.setStatutChauffeur(u.getStatutChauffeur());
                            chauffeurassigner.setRole(u.getRole());
                            chauffeurassigner.setTelephone(u.getTelephone());
                        }
                    }
                    List<Commande> listCommande = new ArrayList<Commande>();
                    listCommande.add(commandeChoisi);
                    Livraison livr = new Livraison(
                            "en cours",
                            chauffeurassigner,
                            commandeChoisi.getAdresse(),
                            listCommande
                    );

                   AddLivraisonToFirebase(livr);



                }
            });



        }

        private void AddLivraisonToFirebase(Livraison livraison) {

            // Récupérez l'utilisateur actuellement connecté
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser == null) {
                Toast.makeText(context, "Aucun utilisateur connecté.", Toast.LENGTH_SHORT).show();
                return; // Arrêter la méthode si aucun utilisateur n'est connecté
            }

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("livraison")
                    .add(livraison)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(context, "Livraison ajoutée avec succès.", Toast.LENGTH_LONG).show();

                        db.collection("commandes")
                                .whereEqualTo("idClient", commandeChoisi.getIdClient())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                db.collection("commandes").document(document.getId())
                                                        .delete()
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                commandeChoisi.setStatut("assigné");
                                                                db.collection("commandes")
                                                                        .add(commandeChoisi)
                                                                        .addOnSuccessListener(documentReference -> {
                                                                            context.startActivity(new Intent(context, Planificateur.class));
                                                                        })
                                                                        .addOnFailureListener(e -> {

                                                                        });

                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                //Toast.makeText(Panier.this, "Panier non vidé", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }
                                        } else {
                                            Log.d("djily", "Error getting documents: ", task.getException());
                                        }
                                    }
                                });

                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "Erreur lors de l'ajout de la commande.", Toast.LENGTH_LONG).show();

                    });
        }
    }
}
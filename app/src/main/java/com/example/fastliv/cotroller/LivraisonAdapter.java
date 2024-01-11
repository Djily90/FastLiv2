package com.example.fastliv.cotroller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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
import com.example.fastliv.ui.Chauffeur;
import com.example.fastliv.ui.Planificateur;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LivraisonAdapter extends RecyclerView.Adapter<LivraisonAdapter.ChauffeurViewHolder> {

    private Context context;
    private List<Livraison> chauffeurs;
    private ArrayAdapter<CharSequence> dataAdapter;
    private LivraisonAdapter chauffeurAdapter;
    private Commande commandeChoisi;

    public LivraisonAdapter(Context context, List<Livraison> chauffeurs) {
        this.context = context;
        this.chauffeurs = chauffeurs;



    }

    @NonNull
    @Override
    public ChauffeurViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.livraison_item, parent, false);
        return new ChauffeurViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChauffeurViewHolder holder, int position) {
        Livraison l = chauffeurs.get(position);
        holder.tvEmalClientLiv.setText(l.getEmailClient());
        holder.tvEmalChauffeurLiv.setText(l.getEmailChauffeur());
        holder.tvStatutLiv.setText(l.getStatutLivraison());
        Geocoder geoCoder = new Geocoder(context, Locale.getDefault());
        List<Address> addr;
        try {
             addr = geoCoder.getFromLocation(l.getAdresse().getLatitude(), l.getAdresse().getLongitude(), 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
      //  holder.tvAdresseLiv.setText(addr.get(0).getFeatureName()+ addr.get(0).getThoroughfare() +addr.get(0).getLocality());
        holder.tvAdresseLiv.setText(addr.get(0).getLocality());
        holder.tvAdresseComplet.setText(l.getAdresse().getLatitude()+","+l.getAdresse().getLongitude());
        if (l.getStatutLivraison().equals("en cours")){
            holder.btnAceepterLiv.setVisibility(View.VISIBLE);
            holder.btnRefuserLiv.setVisibility(View.VISIBLE);
        }else {
            holder.btnAceepterLiv.setVisibility(View.INVISIBLE);
            holder.btnRefuserLiv.setVisibility(View.INVISIBLE);

        }



    }

    @Override
    public int getItemCount() {
        return chauffeurs.size();
    }

    public class ChauffeurViewHolder extends RecyclerView.ViewHolder {

        TextView tvEmalClientLiv, tvStatutLiv,  tvAdresseLiv, tvAdresseComplet, tvEmalChauffeurLiv;
        Button btnAceepterLiv, btnRefuserLiv;
        Activity activity;

        Planificateur planificateur;
        FirebaseFirestore db = FirebaseFirestore.getInstance();




        public ChauffeurViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmalClientLiv = (TextView) itemView.findViewById(R.id.tvEmailClientLiv);
            tvEmalChauffeurLiv = (TextView) itemView.findViewById(R.id.tvEmailChauffeurLiv);
            tvStatutLiv = (TextView) itemView.findViewById(R.id.tvStatutLiv);
            tvAdresseLiv = (TextView) itemView.findViewById(R.id.tvAdresseLiv);
            tvAdresseComplet = (TextView) itemView.findViewById(R.id.tvAdresseComplet);
            btnAceepterLiv = (Button) itemView.findViewById(R.id.btnAccepterLiv);
            btnRefuserLiv = (Button) itemView.findViewById(R.id.btnRefuserLiv);





            btnAceepterLiv.setOnClickListener(new View.OnClickListener() {


               @Override
                public void onClick(View v) {

                   Double latitude = Double.valueOf(tvAdresseComplet.getText().toString().split(",")[0]);
                   Double longitude = Double.valueOf(tvAdresseComplet.getText().toString().split(",")[1]);
                   GeoPoint adrLiv = new GeoPoint(latitude, longitude);


                Livraison liv = new Livraison(
                        tvStatutLiv.getText().toString(),
                        adrLiv,
                        tvEmalClientLiv.getText().toString(),
                        tvEmalChauffeurLiv.getText().toString()


                );


                   AccepterLivraisonToFirebase(liv);


                }
            });



        }

        private void AccepterLivraisonToFirebase(Livraison livraison) {

            // Récupérez l'utilisateur actuellement connecté
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser == null) {
                Toast.makeText(context, "Aucun chauffeur connecté.", Toast.LENGTH_SHORT).show();
                return; // Arrêter la méthode si aucun utilisateur n'est connecté
            }

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("livraison")
                    .whereEqualTo("emailClient", livraison.getEmailClient())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    db.collection("livraison").document(document.getId())
                                            .delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    livraison.setStatutLivraison("accepté");
                                                    db.collection("livraison")
                                                            .add(livraison)
                                                            .addOnSuccessListener(documentReference -> {
                                                                Toast.makeText(context, "Livraison acceptée.", Toast.LENGTH_LONG).show();
                                                                context.startActivity(new Intent(context, Chauffeur.class));
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
        }


        private void RefuserLivraisonToFirebase(Livraison livraison) {

            // Récupérez l'utilisateur actuellement connecté
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser == null) {
                Toast.makeText(context, "Aucun chauffeur connecté.", Toast.LENGTH_SHORT).show();
                return; // Arrêter la méthode si aucun utilisateur n'est connecté
            }

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("livraison")
                    .whereEqualTo("emailClient", livraison.getEmailClient())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    db.collection("livraison").document(document.getId())
                                            .delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    livraison.setStatutLivraison("refusé");
                                                    db.collection("livraison")
                                                            .add(livraison)
                                                            .addOnSuccessListener(documentReference -> {
                                                                Toast.makeText(context, "Livraison reusée.", Toast.LENGTH_LONG).show();
                                                                context.startActivity(new Intent(context, Chauffeur.class));
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
        }
    }
}
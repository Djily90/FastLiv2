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
import com.example.fastliv.ui.AssignerChauffeur;
import com.example.fastliv.ui.Planificateur;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommandeAdapter extends RecyclerView.Adapter<CommandeAdapter.CommandeViewHolder> {

    private Context context;
    private List<Commande> commandes;
    private ArrayAdapter<CharSequence> dataAdapter;
    private CommandeAdapter panierAdapter;

    public CommandeAdapter(Context context, List<Commande> commandes) {
        this.context = context;
        this.commandes = commandes;

    }

    @NonNull
    @Override
    public CommandeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.commande_item, parent, false);
        return new CommandeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommandeViewHolder holder, int position) {
        Commande c = commandes.get(position);
        holder.tvemailClient.setText(c.getEmailClient());
        String LatLong = String.valueOf(c.getAdresse().getLatitude()) + ","+String.valueOf(c.getAdresse().getLongitude());
        holder.tvAdresse.setText( LatLong);
        holder.tvIdClient.setText(c.getIdClient());
        holder.tvStatut.setText(c.getStatut());
        holder.tvDateLivraison.setText(c.getDateLivraison().toString());


    }

    @Override
    public int getItemCount() {
        return commandes.size();
    }

    public class CommandeViewHolder extends RecyclerView.ViewHolder {

        TextView tvemailClient, tvStatut, tvDateLivraison, tvAdresse, tvIdClient;
        Button btnVoirCommande;
        Activity activity;

        Planificateur planificateur;
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        public CommandeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvemailClient = (TextView) itemView.findViewById(R.id.tvemailClient);
            tvStatut = (TextView) itemView.findViewById(R.id.tvStatutCommande);
            tvDateLivraison = (TextView) itemView.findViewById(R.id.tvDateLivraison);
            tvAdresse = (TextView) itemView.findViewById(R.id.tvAdresseCommande);
            tvIdClient = (TextView) itemView.findViewById(R.id.tvIdClient);
            btnVoirCommande = (Button) itemView.findViewById(R.id.btn_voir_commande);
            if (tvStatut.getText() == "en cours"){
                btnVoirCommande.setVisibility(View.VISIBLE);
            }else {
                btnVoirCommande.setVisibility(View.INVISIBLE);
            }


            btnVoirCommande.setOnClickListener(new View.OnClickListener() {


               @Override
                public void onClick(View v) {
                  // Log.d("djily", "selectionner tv => " + tvDateLivraison.getText());




                   Intent intent = new Intent(context, AssignerChauffeur.class);
                   intent.putExtra("adresseLivraison", tvAdresse.getText());
                   intent.putExtra("dateLivraison", tvDateLivraison.getText());
                   intent.putExtra("idClient", tvIdClient.getText());
                   intent.putExtra("emailClient", tvemailClient.getText());
                   intent.putExtra("statutCommande", tvStatut.getText());
                   v.getContext().startActivity(intent);



                   /* Log.d("djily", "element ajouté " + tvName.getText() + " " + quantite);
                    Log.d("djily",  "id  : " + FirebaseAuth.getInstance().getCurrentUser().getUid());
                    CommandeBDD commandeBDD = new CommandeBDD();
                    String urlImage="";
                    for (Commande c : commandes){
                        if (c.getNom() == tvName.getText()){
                            urlImage = c.getImage();
                        }
                    }
                    Commande c = new Produit(tvName.getText().toString(), quantite, urlImage ,tvPrix.getText().toString());

                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DocumentReference panierRef = db.collection("paniers").document(userId);
                    // Étape 1: Supprimer le produit de la liste locale


                    // Étape 2: Mettre à jour Firestore
                    userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    // Créez une Map représentant le produit à supprimer
                    Map<String, Object> produitToRemove = new HashMap<>();
                    produitToRemove.put("image", p.getImage());
                    produitToRemove.put("nom", p.getNom());
                    produitToRemove.put("prix", p.getPrix());
                    produitToRemove.put("quantite", p.getQuantite());
                    // Ajoutez les autres attributs de Produit si nécessaire

                    // Utilisez FieldValue.arrayRemove pour supprimer le produit du tableau 'produits' dans Firestore
                    panierRef.update("produits", FieldValue.arrayRemove(produitToRemove))
                            .addOnSuccessListener(aVoid -> {
                                Log.d("djily", "Produit retiré du panier");
                            })
                            .addOnFailureListener(e -> Log.e("djily", "Erreur lors de la suppression du produit", e));


*/

                }
            });

        }
    }
}
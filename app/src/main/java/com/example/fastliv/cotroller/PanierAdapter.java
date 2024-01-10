package com.example.fastliv.cotroller;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.fastliv.R;
import com.example.fastliv.model.Produit;
import com.example.fastliv.ui.Panier;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanierAdapter extends RecyclerView.Adapter<PanierAdapter.PanierViewHolder> {

    private Context context;
    private List<Produit> products;
    private ArrayAdapter<CharSequence> dataAdapter;
    private PanierAdapter panierAdapter;

    public PanierAdapter(Context context, List<Produit> products) {
        this.context = context;
        this.products = products;


        dataAdapter = ArrayAdapter.createFromResource(this.context
                , R.array.Quantites, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }

    @NonNull
    @Override
    public PanierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.panier_item, parent, false);
        return new PanierViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PanierViewHolder holder, int position) {
        Produit p = products.get(position);
        String url = p.getImage();
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.ivProduct);
        holder.tvName.setText(p.getNom());
        holder.tvPrix.setText(p.getPrix());
        holder.spinner.setAdapter(dataAdapter);


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class PanierViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProduct;
        TextView tvName, tvPrix;
        public Spinner spinner;
        String quantite;
        Button btnSupprimer;
        Activity activity;
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        public PanierViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct = (ImageView) itemView.findViewById(R.id.ivProduct);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvPrix = (TextView) itemView.findViewById(R.id.tvPrix);
            btnSupprimer = (Button) itemView.findViewById(R.id.btn_supprimer);
            spinner = (Spinner)itemView.findViewById(R.id.spinner_prix);
            activity = new Activity();
            List<Produit> listProduitSelectione = new ArrayList<Produit>();


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // On selecting a spinner item
                    String item = parent.getItemAtPosition(position).toString();
                    quantite = parent.getItemAtPosition(position).toString();
                    // Showing selected spinner item
                    //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            btnSupprimer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("djily", "element ajouté " + tvName.getText() + " " + quantite);
                    Log.d("djily",  "id  : " + FirebaseAuth.getInstance().getCurrentUser().getUid());
                    CommandeBDD commandeBDD = new CommandeBDD();
                    String urlImage="";
                    for (Produit p : products){
                        if (p.getNom() == tvName.getText()){
                            urlImage = p.getImage();
                        }
                    }
                    Produit p = new Produit(tvName.getText().toString(), quantite, urlImage ,tvPrix.getText().toString());

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
                                Panier panier = new Panier();
                                panier.refreshActivity();
                            })
                            .addOnFailureListener(e -> Log.e("djily", "Erreur lors de la suppression du produit", e));


                }
            });

        }
    }
}
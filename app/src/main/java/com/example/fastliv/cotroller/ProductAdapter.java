

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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.fastliv.R;
import com.example.fastliv.model.Produit;
import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Produit> products;
    private ArrayAdapter<CharSequence> dataAdapter;

    public ProductAdapter(Context context, List<Produit> products) {
        this.context = context;
        this.products = products;


        dataAdapter = ArrayAdapter.createFromResource(this.context
                , R.array.Quantites, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
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

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProduct;
        TextView tvName, tvPrix;
        public Spinner spinner;
        String quantite;
        Button btnAjouter;
        Activity activity;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct = (ImageView) itemView.findViewById(R.id.ivProduct);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvPrix = (TextView) itemView.findViewById(R.id.tvPrix);
            btnAjouter = (Button) itemView.findViewById(R.id.btn_supprimer);
            spinner = (Spinner)itemView.findViewById(R.id.spinner_quantite_produit);
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

            btnAjouter.setOnClickListener(new View.OnClickListener() {
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
                    commandeBDD.addProductToPanier(p, context);


                }
            });

        }
    }
}
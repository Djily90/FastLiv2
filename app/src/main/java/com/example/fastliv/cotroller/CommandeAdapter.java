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
import com.google.firebase.firestore.QueryDocumentSnapshot;
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
        holder.tvAdresse.setText(LatLong);
        holder.tvIdClient.setText(c.getIdClient());

        if (c.getEmailChauffeur() == null){
            holder.tvEmailChauffeur.setText("pas de chauffeur");
        }else {
            holder.tvEmailChauffeur.setText(c.getEmailChauffeur());
        }
        holder.tvStatut.setText(c.getStatut());
        holder.tvDateLivraison.setText(c.getDateLivraison().toString());
        if (c.getStatut().equals("en cours")){
            holder.btnVoirCommande.setVisibility(View.VISIBLE);
        }else {
            holder.btnVoirCommande.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return commandes.size();
    }

    public class CommandeViewHolder extends RecyclerView.ViewHolder {

        TextView tvemailClient, tvStatut, tvDateLivraison, tvAdresse, tvIdClient, tvEmailChauffeur;
        Button btnVoirCommande;
        Activity activity;

        Planificateur planificateur;
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        public CommandeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvemailClient = (TextView) itemView.findViewById(R.id.tvemailClient);
            tvEmailChauffeur = (TextView) itemView.findViewById(R.id.tvemailChauffeurCommande);
            tvStatut = (TextView) itemView.findViewById(R.id.tvStatutCommande);
            tvDateLivraison = (TextView) itemView.findViewById(R.id.tvDateLivraison);
            tvAdresse = (TextView) itemView.findViewById(R.id.tvAdresseCommande);
            tvIdClient = (TextView) itemView.findViewById(R.id.tvIdClient);
            btnVoirCommande = (Button) itemView.findViewById(R.id.btn_voir_commande);

           /* if (tvStatut.getText().toString() != "en cours"){
                btnVoirCommande.setVisibility(View.VISIBLE);
            }else {
                btnVoirCommande.setVisibility(View.INVISIBLE);
            }

            */


            btnVoirCommande.setOnClickListener(new View.OnClickListener() {


               @Override
                public void onClick(View v) {
                  // Log.d("djily", "selectionner tv => " + tvDateLivraison.getText());




                   Intent intent = new Intent(context, AssignerChauffeur.class);
                   intent.putExtra("adresseLivraison", tvAdresse.getText());
                   intent.putExtra("dateLivraison", tvDateLivraison.getText());
                   intent.putExtra("idClient", tvIdClient.getText());
                   intent.putExtra("emailClient", tvemailClient.getText());
                   intent.putExtra("emailChauffeur", tvEmailChauffeur.getText());
                   intent.putExtra("statutCommande", tvStatut.getText());
                   v.getContext().startActivity(intent);




                }
            });

        }
    }
}
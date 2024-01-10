package com.example.fastliv.model;

import com.bumptech.glide.util.Util;
import org.osmdroid.util.GeoPoint;

import java.util.Date;
import java.util.List;

public class Livraison {
    private String statutLivraison;
    private String emailClient;
    private GeoPoint adresse;
    private Utilisateur chauffeur;
    private List<Commande> commandes;




    public Livraison() {

    }
    public Livraison(String statutLivraison, Utilisateur chauffeur, GeoPoint adresse,
                     List<Commande> commandes, String emailClient) {
        super();
        this.statutLivraison = statutLivraison;
        this.chauffeur = chauffeur;
        this.adresse = adresse;
        this.commandes = commandes;
        this.emailClient = emailClient;
    }

    public Livraison(String statutLivraison, GeoPoint adresse, String emailClient) {

        this.statutLivraison = statutLivraison;
        this.adresse = adresse;
        this.emailClient = emailClient;
    }


    public String getStatutLivraison() {
        return statutLivraison;
    }
    public void setStatutLivraison(String statutLivraison) {
        this.statutLivraison = statutLivraison;
    }
    public String getEmailClient() {
        return emailClient;
    }
    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }
    public Utilisateur getChauffeur() {
        return chauffeur;
    }
    public void setChauffeur(Utilisateur chauffeur) {
        this.chauffeur = chauffeur;
    }


    public GeoPoint getAdresse() {
        return adresse;
    }


    public void setAdresse(GeoPoint adresse) {
        this.adresse = adresse;
    }


    public List<Commande> getCommandes() {
        return commandes;
    }
    public void setCommandes(Commande commande) {
        this.commandes.add(commande);
    }





}


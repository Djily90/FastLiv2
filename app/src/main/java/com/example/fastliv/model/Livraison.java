package com.example.fastliv.model;

import com.bumptech.glide.util.Util;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;
import java.util.List;

public class Livraison {
    private String statutLivraison;
    private GeoPoint adresse;
    private Utilisateur chauffeur;
    private List<Commande> commandes;




    public Livraison() {

    }
    public Livraison(String statutLivraison, Utilisateur chauffeur, GeoPoint adresse,
                     List<Commande> commandes) {
        super();
        this.statutLivraison = statutLivraison;
        this.chauffeur = chauffeur;
        this.adresse = adresse;
        this.commandes = commandes;
    }


    public String getStatutLivraison() {
        return statutLivraison;
    }
    public void setStatutLivraison(String statutLivraison) {
        this.statutLivraison = statutLivraison;
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


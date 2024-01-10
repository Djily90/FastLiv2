package com.example.fastliv.model;

import com.bumptech.glide.util.Util;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.Date;
import java.util.List;

public class Livraison {
    private String statutLivraison;
    private String emailClient;
    private GeoPoint adresse;
    private Utilisateur chauffeur;
    private List<Commande> commandes;
    private String emailChauffeur;




    public Livraison() {

    }
    public Livraison(String statutLivraison, Utilisateur chauffeur, GeoPoint adresse,
                     List<Commande> commandes, String emailClient,String emailChauffeur) {
        super();
        this.statutLivraison = statutLivraison;
        this.chauffeur = chauffeur;
        this.adresse = adresse;
        this.commandes = commandes;
        this.emailClient = emailClient;
        this.emailChauffeur = emailChauffeur;
    }

    public Livraison(String statutLivraison, GeoPoint adresse, String emailClient,String emailChauffeur) {

        this.statutLivraison = statutLivraison;
        this.adresse = adresse;
        this.emailClient = emailClient;
        this.emailChauffeur = emailChauffeur;
    }


    public String getStatutLivraison() {
        return statutLivraison;
    }
    public void setStatutLivraison(String statutLivraison) {
        this.statutLivraison = statutLivraison;
    }
    public String getEmailChauffeur() {
        return emailChauffeur;
    }
    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }
    public String getEmailClient() {
        return emailClient;
    }
    public void setEmailChauffeur(String emailChauffeur) {
        this.emailChauffeur = emailChauffeur;
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


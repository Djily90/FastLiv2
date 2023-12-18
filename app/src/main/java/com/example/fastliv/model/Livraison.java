package com.example.fastliv.model;

import com.google.firebase.firestore.GeoPoint;

import java.util.Date;
import java.util.List;

public class Livraison {
    private Date dateLivraison;
    private String statutLivraison;
    private String adresse;
    private GeoPoint latLong;
    private Chauffeur chauffeur;
    private List<Commande> commandes;




    public Livraison() {

    }
    public Livraison(Date dateLivraison, String statutLivraison, Chauffeur chauffeur, String adresse,
                     List<Commande> commandes) {
        super();
        this.dateLivraison = dateLivraison;
        this.statutLivraison = statutLivraison;
        this.chauffeur = chauffeur;
        this.adresse = adresse;
        this.commandes = commandes;
    }




    public Date getDateLivraison() {
        return dateLivraison;
    }
    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
    }
    public String getStatutLivraison() {
        return statutLivraison;
    }
    public void setStatutLivraison(String statutLivraison) {
        this.statutLivraison = statutLivraison;
    }
    public Chauffeur getChauffeur() {
        return chauffeur;
    }
    public void setChauffeur(Chauffeur chauffeur) {
        this.chauffeur = chauffeur;
    }


    public String getAdresse() {
        return adresse;
    }


    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }


    public List<Commande> getCommandes() {
        return commandes;
    }
    public void setCommandes(Commande commande) {
        this.commandes.add(commande);
    }

    public GeoPoint getLatLong() {
        return latLong;
    }


    public void setLatLong(GeoPoint latLong) {
        this.latLong = latLong;
    }


    GeoPoint getLatLongByAdresse(String adresse) {
        // Fonction pour convertir obtenir la latitude et la longitude depuis une adresse
        return null;
    }




}


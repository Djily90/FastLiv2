package com.example.fastliv.model;

import android.location.Address;
import android.location.Geocoder;

import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Commande {
    private List<Produit> produits = new ArrayList<>();
    private String idClient;
    private GeoPoint adresse;
    private boolean statut;
    private Date dateLivraison;

    public Commande(){

    }
    public Commande(List<Produit> produits, String idClient, GeoPoint adresse, boolean statut, Date dateLivraison){
        this.produits = produits;
        this.idClient = idClient;
        this.adresse = adresse;
        this.statut = statut;
        this.dateLivraison = dateLivraison;
    }

    public List<Produit> getProduits() {
        return produits;
    }
    public void setProduits(List<Produit> produit) {
        this.produits=produit;
    }

    public String getIdClient(){
        return idClient;
    }
    public void setIdClient(String id){
        this.idClient = id;
    }

    public GeoPoint getAdresse(){
        return adresse;
    }
    public void setAdresse(GeoPoint adrese){
        this.adresse = adrese;
    }

    public boolean getStatut(){
        return statut;
    }
    public void setStatut(Boolean statut){
        this.statut = statut;
    }

    public Date getDateLivraison(){
        return dateLivraison;
    }
    public void setDateLivraison(Date date){
        this.dateLivraison = date;
    }

}

package com.example.fastliv.model;

import android.location.Address;
import android.location.Geocoder;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.GeoPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Commande {
    private List<Produit> produits = new ArrayList<>();
    private String idClient;
    private GeoPoint adresse;
    private String statut;
    private Date dateLivraison;

    private String emailClient;


    public Commande(){

    }
    public Commande(List<Produit> produits, String idClient, GeoPoint adresse, String statut, Date dateLivraison){
        this.produits = produits;
        this.idClient = idClient;
        this.adresse = adresse;
        this.statut = statut;
        this.dateLivraison = dateLivraison;
    }

    public Commande(String emailClient, String idClient, GeoPoint adresse, String statut, Date dateLivraison){
        this.emailClient = emailClient;
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

    public String getEmailClient(){
        return emailClient;
    }
    public void setEmailClient(String emailClient){
        this.emailClient = emailClient;
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

    public String getStatut(){
        return statut;
    }
    public void setStatut(String statut){
        this.statut = statut;
    }

    public Date getDateLivraison(){
        return dateLivraison;
    }
    public void setDateLivraison(Date date){
        this.dateLivraison = date;
    }

}

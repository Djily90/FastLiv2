package com.example.fastliv.model;

public class Produit {
    private String nom;
    private String quantite;


    public Produit(String nom, String quantite) {
        this.nom = nom;
        this.quantite = quantite;
    }

    public Produit() {

    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getDescription() {
        return quantite;
    }
    public void setDescription(String quantite) {
        this.quantite = quantite;
    }


}

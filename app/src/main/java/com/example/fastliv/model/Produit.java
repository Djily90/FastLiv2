package com.example.fastliv.model;

public class Produit {
    private String nom;
    private String quantite;
    private String image;


    public Produit(String nom, String quantite, String image) {
        this.nom = nom;
        this.quantite = quantite;
        this.image = image;
    }

    public Produit() {

    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }


}

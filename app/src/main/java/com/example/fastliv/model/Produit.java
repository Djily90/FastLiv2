package com.example.fastliv.model;

public class Produit {
    private String nom;
    private String quantite;
    private String image;

    private String prix;



    public Produit(String nom, String quantite, String image, String prix) {
        this.nom = nom;
        this.quantite = quantite;
        this.image = image;
        this.prix = prix;
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
    public String getPrix() {
        return prix;
    }
    public void setPrix(String prix) {
        this.prix = prix;
    }


}

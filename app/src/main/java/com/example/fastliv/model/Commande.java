package com.example.fastliv.model;

import java.util.ArrayList;
import java.util.List;

public class Commande {
    private List<Produit> produits = new ArrayList<>();

    public Commande(){

    }
    public Commande(List<Produit> produits){
        this.produits = produits;
    }

    public List<Produit> getProduits() {
        return produits;
    }
    public void setProduits(Produit produit) {
        this.produits.add(produit);
    }
}

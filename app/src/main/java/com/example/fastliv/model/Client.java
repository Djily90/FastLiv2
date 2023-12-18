package com.example.fastliv.model;

import java.util.ArrayList;
import java.util.List;

public class Client extends Utilisateur {
    private List<Commande> commandes = new ArrayList<>();

    public Client(String email, String telephone, String password, String role) {
        super(email, telephone, password, role);
    }

    public List<Commande> getListCommandes(){
        return commandes;
    }



}
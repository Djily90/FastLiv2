package com.example.fastliv.model;

import java.util.ArrayList;
import java.util.List;

public class Chauffeur extends Utilisateur {
    private String immatriculation;
    private List<Mission> missions = new ArrayList<>();
    public Chauffeur(String email, String motDePasse, String numero, String role, String immatriculation) {
        super(email,  motDePasse, numero, role);
        this.immatriculation = immatriculation;
    }

    public void setMissions(Mission mission) {
        this.missions.add(mission);
    }
    public List<Mission> getMissions() {
        return missions;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }
}

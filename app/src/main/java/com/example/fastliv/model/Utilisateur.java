package com.example.fastliv.model;

public class Utilisateur {
    private String uuid;
    private String email;
    private String telephone;
    private String password;
    private String role;
    private String immatriculation;
    private String statutChauffeur;



    public Utilisateur() {

    }


    public Utilisateur(String email, String telephone, String role, String immatriculation, String statutChauffeur) {
        this.email = email;
        this.telephone = telephone;
        this.role = role;
        this.immatriculation = immatriculation;
        this.statutChauffeur = statutChauffeur;
    }


    public Utilisateur(String uuid, String email, String telephone, String role, String immatriculation, String statutChauffeur) {
        this.uuid =  uuid;
        this.email = email;
        this.telephone = telephone;
        this.role = role;
        this.immatriculation = immatriculation;
        this.statutChauffeur = statutChauffeur;
    }



    public String getUuid() {
        return uuid;
    }


    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getTelephone() {
        return telephone;
    }


    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getRole() {
        return role;
    }


    public void setRole(String role) {
        this.role = role;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public String getStatutChauffeur() {
        return statutChauffeur;
    }

    public void setStatutChauffeur(String statutChauffeur) {
        this.statutChauffeur = statutChauffeur;
    }


    @Override
    public String toString() {
        return "Utilisateur [id=" + uuid + ", email=" + email + ", telephone=" + telephone + ", password=" + password
                + ", role=" + role + "]";
    }
}

package com.example.fastliv.model;

public class Utilisateur {
    private int id;
    private String email;
    private String telephone;
    private String password;
    private String role;
    private String immatriculation;


    public Utilisateur() {

    }


    public Utilisateur(String email,  String password, String telephone, String role, String immatriculation) {
        this.email = email;
        this.telephone = telephone;
        this.password = password;
        this.role = role;
        this.immatriculation = immatriculation;
    }


    public Utilisateur(String email, String telephone, String role, String immatriculation) {
        this.email = email;
        this.telephone = telephone;
        this.role = role;
        this.immatriculation = immatriculation;
    }



    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
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


    @Override
    public String toString() {
        return "Utilisateur [id=" + id + ", email=" + email + ", telephone=" + telephone + ", password=" + password
                + ", role=" + role + "]";
    }
}

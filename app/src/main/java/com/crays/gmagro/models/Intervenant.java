package com.crays.gmagro.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Intervenant {
    private int id;
    private String nom;
    private String prenom;
    private String mail;
    private String password;
    private boolean actif;
    private String role_code;
    private String site_uai;

    public Intervenant() {
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActif() {
        return actif;
    }

    public String getRole_code() {
        return role_code;
    }

    public String getSite_uai() {
        return site_uai;
    }

    public Intervenant (JSONObject jsono) {
        try {
            this.id = jsono.getInt("id");
            this.nom = jsono.getString("nom");
            this.prenom = jsono.getString("prenom");
            this.mail = jsono.getString("mail");
            this.password = jsono.getString("password");
            this.actif =  Boolean.parseBoolean(jsono.getString("actif"));
            this.role_code = jsono.getString("role_code");
            this.site_uai = jsono.getString("site_uai");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return getNom() + " " + getPrenom();
    }
}

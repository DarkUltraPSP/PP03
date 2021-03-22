package com.crays.gmagro.models;

import com.crays.gmagro.ASyncWS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Activite {
    private String code;
    private String libelle;

    public String getCode() {
        return code;
    }

    public String getLibelle() {
        return libelle;
    }

    public Activite(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public Activite (JSONObject jsono) {
        try {
            this.code = jsono.getString("code");
            this.libelle = jsono.getString("libelle");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return libelle;
    }
}

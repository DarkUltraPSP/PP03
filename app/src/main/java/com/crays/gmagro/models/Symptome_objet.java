package com.crays.gmagro.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Symptome_objet {
    private String code;
    private String libelle;
    private String site_uai;

    public String getLibelle() {
        return libelle;
    }

    public String getSite_uai() {
        return site_uai;
    }

    public String getCode() {
        return code;
    }

    public Symptome_objet (JSONObject jsono) {
        try {
            this.code = jsono.getString("code");
            this.libelle = jsono.getString("libelle");
            this.site_uai = jsono.getString("site_uai");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.libelle;
    }
}

package com.crays.gmagro.models;

import org.json.JSONException;
import org.json.JSONObject;

public class IntInt {
    private int intervention_id;
    private int intervenant_id;
    private String tps_passe;

    public int getIntervention_id () {
        return intervention_id;
    }

    public int getIntervenant_id () {
        return intervenant_id;
    }

    public String getTps_passe () {
        return tps_passe;
    }

    public IntInt (JSONObject jsono) {
        try {
            this.intervention_id = jsono.getInt("intervention_id");
            this.intervenant_id = jsono.getInt("intervenant_id");
            this.tps_passe = jsono.getString("libelle");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

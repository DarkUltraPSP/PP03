package com.crays.gmagro.models;

import com.crays.gmagro.daos.IntervenantDAO;

import org.json.JSONException;
import org.json.JSONObject;

public class IntInt {
    private int intervention_id;
    private int intervenant_id;
    private String tps_passe;

    public IntInt(int intervention_id, int intervenant_id, String tps_passe) {
        this.intervention_id = intervention_id;
        this.intervenant_id = intervenant_id;
        this.tps_passe = tps_passe;
    }

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

    @Override
    public String toString() {
        return IntervenantDAO.findIntervenantByID(this.intervenant_id) + " "+ tps_passe;
    }
}

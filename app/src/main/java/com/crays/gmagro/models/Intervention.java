package com.crays.gmagro.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Intervention {
    private int id;
    private String dh_debut;
    private String dh_fin;
    private String commentaire;
    private String temps_arret;
    private boolean changement_organe;
    private boolean perte;
    private String dh_creation;
    private String dh_derniere_maj;
    private int intervenant_id;
    private String activite_code;
    private String machine_code;
    private String cause_defaut_code;
    private String cause_objet_code;
    private String symptome_defaut_code;
    private String symptome_objet_code;

    public Intervention(int id, String dh_debut, String dh_fin, String commentaire, String temps_arret, boolean changement_organe, boolean perte, String dh_creation, String dh_derniere_maj, int intervenant_id, String activity_code, String machine_code, String cause_defaut_code, String cause_objet_code, String symptome_defaut_code, String symptome_objet_code) {
        this.id = id;
        this.dh_debut = dh_debut;
        this.dh_fin = dh_fin;
        this.commentaire = commentaire;
        this.temps_arret = temps_arret;
        this.changement_organe = changement_organe;
        this.perte = perte;
        this.dh_creation = dh_creation;
        this.dh_derniere_maj = dh_derniere_maj;
        this.intervenant_id = intervenant_id;
        this.activite_code = activity_code;
        this.machine_code = machine_code;
        this.cause_defaut_code = cause_defaut_code;
        this.cause_objet_code = cause_objet_code;
        this.symptome_defaut_code = symptome_defaut_code;
        this.symptome_objet_code = symptome_objet_code;
    }

    public int getId() {
        return id;
    }

    public String getDh_debut() {
        return dh_debut;
    }

    public String getDh_fin() {
        return dh_fin;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public String getTemps_arret() {
        return temps_arret;
    }

    public boolean isChangement_organe() {
        return changement_organe;
    }

    public boolean isPerte() {
        return perte;
    }

    public String getDh_creation() {
        return dh_creation;
    }

    public String getDh_derniere_maj() {
        return dh_derniere_maj;
    }

    public int getIntervenant_id() {
        return intervenant_id;
    }

    public String getActivite_code() {
        return activite_code;
    }

    public String getMachine_code() {
        return machine_code;
    }

    public String getCause_defaut_code() {
        return cause_defaut_code;
    }

    public String getCause_objet_code() {
        return cause_objet_code;
    }

    public String getSymptome_defaut_code() {
        return symptome_defaut_code;
    }

    public String getSymptome_objet_code() {
        return symptome_objet_code;
    }

    public Intervention (JSONObject jsono) {
        try {
            this.id = jsono.getInt("id");
            this.dh_debut = jsono.getString("dh_debut");
            this.dh_fin = jsono.getString("dh_fin");
            this.commentaire = jsono.getString("commentaire");
            this.temps_arret = jsono.getString("temps_arret");
            this.changement_organe = Boolean.parseBoolean(jsono.getString("changement_organe"));
            this.perte =  Boolean.parseBoolean(jsono.getString("perte"));
            this.dh_creation = jsono.getString("dh_creation");
            this.dh_derniere_maj = jsono.getString("dh_derniere_maj");
            this.intervenant_id = jsono.getInt("intervenant_id");
            this.activite_code = jsono.getString("activite_code");
            this.machine_code = jsono.getString("machine_code");
            this.cause_defaut_code = jsono.getString("cause_defaut_code");
            this.cause_objet_code = jsono.getString("cause_objet_code");
            this.symptome_defaut_code = jsono.getString("symptome_defaut_code");
            this.symptome_objet_code = jsono.getString("symptome_defaut_code");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString()
    {
        return machine_code + " - " + dh_derniere_maj;
    }
}

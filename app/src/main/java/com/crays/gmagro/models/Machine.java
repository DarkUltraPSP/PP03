package com.crays.gmagro.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Machine {
    private String code;
    private String date_fabrication;
    private String numero_serie;
    private String site_uai;
    private String type_machine_code;

    public String getCode() {
        return code;
    }

    public String getDate_fabrication() {
        return date_fabrication;
    }

    public String getNumero_serie() {
        return numero_serie;
    }

    public String getSite_uai() {
        return site_uai;
    }

    public String getType_machine_code() {
        return type_machine_code;
    }

    public Machine (JSONObject jsono) {
        try {
            this.code = jsono.getString("code");
            this.date_fabrication = jsono.getString("date_fabrication");
            this.numero_serie = jsono.getString("numero_serie");
            this.site_uai = jsono.getString("site_uai");
            this.type_machine_code = jsono.getString("type_machine_code");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.code;
    }
}

package com.crays.gmagro.daos;

import com.crays.gmagro.ASyncWS;
import com.crays.gmagro.models.IntInt;
import com.crays.gmagro.models.Intervention;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InterventionDAO {

    private ArrayList<Intervention> getInterventions()
    {
        ArrayList<Intervention> allInterv = new ArrayList<>();
        ASyncWS asGetInterv = new ASyncWS()
        {
            @Override
            protected void onPostExecute(String s)
            {
                try
                {
                    JSONArray jsonArray = new JSONArray(s);
                    for (int i = 0; i<jsonArray.length(); i++)
                    {
                        JSONObject jsono = jsonArray.getJSONObject(i);
                        Intervention interv = new Intervention(jsono);
                        allInterv.add(interv);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        };
        asGetInterv.execute("uc=getIntervention");
        return allInterv;
    }

    public static void sendIntervention(Intervention interv , ArrayList<IntInt> lesIntInts ){
        ASyncWS as = new ASyncWS(){
            @Override
            protected void onPostExecute(String s)
            {

            }
        };
        int changement_organe = 0;
        if (interv.isChangement_organe() == true){
            changement_organe = 1;
        }
        int perte = 0;
        if(interv.isPerte() == true) {
            perte = 1;
        }

        String url = "uc=addIntervention" +
                "&dh_debut=" + interv.getDh_debut() +
                "&dh_fin=" + interv.getDh_fin() +
                "&commentaire=" + interv.getCommentaire() +
                "&temps_arret=" + interv.getTemps_arret() +
                "&changement_organe=" + changement_organe +
                "&perte=" + perte +
                "&intervenant_id=" + interv.getIntervenant_id() +
                "&activite_code=" + interv.getActivite_code() +
                "&machine_code=" + interv.getMachine_code() +
                "&cause_defaut_code=" + interv.getCause_defaut_code() +
                "&cause_objet_code=" + interv.getCause_objet_code() +
                "&symptome_defaut_code=" + interv.getSymptome_defaut_code() +
                "&symptome_objet_code=" + interv.getSymptome_objet_code() +
                "&intint=";

        for( IntInt ii : lesIntInts){
            url+=ii.getIntervenant_id() +"|" +ii.getTps_passe() ;
            if( !lesIntInts.get(lesIntInts.size()-1).equals(ii)){
                url+=";;" ;
            }
        }

        as.execute(url) ;
    }

}

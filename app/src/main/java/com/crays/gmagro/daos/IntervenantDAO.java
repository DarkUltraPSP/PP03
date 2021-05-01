package com.crays.gmagro.daos;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.crays.gmagro.ASyncWS;
import com.crays.gmagro.models.Intervenant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class IntervenantDAO
{
    public static ArrayList<Intervenant> allIntervenant = new ArrayList<>();
    public static void requestHTTPGetAllIntervenant(ArrayAdapter<Intervenant> adapter)
    {
        ASyncWS as = new ASyncWS(){
            @Override
            protected void onPostExecute(String s) {
                JSONArray jsonArray = null;
                try
                {
                    jsonArray = new JSONArray(s);
                    for (int i = 0; i<jsonArray.length(); i++)
                    {
                        JSONObject jsono = jsonArray.getJSONObject(i);
                        Intervenant intervenant = new Intervenant(jsono);
                        allIntervenant.add(intervenant);
                    }
                    adapter.notifyDataSetChanged();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                Log.d("AllIntervenants", allIntervenant.toString());
            }
        };
        as.execute("uc=getIntervenant");
    }

    public static String findIntervenantByID(int id) {
        String name = "";
        for (Intervenant interv : allIntervenant) {
            if (interv.getId() == id) {
                name = interv.getNom() + " " + interv.getPrenom();
            }
        }
        return name;
    }

}

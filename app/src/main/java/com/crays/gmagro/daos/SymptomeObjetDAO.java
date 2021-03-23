package com.crays.gmagro.daos;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.crays.gmagro.ASyncWS;
import com.crays.gmagro.models.CauseDefaut;
import com.crays.gmagro.models.Symptome_objet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SymptomeObjetDAO {

    public static ArrayList<Symptome_objet> allSymptomeObjet = new ArrayList<>();
    public static void requestHTTPGetAllSymptomeObjet(ArrayAdapter<Symptome_objet> adapter) {
        ASyncWS as = new ASyncWS(){
            @Override
            protected void onPostExecute(String s) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(s);
                    for (int i = 0; i<jsonArray.length(); i++)
                    {
                        JSONObject jsono = jsonArray.getJSONObject(i);
                        Symptome_objet sO = new Symptome_objet(jsono);
                        allSymptomeObjet.add(sO);

                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("AllSymptomeObjet", allSymptomeObjet.toString());
            }
        };
        as.execute("uc=getSymptomeObjet");
    }
}

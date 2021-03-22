package com.crays.gmagro.daos;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.crays.gmagro.ASyncWS;
import com.crays.gmagro.models.Machine;
import com.crays.gmagro.models.Symptome_defaut;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SymptomeDefautDAO {
    public static ArrayList<Symptome_defaut> AllSDefaut = new ArrayList<>();
    public static void requestHTTPGetAllSymptomeDefaut(ArrayAdapter<Symptome_defaut> adapter) {
        ASyncWS as = new ASyncWS() {
            @Override
            protected void onPostExecute(String s) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(s);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsono = jsonArray.getJSONObject(i);
                        Symptome_defaut symptDefaut = new Symptome_defaut(jsono);
                        AllSDefaut.add(symptDefaut);

                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("AllSymtomeDefaut", AllSDefaut.toString());
            }
        };
        as.execute("uc=getSymptomeDefaut");
    }
}

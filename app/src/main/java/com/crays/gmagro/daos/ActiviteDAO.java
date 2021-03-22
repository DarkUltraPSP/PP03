package com.crays.gmagro.daos;

import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import com.crays.gmagro.ASyncWS;
import com.crays.gmagro.models.Activite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActiviteDAO {
    public static ArrayList<Activite> allActivite = new ArrayList<>();
    public static void requestHTTPGetAllActivite(ArrayAdapter<Activite> adapter) {
        ASyncWS as = new ASyncWS(){
            @Override
            protected void onPostExecute(String s) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(s);
                    for (int i = 0; i<jsonArray.length(); i++)
                    {
                        JSONObject jsono = jsonArray.getJSONObject(i);
                        Activite act = new Activite(jsono);
                        allActivite.add(act);

                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("AllActivites", allActivite.toString());
            }
        };
        as.execute("uc=getActivite");
    }

}

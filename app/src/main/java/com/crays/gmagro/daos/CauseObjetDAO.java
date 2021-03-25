package com.crays.gmagro.daos;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.crays.gmagro.ASyncWS;
import com.crays.gmagro.models.CauseObjet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CauseObjetDAO {
    public static ArrayList<CauseObjet> allCauseObjet = new ArrayList<>();
    public static void requestHTTPGetAllCauseObjet(ArrayAdapter<CauseObjet> adapter) {
        ASyncWS as = new ASyncWS(){
            @Override
            protected void onPostExecute(String s) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(s);
                    for (int i = 0; i<jsonArray.length(); i++)
                    {
                        JSONObject jsono = jsonArray.getJSONObject(i);
                        CauseObjet cO = new CauseObjet(jsono);
                        allCauseObjet.add(cO);

                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("AllCauseObjet", allCauseObjet.toString());
            }
        };
        as.execute("uc=getCauseObjet");
    }
}

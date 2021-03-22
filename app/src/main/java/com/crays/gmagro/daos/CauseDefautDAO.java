package com.crays.gmagro.daos;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.crays.gmagro.ASyncWS;
import com.crays.gmagro.models.CauseDefaut;
import com.crays.gmagro.models.Machine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CauseDefautDAO {
    public static ArrayList<CauseDefaut> allCauseDefaut = new ArrayList<>();
    public static void requestHTTPGetAllCauseDefaut(ArrayAdapter<CauseDefaut> adapter) {
        ASyncWS as = new ASyncWS(){
            @Override
            protected void onPostExecute(String s) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(s);
                    for (int i = 0; i<jsonArray.length(); i++)
                    {
                        JSONObject jsono = jsonArray.getJSONObject(i);
                        CauseDefaut cD = new CauseDefaut(jsono);
                        allCauseDefaut.add(cD);

                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("AllCauseDefaut", allCauseDefaut.toString());
            }
        };
        as.execute("uc=getCauseDefaut");
    }
}

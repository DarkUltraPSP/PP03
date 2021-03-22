package com.crays.gmagro.daos;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.crays.gmagro.ASyncWS;
import com.crays.gmagro.models.Activite;
import com.crays.gmagro.models.Machine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MachineDAO {

    public static ArrayList<Machine> allMachines = new ArrayList<>();
    public static void requestHTTPGetAllMachine(ArrayAdapter<Machine> adapter) {
        ASyncWS as = new ASyncWS(){
            @Override
            protected void onPostExecute(String s) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(s);
                    for (int i = 0; i<jsonArray.length(); i++)
                    {
                        JSONObject jsono = jsonArray.getJSONObject(i);
                        Machine mach = new Machine(jsono);
                        allMachines.add(mach);

                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("AllMachines", allMachines.toString());
            }
        };
        as.execute("uc=getMachine");
    }
}

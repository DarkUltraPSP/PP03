package com.crays.gmagro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.crays.gmagro.models.Intervenant;
import com.crays.gmagro.models.Intervention;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity
{
    private ArrayList<Intervention> allInterv = new ArrayList<>();
    private ArrayAdapter<Intervention> arrayAdapter;
    private ListView lvInterv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button btnDeco = findViewById(R.id.btnDeconnexion);
        Button btnAdd = findViewById(R.id.btnAdd);
        lvInterv = findViewById(R.id.lvInterv);

        displaySession();

        displayInterventions();

        btnDeco.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OnClickLogOut(v);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                cliqueBtnAdd();
            }
        });
    }

    private void cliqueBtnAdd() {
        Intent intent = new Intent(this, AddIntervention.class);
        startActivityForResult(intent, 101);
    }

    private void displaySession() {
        ASyncWS asGetSession = new ASyncWS() {
            @Override
            protected void onPostExecute(String s) {
                refreshTV(s);
            }
        };
        asGetSession.execute("uc=getSession");
    }

    private void refreshTV(String s) {
        TextView tvInfo = findViewById(R.id.tvInfo);
        tvInfo.setText("Vous etes connectes en tant que : " + s);
    }

    private void OnClickLogOut(View v)
    {
        ASyncWS as = new ASyncWS(){
            @Override
            protected void onPostExecute(String s)
            {
                getReturnDeco(s) ;
            }
        };
        as.execute("uc=disconnect");
    }

    private void getReturnDeco(String s)
    {
        if (s.equals("1"))
        {
            finish();
        }
    }

    private void displayInterventions()
    {
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
                refreshLV();


            }
        };
        asGetInterv.execute("uc=getIntervention");
    }

    private void refreshLV()
    {
        arrayAdapter = new ArrayAdapter<Intervention> (this,
                        android.R.layout.simple_list_item_1,
                        allInterv
                );
        this.lvInterv.setAdapter(arrayAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode==0 ){
            finish();
        }
    }
}
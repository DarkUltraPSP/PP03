package com.crays.gmagro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.crays.gmagro.daos.ActiviteDAO;
import com.crays.gmagro.daos.MachineDAO;
import com.crays.gmagro.models.Activite;
import com.crays.gmagro.models.Machine;

public class AddIntervention extends AppCompatActivity {
    private ArrayAdapter<Activite> allActAdapter;
    private ArrayAdapter<Machine> allMachAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_intervention);
        Button btnDeconnexion = findViewById(R.id.btnDeco);
        Spinner spinAct = findViewById(R.id.spinActivite);
        Spinner spinMach = findViewById(R.id.spinMachine);
        displaySession();

        btnDeconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickLogOut(v);
            }
        });
        allActAdapter = new ArrayAdapter<Activite> (this,
                android.R.layout.simple_spinner_dropdown_item,
                ActiviteDAO.allActivite
        );
        allMachAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                MachineDAO.allMachines
        );

        spinAct.setAdapter(allActAdapter);
        ActiviteDAO.requestHTTPGetAllActivite(allActAdapter);

        spinMach.setAdapter(allMachAdapter);
        MachineDAO.requestHTTPGetAllMachine(allMachAdapter);
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
        TextView tvInfo = findViewById(R.id.tvInfoLogin);
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
            setResult(0);
            finish();
        }
    }
}
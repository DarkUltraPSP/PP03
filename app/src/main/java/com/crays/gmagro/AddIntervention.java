package com.crays.gmagro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.crays.gmagro.daos.ActiviteDAO;
import com.crays.gmagro.daos.CauseDefautDAO;
import com.crays.gmagro.daos.CauseObjetDAO;
import com.crays.gmagro.daos.IntervenantDAO;
import com.crays.gmagro.daos.MachineDAO;
import com.crays.gmagro.daos.SymptomeDefautDAO;
import com.crays.gmagro.daos.SymptomeObjetDAO;
import com.crays.gmagro.models.Activite;
import com.crays.gmagro.models.CauseDefaut;
import com.crays.gmagro.models.CauseObjet;
import com.crays.gmagro.models.Intervenant;
import com.crays.gmagro.models.Machine;
import com.crays.gmagro.models.Symptome_defaut;
import com.crays.gmagro.models.Symptome_objet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddIntervention extends AppCompatActivity {
    private ArrayAdapter<Activite> allActAdapter;
    private ArrayAdapter<Machine> allMachAdapter;
    private ArrayAdapter<CauseDefaut> allCDefautAdapter;
    private ArrayAdapter<CauseObjet> allCObjetAdapter;
    private ArrayAdapter<Symptome_defaut> allSDefautAdapter;
    private ArrayAdapter<Symptome_objet> allSObjetAdapter;
    private ArrayAdapter<Intervenant> arrayAdapterIntervenant;
    private ArrayList<Intervenant> intervenantsAdded = new ArrayList<>();
    private ArrayAdapter<Intervenant> arrayAdapterIntervenantAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_intervention);
        Button btnDeconnexion = findViewById(R.id.btnDeco);
        Spinner spinAct = findViewById(R.id.spinActivite);
        Spinner spinMach = findViewById(R.id.spinMachine);
        Spinner spinCDefaut = findViewById(R.id.spinCauseDefaut);
        Spinner spinCObjet = findViewById(R.id.spinCauseObjet);
        Spinner spinSymptDefaut = findViewById(R.id.spinSymptomeDefaut);
        Spinner spinSymptObjet = findViewById(R.id.spinSymptomeObjet);
        Spinner spinIntervenant = findViewById(R.id.spinIntervenant);
        Button btnHeure = findViewById(R.id.btnShowPopUp);
        CheckBox cbInterFinie = findViewById (R.id.cbInterventionTerminee);
        Button heureFinButton  = findViewById (R.id.btnHeureFin);
        CheckBox cbMachineStoped = findViewById (R.id.cbMachineArretee);
        Button btnTpsPerdu = findViewById (R.id.btnTpsArret);
        Button btnTpsPasse = findViewById(R.id.btnTpsPasse);
        Button btnAddIntervenant = findViewById(R.id.btnAddIntervLv);
        ListView lvInterv = findViewById(R.id.lvIntervenants);

        displaySession();

        btnDeconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickLogOut(v);
            }
        });
        adapterConstructer();

        spinAct.setAdapter(allActAdapter);
        ActiviteDAO.requestHTTPGetAllActivite(allActAdapter);

        spinMach.setAdapter(allMachAdapter);
        MachineDAO.requestHTTPGetAllMachine(allMachAdapter);

        spinCDefaut.setAdapter(allCDefautAdapter);
        CauseDefautDAO.requestHTTPGetAllCauseDefaut(allCDefautAdapter);

        spinSymptDefaut.setAdapter(allSDefautAdapter);
        SymptomeDefautDAO.requestHTTPGetAllSymptomeDefaut(allSDefautAdapter);

        spinSymptObjet.setAdapter(allSObjetAdapter);
        SymptomeObjetDAO.requestHTTPGetAllSymptomeObjet(allSObjetAdapter);

        spinIntervenant.setAdapter(arrayAdapterIntervenant);
        IntervenantDAO.requestHTTPGetAllIntervenant(arrayAdapterIntervenant);

        spinCObjet.setAdapter(allCObjetAdapter);
        CauseObjetDAO.requestHTTPGetAllCauseObjet(allCObjetAdapter);

        btnAddIntervenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                intervenantsAdded.add((Intervenant) spinIntervenant.getSelectedItem());
                arrayAdapConstructIntervAdd();
                Log.d("test", intervenantsAdded.toString());
                lvInterv.setAdapter(arrayAdapterIntervenantAdd);
            }
        });

        btnHeure.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                showDateTimeDialog (btnHeure);
            }
        });

        heureFinButton.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View v) {
                showDateTimeDialog (heureFinButton);
            }
        });

        btnTpsPerdu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v) {
                showTimeDialog(btnTpsPerdu);
            }
        });

        btnTpsPasse.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v) {
                showTimeDialog(btnTpsPasse);
            }
        });

        cbInterFinie.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener ()
        {
            @Override
            public void onCheckedChanged (CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    heureFinButton.setVisibility (View.VISIBLE);
                }
                else {
                    heureFinButton.setVisibility (View.INVISIBLE);
                    heureFinButton.setText("Selectionnez une date et une heure");
                }
            }
        });

        cbMachineStoped.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener ()
        {
            @Override
            public void onCheckedChanged (CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnTpsPerdu.setVisibility (View.VISIBLE);
                }
                else {
                    btnTpsPerdu.setVisibility (View.INVISIBLE);
                    btnTpsPerdu.setText("Selectionnez un heure");
                }
            }
        });
    }

    private void showDateTimeDialog(final Button date_time_in)
    {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        calendar.set(Calendar.SECOND,00);

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
                    }

                };

                new TimePickerDialog(AddIntervention.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
            }
        };

        new DatePickerDialog(AddIntervention.this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void showTimeDialog (final Button date_time_in)
    {
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet (TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 00);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

                date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
            }

        };
        new TimePickerDialog(AddIntervention.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
    }

    private void adapterConstructer()
    {
        allActAdapter = new ArrayAdapter<Activite> (this,
                android.R.layout.simple_spinner_dropdown_item,
                ActiviteDAO.allActivite
        );
        allMachAdapter = new ArrayAdapter<Machine>(this,
                android.R.layout.simple_spinner_dropdown_item,
                MachineDAO.allMachines
        );
        allCDefautAdapter = new ArrayAdapter<CauseDefaut>(this,
                android.R.layout.simple_spinner_dropdown_item,
                CauseDefautDAO.allCauseDefaut
        );
        allSDefautAdapter = new ArrayAdapter<Symptome_defaut>(this,
                android.R.layout.simple_spinner_dropdown_item,
                SymptomeDefautDAO.AllSDefaut
        );
        allSObjetAdapter = new ArrayAdapter<Symptome_objet>(this,
                android.R.layout.simple_spinner_dropdown_item,
                SymptomeObjetDAO.allSymptomeObjet
        );
        arrayAdapterIntervenant = new ArrayAdapter<Intervenant>(this,
                android.R.layout.simple_spinner_dropdown_item,
                IntervenantDAO.allIntervenant
        );
        allCObjetAdapter = new ArrayAdapter<CauseObjet>(this,
                android.R.layout.simple_spinner_dropdown_item,
                CauseObjetDAO.allCauseObjet
        );
    }

    public void arrayAdapConstructIntervAdd () {
        arrayAdapterIntervenantAdd = new ArrayAdapter<Intervenant>(this,
                android.R.layout.simple_list_item_1,
                this.intervenantsAdded
                );
    }

    private void displaySession()
    {
        ASyncWS asGetSession = new ASyncWS() {
            @Override
            protected void onPostExecute(String s) {
                refreshTV(s);
            }
        };
        asGetSession.execute("uc=getSession");
    }

    private void refreshTV(String s)
    {
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
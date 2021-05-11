package com.crays.gmagro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.crays.gmagro.daos.ActiviteDAO;
import com.crays.gmagro.daos.CauseDefautDAO;
import com.crays.gmagro.daos.CauseObjetDAO;
import com.crays.gmagro.daos.IntervenantDAO;
import com.crays.gmagro.daos.InterventionDAO;
import com.crays.gmagro.daos.MachineDAO;
import com.crays.gmagro.daos.SymptomeDefautDAO;
import com.crays.gmagro.daos.SymptomeObjetDAO;
import com.crays.gmagro.models.Activite;
import com.crays.gmagro.models.CauseDefaut;
import com.crays.gmagro.models.CauseObjet;
import com.crays.gmagro.models.IntInt;
import com.crays.gmagro.models.Intervenant;
import com.crays.gmagro.models.Intervention;
import com.crays.gmagro.models.Machine;
import com.crays.gmagro.models.Symptome_defaut;
import com.crays.gmagro.models.Symptome_objet;
import com.crays.gmagro.tools.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddIntervention extends AppCompatActivity {
    private ArrayAdapter<Activite> allActAdapter;
    private ArrayAdapter<Machine> allMachAdapter;
    private ArrayAdapter<CauseDefaut> allCDefautAdapter;
    private ArrayAdapter<CauseObjet> allCObjetAdapter;
    private ArrayAdapter<Symptome_defaut> allSDefautAdapter;
    private ArrayAdapter<Symptome_objet> allSObjetAdapter;
    private ArrayAdapter<Intervenant> arrayAdapterIntervenant;
    private ArrayList<IntInt> intervenantsAdded = new ArrayList<>();
    private ArrayAdapter<IntInt> arrayAdapterIntervenantAdd;
    private int sessionIntervenantID = 0;

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

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");

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

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

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
        arrayAdapterIntervenantAdd = new ArrayAdapter<IntInt>(this,
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
        asGetSession.execute("uc=getSession&getIntervName=sdfsf");
    }

    private void refreshTV(String s)
    {
        TextView tvInfo = findViewById(R.id.tvInfoLogin);
        tvInfo.setText("Vous etes connecté en tant que : " + s);
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

    private void getIntervSessionID() {
        ASyncWS as = new ASyncWS(){
            @Override
            protected void onPostExecute(String s)
            {
                sessionIntervenantID = Integer.parseInt(s);
            }
        };
        as.execute("uc=getSession&getIntervID=true");
    }

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
        Button btnHeureDebut = findViewById(R.id.btnShowPopUp);
        CheckBox cbInterFinie = findViewById (R.id.cbInterventionTerminee);
        Button btnHeureFin  = findViewById (R.id.btnHeureFin);
        CheckBox cbMachineStoped = findViewById (R.id.cbMachineArretee);
        Button btnTpsArret = findViewById (R.id.btnTpsArret);
        Button btnTpsPasse = findViewById(R.id.btnTpsPasse);
        Button btnAddIntervenant = findViewById(R.id.btnAddIntervLv);
        ListView lvInterv = findViewById(R.id.lvIntervenants);
        Button sendInterv = findViewById(R.id.btnSendIntervention);
        EditText etCommentaire = findViewById(R.id.editTextCommentaire);
        CheckBox cbChangementOrgane = findViewById(R.id.cbChangementOrgane);
        CheckBox cbPertes = findViewById(R.id.cbPertes);

        displaySession();
        getIntervSessionID();

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
                arrayAdapConstructIntervAdd();
                lvInterv.setAdapter(arrayAdapterIntervenantAdd);
                String tpsPasse = btnTpsPasse.getText().toString();
                int intervID = ((Intervenant) spinIntervenant.getSelectedItem()).getId();
                IntInt intIntAdded = new IntInt(0, intervID, tpsPasse);

                if (!tpsPasse.equals("Temps passé")) {
                        intervenantsAdded.add(intIntAdded);
                } else {
                    Toast.makeText(AddIntervention.this, "Le champs Temps Passé est obligatoire", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnHeureDebut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                showDateTimeDialog (btnHeureDebut);
            }
        });

        btnHeureFin.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View v) {
                showDateTimeDialog (btnHeureFin);
            }
        });

        btnTpsArret.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v) {
                showTimeDialog(btnTpsArret);
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
                    btnHeureFin.setVisibility (View.VISIBLE);
                }
                else {
                    btnHeureFin.setVisibility (View.INVISIBLE);
                    btnHeureFin.setText("Selectionnez une date et une heure");
                }
            }
        });

        cbMachineStoped.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener ()
        {
            @Override
            public void onCheckedChanged (CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnTpsArret.setVisibility (View.VISIBLE);
                }
                else {
                    btnTpsArret.setVisibility (View.INVISIBLE);
                    btnTpsArret.setText("Selectionnez une heure");
                }
            }
        });

        sendInterv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnHeureFin.equals("Selectionnez une date et une heure de debut")){
                    Date currentTime = Calendar.getInstance().getTime();
                    int id = 0;
                    String dh_debut = btnHeureDebut.getText().toString();
                    String dh_fin =  btnHeureFin.getText().toString();
                    String commentaire = etCommentaire.getText().toString();
                    String tps_arret = btnTpsArret.getText().toString();
                    boolean changement_organe = cbChangementOrgane.isChecked();
                    boolean perte = cbPertes.isChecked();
                    String dh_creation = currentTime.toString();
                    String dh_derniere_maj= currentTime.toString();
                    int idIntervenant = sessionIntervenantID;
                    String activity_code = ((Activite) spinAct.getSelectedItem()).getCode();
                    String machine_code = ((Machine) spinMach.getSelectedItem()).getCode();
                    String cause_defaut_code = ((CauseDefaut) spinCDefaut.getSelectedItem()).getCode();
                    String cause_objet_code = ((CauseObjet) spinCObjet.getSelectedItem()).getCode();
                    String symptome_defaut_code = ((Symptome_defaut) spinSymptDefaut.getSelectedItem()).getCode();
                    String symptome_objet_code = ((Symptome_objet) spinSymptObjet.getSelectedItem()).getCode();
                    if (!cbInterFinie.isChecked()) {
                        dh_fin = null;
                    }
                    if (!cbMachineStoped.isChecked()){
                        tps_arret = null;
                    }
                    Intervention intervention = new Intervention(
                            id,
                            dh_debut,
                            dh_fin,
                            commentaire,
                            tps_arret,
                            changement_organe,
                            perte,
                            dh_creation,
                            dh_derniere_maj,
                            idIntervenant,
                            activity_code,
                            machine_code,
                            cause_defaut_code,
                            cause_objet_code,
                            symptome_defaut_code,
                            symptome_objet_code
                    );
                    InterventionDAO.sendIntervention(intervention,intervenantsAdded);
                }
                else {
                    Toast.makeText(AddIntervention.this, "Vous devez insérer une date de début", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
package com.crays.gmagro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cliqueBLogin(v );

            }
        });

    }

    private void cliqueBLogin(View v)
    {
        EditText etLogin = findViewById(R.id.etLogin);
        EditText etPassword = findViewById(R.id.etPassword);
        String l = etLogin.getText().toString() ;
        String m = etPassword.getText().toString() ;
        ASyncWS as = new ASyncWS(){
            @Override
            protected void onPostExecute(String s)
            {
                traiterLeRetourConnexion(s) ;
            }
        };
        as.execute("uc=connect&mail="+ l +"&password="+ m);
    }

    private void traiterLeRetourConnexion(String s) {
        switch(s){
            case "799":
                Intent intent = new Intent(this, MainActivity2.class);
                startActivityForResult(intent, 101);
                break;
            case "801":
                Toast.makeText(this, "Vous n'etes pas SuperAdmin", Toast.LENGTH_SHORT).show();
                break;
            case "803":
                Toast.makeText(MainActivity.this, "E-mail ou Mot de passe incorrect", Toast.LENGTH_SHORT).show();
        }
    }
}
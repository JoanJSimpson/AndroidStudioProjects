package com.example.joan.ejemplotransicion;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Joan on 17/12/15.
 */
public class EjemploPreferencias extends Activity {
    private Button btnPreferencias;
    private Button btnObtenerPreferencias;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnPreferencias = (Button)findViewById(R.id.BtnPreferencias);
        btnObtenerPreferencias = (Button)findViewById(R.id.BtnObtenerPreferencias);

        btnPreferencias.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(EjemploPreferencias.this,
                        PantallaOpciones.class));
            }	});

        btnObtenerPreferencias.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences pref =
                        PreferenceManager.getDefaultSharedPreferences(
                                EjemploPreferencias.this);
                Log.i("", "OpciÛn 1: " + pref.getBoolean("opcion1", false));
                Log.i("", "OpciÛn 2: " + pref.getString("opcion2", ""));
                Log.i("", "OpciÛn 3: " + pref.getString("opcion3", ""));
            } });
    }
}

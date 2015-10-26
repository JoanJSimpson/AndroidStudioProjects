package com.example.joan.ejemlayout1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class ejemLayout1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejem_layout1);

        final AnalogClock miReloj = (AnalogClock) findViewById(R.id.reloj);
        final CheckBox miCheck = (CheckBox)findViewById(R.id.checkPrueba);
        final Button miButtonPrueba = (Button) findViewById(R.id.botonPrueba);
        final TextView miTextView = (TextView) findViewById(R.id.etiquetaPrueba);
        final Button miButtonCerrar = (Button) findViewById(R.id.cmdCerrar);


    }

}

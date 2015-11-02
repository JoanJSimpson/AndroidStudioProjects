package com.example.joan.linearlayout;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final TextView lblMensaje = (TextView) findViewById(R.id.LblSeleccion);
        final Button botonPonerColor = (Button) findViewById(R.id.boton1);
        final Button botonBlanco = (Button) findViewById(R.id.boton2);
        final RadioGroup rg = (RadioGroup) findViewById(R.id.gruporb);
        final TextView campoTexto = (TextView) findViewById(R.id.textColor);


        //rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           // public void onCheckedChanged(RadioGroup group, int checkedId) {
        botonBlanco.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                campoTexto.setBackgroundColor(Color.TRANSPARENT);
            }
        });
            botonPonerColor.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                //lblMensaje.setText("Opcion seleccionada: " + checkedId);

                //Mejora el texto informativo
                //String textoOpcion = "Opcion seleccionada: ";
                if (rg.getCheckedRadioButtonId() == R.id.radio1) {
                    campoTexto.setBackgroundColor(Color.RED);
                } else if (rg.getCheckedRadioButtonId() == R.id.radio2) {
                    campoTexto.setBackgroundColor(Color.GREEN);
                } else {
                    campoTexto.setBackgroundColor(Color.BLUE);
                }

            }
        });

    }

}

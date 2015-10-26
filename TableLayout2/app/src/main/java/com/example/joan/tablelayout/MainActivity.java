package com.example.joan.tablelayout;

import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView lblMensaje = (TextView) findViewById(R.id.LblSeleccion);
        final Button boton1 = (Button) findViewById(R.id.boton1);
        final Button boton2 = (Button) findViewById(R.id.boton2);
        final Button boton3 = (Button) findViewById(R.id.boton3);
        final Button boton4 = (Button) findViewById(R.id.boton4);

        boton1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                lblMensaje.setBackgroundColor(getResources().getColor(R.color.boton1));
            }
        });

        boton2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                lblMensaje.setBackgroundColor(getResources().getColor(R.color.boton2));
            }
        });

        boton3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                lblMensaje.setBackgroundColor(getResources().getColor(R.color.boton3));
            }
        });

        boton4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                lblMensaje.setBackgroundColor(getResources().getColor(R.color.defecto));
            }
        });



    }
}

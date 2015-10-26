package com.example.joan.ejerbotonesywidgets;

import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    int colorAcumulado;
    int color2=0;
    boolean negro1=true;
    boolean negro2=true;
    boolean negro3=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView colorFondo = (TextView) findViewById(R.id.color);
        final ImageButton boton1 = (ImageButton) findViewById(R.id.boton1);
        final ImageButton boton2 = (ImageButton) findViewById(R.id.boton2);
        final RadioGroup rg = (RadioGroup) findViewById(R.id.gruporb);
        final RadioButton r1=(RadioButton) findViewById(R.id.radio1);
        final RadioButton r2=(RadioButton) findViewById(R.id.radio2);
        final RadioButton r3=(RadioButton) findViewById(R.id.radio3);


        rg.clearCheck();
        //rg.check(R.id.radio1);
        //int idSeleccionado = rg.getCheckedRadioButtonId();

        rg.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (r1.isChecked()==true){
                            colorFondo.setBackgroundColor(getResources().getColor(R.color.rboton1));
                            colorAcumulado=getResources().getColor(R.color.rboton1);
                        }else if (r2.isChecked()==true){
                            colorFondo.setBackgroundColor(getResources().getColor(R.color.rboton2));
                            colorAcumulado=getResources().getColor(R.color.rboton2);
                        }else if (r3.isChecked()==true){
                            colorFondo.setBackgroundColor(getResources().getColor(R.color.rboton3));
                            colorAcumulado=getResources().getColor(R.color.rboton3);
                        }
                        //lblMensaje.setText("ID opcion seleccionada: " + checkedid);

                    } });



        boton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                colorFondo.setBackgroundColor(getResources().getColor(R.color.boton1));
                colorAcumulado=getResources().getColor(R.color.boton1);
            }
        });
        boton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                colorFondo.setBackgroundColor(getResources().getColor(R.color.boton2));
                colorAcumulado=getResources().getColor(R.color.boton2);
            }
        });

        final ToggleButton toggleBoton1 = (ToggleButton)findViewById(R.id.togle1);
        final ToggleButton toggleBoton2 = (ToggleButton)findViewById(R.id.togle2);
        final ToggleButton toggleBoton3 = (ToggleButton)findViewById(R.id.togle3);


        toggleBoton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0)
            {
                if(toggleBoton1.isChecked()) {

                    //colorFondo.setBackgroundColor(getResources().getColor(R.color.rboton1));
                    color2 += getResources().getColor(R.color.rboton1);
                    negro1=false;


                }else {
                    color2 -= getResources().getColor(R.color.rboton1);
                    negro1=true;

                }
                if (negro1==true && negro2==true && negro3==true)
                    colorFondo.setBackgroundColor(Color.BLACK);
                else
                    colorFondo.setBackgroundColor(color2);
            } });

        toggleBoton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0)
            {
                if(toggleBoton2.isChecked()) {
                    //colorFondo.setBackgroundColor(getResources().getColor(R.color.rboton2));
                    color2 += getResources().getColor(R.color.rboton2);
                    negro2=false;
                }
                else {
                    color2 -= getResources().getColor(R.color.rboton2);
                    negro2=true;

                }
                if (negro1==true && negro2==true && negro3==true)
                    colorFondo.setBackgroundColor(Color.BLACK);
                else
                    colorFondo.setBackgroundColor(color2);
            } });

        toggleBoton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0)
            {
                if(toggleBoton3.isChecked()) {
                    //colorFondo.setBackgroundColor(getResources().getColor(R.color.rboton3));
                    color2 += getResources().getColor(R.color.rboton3);
                    negro3=false;
                }
                else {
                    color2 -= getResources().getColor(R.color.rboton3);
                    negro3=true;

                }
                if (negro1==true && negro2==true && negro3==true)
                    colorFondo.setBackgroundColor(Color.BLACK);
                else
                    colorFondo.setBackgroundColor(color2);
            } });






    }
}

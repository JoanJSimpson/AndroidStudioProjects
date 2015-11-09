package com.example.joan.examenjpiera;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Joan on 9/11/15.
 */
public class Pantalla2 extends Activity {
    CheckBox checkReloj;
    AnalogClock analog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla2);

        final ImageView imagen = (ImageView) findViewById(R.id.imagenPantalla2);
        final Button volverBtn= (Button) findViewById(R.id.botonVolver);
        final TextView pizzaSel = (TextView) findViewById(R.id.pizzaSel);
        final TextView precioSel = (TextView) findViewById(R.id.precioSel);
        final TextView unidadesSel = (TextView) findViewById(R.id.unidadeSel);
        final TextView extraSel = (TextView) findViewById(R.id.extraSel);
        final TextView costeSel = (TextView) findViewById(R.id.costeSel);
        final TextView envioSel = (TextView) findViewById(R.id.envioSel);
        checkReloj = (CheckBox) findViewById(R.id.checkReloj);
        analog = (AnalogClock) findViewById(R.id.reloj);

        final TextView lblParentesis = (TextView) findViewById(R.id.parentesis);
        Double precioIngredientes;
        int unidades;



        Bundle miBundleRecoger = getIntent().getExtras();
        pizzaSel.setText(miBundleRecoger.getString("PIZZA"));
        precioSel.setText(String.valueOf(miBundleRecoger.getDouble("PRECIOPIZZA")));
        extraSel.setText(String.valueOf(miBundleRecoger.getDouble("PRECIOINGREDIENTES")));
        unidadesSel.setText(String.valueOf(miBundleRecoger.getInt("UNIDADES")));
        envioSel.setText(miBundleRecoger.getString("MODO"));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imagen.setBackground(getDrawable(miBundleRecoger.getInt("IMAGEN")));
        }else{
            imagen.setBackgroundDrawable(getResources().getDrawable(miBundleRecoger.getInt("IMAGEN")));
        }
        costeSel.setText(String.valueOf(miBundleRecoger.getDouble("PRECIOTOTAL")) + " €");

        checkReloj.setOnCheckedChangeListener(new myCheckBoxChangeClicker());



/*
        if (precioModo==0.0){
            lblParentesis.setText("("+precioPizza+" + ("+pesoTotal+"*"+unidades+"))");

        }else{
            lblParentesis.setText("("+precioPizza+" + ("+pesoTotal+"*"+unidades+") + ("+precioTotalSinUrgente+"*"+precioModo+"))");
        }*/



        //Boton volver --> devuelve el mensaje devuelto a principal
        //podria quitar el vueltaBundle porque no lo utilizo en la otra pantalla
        volverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vueltaIntent = new Intent();
                Bundle vueltaBundle = new Bundle();
                String mensajeDevuelto = "Devuelto a Principal";
                vueltaBundle.putString("DEVUELTO", mensajeDevuelto);
                vueltaIntent.putExtras(vueltaBundle);
                setResult(RESULT_OK, vueltaIntent);
                finish();
            }
        });



    }//Fin onCreate

    class myCheckBoxChangeClicker implements CheckBox.OnCheckedChangeListener
    { @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            if(buttonView==checkReloj) {
                analog.setVisibility(ImageView.VISIBLE);
            }

        }else{
            analog.setVisibility(ImageView.INVISIBLE);
        }
    }
    }// clase interna




    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}

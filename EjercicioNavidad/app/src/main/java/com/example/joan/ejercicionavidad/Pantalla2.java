package com.example.joan.ejercicionavidad;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Joan on 9/11/15.
 */
public class Pantalla2 extends Activity {
    TextView tit;
    Zonas zona;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.joan.ejercicionavidad.R.layout.activity_pantalla2);

        tit=(TextView) findViewById(com.example.joan.ejercicionavidad.R.id.TituloAgencia2);
        final ImageView imagen = (ImageView) findViewById(com.example.joan.ejercicionavidad.R.id.imagenPantalla2);
        final Button volverBtn= (Button) findViewById(com.example.joan.ejercicionavidad.R.id.botonVolver);
        final TextView lblZona = (TextView) findViewById(com.example.joan.ejercicionavidad.R.id.lblZona);
        final TextView lblTarifa = (TextView) findViewById(com.example.joan.ejercicionavidad.R.id.lblTarifa);
        final TextView lblDecoracion = (TextView) findViewById(com.example.joan.ejercicionavidad.R.id.lblDecoracion);
        final TextView lblPrecio = (TextView) findViewById(com.example.joan.ejercicionavidad.R.id.totalPrecio);
        final TextView lblPeso = (TextView) findViewById(com.example.joan.ejercicionavidad.R.id.lblPeso);
        final TextView lblParentesis = (TextView) findViewById(com.example.joan.ejercicionavidad.R.id.parentesis);
        String size;
        Double precioCaja, precioZona, precioPeso, precioTarifa, pesoTotal;



        Bundle miBundleRecoger = getIntent().getExtras();
        lblZona.setText(miBundleRecoger.getString("ZONA"));
        lblTarifa.setText(miBundleRecoger.getString("TARIFA"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imagen.setBackground(getDrawable(miBundleRecoger.getInt("IMAGEN")));
        }else{
            imagen.setBackgroundDrawable(getResources().getDrawable(miBundleRecoger.getInt("IMAGEN")));
        }
        //precioTotal = (precioZona + precioCaja + (Peso*precio))+((precioZona + precioCaja + (Peso*precio))*precioTarifa)
        zona = (Zonas) miBundleRecoger.getSerializable("ZONA");
        precioTarifa = zona.getPrecio();
        precioZona = miBundleRecoger.getDouble("PRECIOZONA");
        precioPeso = miBundleRecoger.getDouble("PRECIOPESO");
        pesoTotal = miBundleRecoger.getDouble("PESO");
        Double precioTotalSinUrgente = precioTarifa+(pesoTotal*precioPeso);
        lblPrecio.setText(String.valueOf(miBundleRecoger.getDouble("PRECIOTOTAL"))+" €");

        if (precioTarifa==0.0){
            lblParentesis.setText("("+precioZona+" + ("+pesoTotal+"*"+precioPeso+"))");

        }else{
            lblParentesis.setText("("+precioZona+" + ("+pesoTotal+"*"+precioPeso+") + ("+precioTotalSinUrgente+"*"+precioTarifa+"))");
        }

        size=miBundleRecoger.getString("CAJA");
        if (size.length()<20){
            lblDecoracion.setTextSize(20);
        }else if (size.length()<25){
            lblDecoracion.setTextSize(15);
        } else{
            lblDecoracion.setTextSize(12);
        }
        lblDecoracion.setText(size);
        lblPeso.setText(String.valueOf(miBundleRecoger.getDouble("PESO"))+" Kg");


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

    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }



}

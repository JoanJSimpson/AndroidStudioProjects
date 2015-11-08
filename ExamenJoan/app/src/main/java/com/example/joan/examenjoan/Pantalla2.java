package com.example.joan.examenjoan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Joan on 9/11/15.
 */
public class Pantalla2 extends Activity {
    TextView tit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla2);

        tit=(TextView) findViewById(R.id.TituloAgencia2);
        final ImageView imagen = (ImageView) findViewById(R.id.imagenPantalla2);
        final Button volverBtn= (Button) findViewById(R.id.botonVolver);
        final TextView lblZona = (TextView) findViewById(R.id.lblZona);
        final TextView lblTarifa = (TextView) findViewById(R.id.lblTarifa);
        final TextView lblDecoracion = (TextView) findViewById(R.id.lblDecoracion);
        final TextView lblPrecio = (TextView) findViewById(R.id.totalPrecio);
        final TextView lblPeso = (TextView) findViewById(R.id.lblPeso);
        final TextView lblParentesis = (TextView) findViewById(R.id.parentesis);
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
        precioCaja = miBundleRecoger.getDouble("PRECIOCAJA");
        precioZona = miBundleRecoger.getDouble("PRECIOZONA");
        precioPeso = miBundleRecoger.getDouble("PRECIOPESO");
        precioTarifa = miBundleRecoger.getDouble("PRECIOTARIFA");
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

    //**************************************
    //No me funciona el adaptador para rellenar la lista
    //**************************************
    /*

        AdaptadorZonas adaptador = new AdaptadorZonas(this);

        ListView lstOpciones = (ListView) findViewById(R.id.Lista);
        lstOpciones.setAdapter(adaptador);
        */

    }//Fin onCreate

    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    //**************************************
    //No me funciona el adaptador para rellenar la lista
    //**************************************
    /*
    class AdaptadorZonas extends ArrayAdapter {
        Activity context;

        AdaptadorZonas(Activity context){
            super(context, R.layout.listitem);
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.listitem, null);

            TextView lblBebida = (TextView) findViewById(R.id.LblBebida);
            ImageView imageItem = (ImageView) findViewById(R.id.imagenItem);
            TextView lblPrecio = (TextView) findViewById(R.id.LblPrecio);
            TextView lblVaso = (TextView) findViewById(R.id.LblVaso);
            TextView lblAperitivo = (TextView) findViewById(R.id.LblAperitivo);

            Bundle miBundleRecoger = getIntent().getExtras();
            lblBebida.setText(miBundleRecoger.getString("BEBIDA"));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageItem.setBackground(getDrawable(miBundleRecoger.getInt("IMAGEN")));
            }else{
                imageItem.setBackgroundDrawable(getResources().getDrawable(miBundleRecoger.getInt("IMAGEN")));
            }
            lblPrecio.setText(String.valueOf(miBundleRecoger.getDouble("PRECIO")));
            lblVaso.setText(miBundleRecoger.getString("VASO"));
            //lblAperitivo.setText(miBundleRecoger.getString("APERITIVO"));

            return (item);
        }
    }*/

}

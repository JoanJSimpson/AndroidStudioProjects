package com.example.joan.posibleexamenjoan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Joan on 6/11/15.
 */
public class Pantalla2 extends Activity {
    TextView tit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla2);

        tit=(TextView) findViewById(R.id.TituloRestaurante);
        final Button volverBtn= (Button) findViewById(R.id.botonVolver);
        final TextView bebidaTitulo = (TextView) findViewById(R.id.bebidaTitulo);
        final TextView bebPrecio = (TextView) findViewById(R.id.bebPrecio);
        final TextView vasoTitulo = (TextView) findViewById(R.id.vasoTitulo);
        final TextView vasoPrecio = (TextView) findViewById(R.id.vasoPrecio);
        final TextView aperitivoTitulo = (TextView) findViewById(R.id.aperitivoTitulo);
        final TextView aperPrecio = (TextView) findViewById(R.id.aperPrecio);
        final TextView totalTitulo = (TextView) findViewById(R.id.totalTitulo);
        final TextView totalPrecio = (TextView) findViewById(R.id.totalPrecio);
        final LinearLayout linear = (LinearLayout) findViewById(R.id.linear);
        final TableRow separador = (TableRow) findViewById(R.id.SeparadorCabecera);
        String size;
        ///////para cambiar el tipo de fuente
        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/akbar.ttf");
        tit.setTypeface(face);
        //////////

        Bundle miBundleRecoger = getIntent().getExtras();
        bebidaTitulo.setText(miBundleRecoger.getString("BEBIDA"));
        bebPrecio.setText(String.valueOf(miBundleRecoger.getDouble("BEBIDAPRECIO")));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            linear.setBackground(getDrawable(miBundleRecoger.getInt("IMAGEN")));
            if ((String.valueOf(miBundleRecoger.getInt("IMAGEN"))).equals(String.valueOf(R.drawable.cocacola))){
                aperitivoTitulo.setTextColor(Color.WHITE);
                aperPrecio.setTextColor(Color.WHITE);
                vasoTitulo.setTextColor(Color.WHITE);
                vasoPrecio.setTextColor(Color.WHITE);
                bebidaTitulo.setTextColor(Color.WHITE);
                bebPrecio.setTextColor(Color.WHITE);
                totalPrecio.setTextColor(Color.WHITE);
                totalTitulo.setTextColor(Color.WHITE);
                separador.setBackgroundColor(Color.WHITE);
            }
        }else{
            linear.setBackgroundDrawable(getResources().getDrawable(miBundleRecoger.getInt("IMAGEN")));
        }
        totalPrecio.setText(String.valueOf(miBundleRecoger.getDouble("PRECIO"))+" €");

        size=miBundleRecoger.getString("APERITIVOS");
        if (size.length()<10){
            aperitivoTitulo.setTextSize(20);
        } else if (size.length()<20){
            aperitivoTitulo.setTextSize(15);
        }
        aperitivoTitulo.setText(size);
        aperPrecio.setText(String.valueOf(miBundleRecoger.getDouble("APERITIVOSPRECIO")));

        vasoTitulo.setText(miBundleRecoger.getString("VASO"));
        vasoPrecio.setText(String.valueOf(miBundleRecoger.getDouble("VASOPRECIO")));

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

        AdaptadorBebidas adaptador = new AdaptadorBebidas(this);

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
    class AdaptadorBebidas extends ArrayAdapter {
        Activity context;

        AdaptadorBebidas(Activity context){
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

package com.example.joan.posibleexamenjoan;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Joan on 6/11/15.
 */
public class Pantalla2 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla2);

        final Button volverBtn= (Button) findViewById(R.id.botonVolver);
        final TextView bebSel = (TextView) findViewById(R.id.bebSel);
        final TextView vasoSel = (TextView) findViewById(R.id.vasSel);
        final TextView aperSel = (TextView) findViewById(R.id.aperSel);
        final TextView totalSel = (TextView) findViewById(R.id.totalSel);
        final LinearLayout linear = (LinearLayout) findViewById(R.id.linear);

        Bundle miBundleRecoger = getIntent().getExtras();
        bebSel.setText(miBundleRecoger.getString("BEBIDA"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            linear.setBackground(getDrawable(miBundleRecoger.getInt("IMAGEN")));
        }else{
            linear.setBackgroundDrawable(getResources().getDrawable(miBundleRecoger.getInt("IMAGEN")));
        }
        totalSel.setText(String.valueOf(miBundleRecoger.getDouble("PRECIO")));
        //String mensaje2=String.valueOf(miBundleRecoger.getDouble("PRECIO"));
        //totalSel.setText(mensaje2);
        //showToast(mensaje2);
        //vasoSel.setText(miBundleRecoger.getString("VASO"));
        aperSel.setText(miBundleRecoger.getString("APERITIVOS"));
        vasoSel.setText(miBundleRecoger.getString("VASO"));
        //showToast(miBundleRecoger.getString("APERITIVOS"));

        //Boton volver --> devuelve el mensaje devuelto a principal
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

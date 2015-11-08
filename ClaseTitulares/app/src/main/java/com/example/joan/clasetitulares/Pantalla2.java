package com.example.joan.clasetitulares;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Joan on 5/11/15.
 */
public class Pantalla2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla2);


        //poner en TextView lo que viene de la pantalla anterior
        final TextView total= (TextView) findViewById(R.id.miTotal);
        Bundle miBundleRecoger = getIntent().getExtras();
        total.setText(miBundleRecoger.getString("TEXTO"));


        final Button volverBtn= (Button) findViewById(R.id.botonVolver);


        //Boton volver --> devuelve el mensaje devuelto a principal
        volverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vueltaIntent = new Intent();
                Bundle vueltaBundle=new Bundle();
                String mensajeDevuelto = "Devuelto a Principal";
                vueltaBundle.putString("DEVUELTO",mensajeDevuelto);
                vueltaIntent.putExtras(vueltaBundle);
                setResult(RESULT_OK, vueltaIntent);
                finish();
            }
        });

        showToast("Pantalla 2 ShowToast");
        //showAlert("Pantalla 2 ShowAlert");
    }

    protected void showToast (CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    protected void showAlert( CharSequence text) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(text);
        alert.setPositiveButton(android.R.string.ok, null);
        alert.show();
    }




}

package com.example.joan.holamundo3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


public class holaMundo3 extends AppCompatActivity {
    public static int COD_RESPUESTA=0;
    TextView elSaludo;
    static int miPosicion;
    static MediaPlayer miMusica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hola_mundo3);

        final EditText miTexto = (EditText) findViewById(R.id.miTxt);
        final Button miBoton= (Button) findViewById(R.id.miBtn);
        final ImageButton botonMusica = (ImageButton) findViewById(R.id.miBtnMusica);

         /*Musica*/
        miMusica= MediaPlayer.create(getApplicationContext(),R.raw.ballad);


       // miMusica.start();


        elSaludo=(TextView)findViewById(R.id.miLbl);

        //Borrar el texto inicial del EditText
        miTexto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean b) {
                if (b) miTexto.setText("");
            }

        });

        botonMusica.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (miMusica.isPlaying()) {
                    miMusica.pause();
                } else {
                    miMusica.start();
                }

            }
        });


        miBoton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent miIntent = new Intent(holaMundo3.this, Pantalla2.class);
                Bundle miBundle = new Bundle();
                String mensajePaso = "Te saludo " + miTexto.getText() + " pequeño padaguan";
                miBundle.putString("TEXTO", mensajePaso);
                miIntent.putExtras(miBundle);
                startActivityForResult(miIntent, COD_RESPUESTA);

            }
        });


//        CharSequence posicion = getString(miPosicion);
  //      showAlert(posicion);
        showToast("Pantalla Principal ShowToast");
        //showAlert("Pantalla Principal ShowAlert");
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

    public void onActivityResult(int cod_resp, int cod_result, Intent intent){
        if (cod_result==RESULT_OK){
            Bundle otroBundle = intent.getExtras();
            elSaludo.setText(otroBundle.getString("DEVUELTO"));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this,"onStart", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        miMusica.seekTo(miPosicion);
        miMusica.start();
        Toast.makeText(this,"onResume", Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onPause() {
        Toast.makeText(this,"onPause", Toast.LENGTH_SHORT).show();
        super.onPause();
        if (miMusica.isPlaying() && miMusica != null) {
            miMusica.pause();
            miPosicion = miMusica.getCurrentPosition();

        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this,"onStop", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this,"onRestart", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        Toast.makeText(this,"onDestroy", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}

package com.example.joan.spinnersimple;


import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Joan on 2/11/15.
 */
public class MySpinnerSimple extends AppCompatActivity {

        Spinner miSpinner;

    final static String semana[] = {"Joan", "Miguel Angel", "Skoner el loco", "Kristaps", "Oscar"};
    final static int imagenes[] = {R.drawable.imagen, R.drawable.imagen2,R.drawable.imagen3,R.drawable.imagen4,R.drawable.imagen5};

public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_spinner_simple);

        //String mensaje;
        final ImageView imagenFondo = (ImageView)findViewById(R.id.imagen);
        miSpinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> miAdaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, semana);

        miAdaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); /* MIRAR BIEN( es diferente al ListaView) */
        miSpinner.setAdapter(miAdaptador);
        miSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                /*evento distinto al ListView */
                public void onItemSelected(AdapterView arg0, View arg1, int position, long id) {
                        String mensaje = "";
                        mensaje = "Has pulsado: " + semana[position];
                        showToast(mensaje);
                    //no funciona en API 15, si en emulador API 21
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        imagenFondo.setBackground(getDrawable(imagenes[position]));
                    }else{
                        imagenFondo.setBackgroundDrawable(getResources().getDrawable(imagenes[position]));
                    }


                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
        });
}
public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        }
}

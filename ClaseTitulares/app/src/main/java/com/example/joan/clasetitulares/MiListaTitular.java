package com.example.joan.clasetitulares;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MiListaTitular extends AppCompatActivity {
    public static int COD_RESPUESTA=0;

    public static int suma=0;

    private Titular[] datos = new Titular[]{
        new Titular("Titulo 1", "Subtitulo largo 1", 30, R.drawable.imagen),
        new Titular("Titulo 2", "Subtitulo largo 2", 25, R.drawable.imagen1),
        new Titular("Titulo 3", "Subtitulo largo 3", 20, R.drawable.imagen2),
        new Titular("Titulo 4", "Subtitulo largo 4", 15, R.drawable.imagen3),
        new Titular("Titulo 5", "Subtitulo largo 5", 10, R.drawable.imagen4),
    };

    ListView miLista;
    TextView vuelta;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_clase_titular);

        //Declaracion de variables
        final Button miBoton = (Button) findViewById(R.id.miBtn);
        miLista = (ListView) findViewById(R.id.ListTitular);
        AdaptadorTitulares adaptador = new AdaptadorTitulares(this);

        //Rellenar miLista
        miLista.setAdapter(adaptador);
        miLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String mensaje = datos[position].toString();
                showToast(mensaje);
                String mensaje2 = String.valueOf(datos[position].getEdad());
                showToast(mensaje2);
                suma+=datos[position].getEdad();
            }
        });//miLista

        //Boton para pasar a la siguiente pantalla
        miBoton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent miIntent = new Intent(MiListaTitular.this, Pantalla2.class);
                Bundle miBundle = new Bundle();
                //String mensajePaso = String.valueOf(datos[1].getEdad());
                miBundle.putString("TEXTO", String.valueOf(suma));
                miIntent.putExtras(miBundle);
                startActivityForResult(miIntent, COD_RESPUESTA);
                //startActivity(miIntent);
            }
        });//miBoton
    }//onCreate


    //Metodo showToast
    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    //Metodo onActivityResult
    public void onActivityResult(int cod_resp, int cod_result, Intent intent){
        if (cod_result==RESULT_OK){
            Bundle otroBundle = intent.getExtras();
            vuelta = (TextView) findViewById(R.id.txtViewVuelta);
            vuelta.setText(otroBundle.getString("DEVUELTO"));
        }
    }//Fin onActivityResult


    //Clase AdaptadorTitulares para que meta dentro todo lo que queramos
    class AdaptadorTitulares extends ArrayAdapter {
        Activity context;

        AdaptadorTitulares(Activity context) {
            super(context, R.layout.listitem_titular, datos);
            this.context = context;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.listitem_titular, null);

            TextView lblTitulo = (TextView) item.findViewById(R.id.LblTitulo);
            lblTitulo.setText(datos[position].getTitulo());

            TextView lblSubtitulo = (TextView) item.findViewById(R.id.LblSubTitulo);
            lblSubtitulo.setText(datos[position].getSubtitulo());

            TextView lblEdad = (TextView) item.findViewById(R.id.LblEdad);
            lblEdad.setText(String.valueOf(datos[position].getEdad()));

            ImageView imagenFondo = (ImageView) item.findViewById(R.id.imagen);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imagenFondo.setBackground(getDrawable(datos[position].getImagen()));
            }else{
                imagenFondo.setBackgroundDrawable(getResources().getDrawable(datos[position].getImagen()));
            }

            return (item);
        }
    }//Fin AdaptadorTitulares

}
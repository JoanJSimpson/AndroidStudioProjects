package com.example.joan.clasetitulares;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MiListaTitular extends AppCompatActivity {

    private Titular[] datos = new Titular[]{
        new Titular("Titulo 1", "Subtitulo largo 1"),
        new Titular("Titulo 2", "Subtitulo largo 2"),
        new Titular("Titulo 3", "Subtitulo largo 3"),
        new Titular("Titulo 4", "Subtitulo largo 4"),
        new Titular("Titulo 5", "Subtitulo largo 5"),
    };

    ListView miLista;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_clase_titular);

        //String mensaje;
        miLista = (ListView) findViewById(R.id.ListTitular);

        AdaptadorTitulares adaptador = new AdaptadorTitulares(this);
        miLista.setAdapter(adaptador);
        miLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String mensaje=datos[position].toString();
                showToast(mensaje);
            }
        });


    }//onCreate

    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }



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
            return (item);
        }
    }

}
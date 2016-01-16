package com.example.joan.ejercicionavidad;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Joan on 15/1/16.
 */
public class ListaVista extends AppCompatActivity {


    String[] clientes;
    DatosClientes[] datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.example.joan.ejercicionavidad.R.layout.listavista);

        listar();
        ListView lista = (ListView) findViewById(com.example.joan.ejercicionavidad.R.id.listViewClientes);

        //Adaptador listView
        miAdaptador adaptador = new miAdaptador(this);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                showToast("Pulsado: "+datos[position].toString());
                //String item =

                //Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();

            }
        });

    }



    public void listar() {

        SQLiteHelper sqliteHelper = new SQLiteHelper(this, "DBClientes.sqlite", null, 1);
        SQLiteDatabase bd = sqliteHelper.getReadableDatabase();

        if (bd != null) {
            Cursor cursor = bd.rawQuery("SELECT * FROM Clientes", null);
            int cantidad = cursor.getCount();
            int i = 0;
            //String[] clientes = new String[cantidad];
            datos = new DatosClientes[cantidad];

            if (cursor.moveToFirst()) {
                do {
                    DatosClientes datosAInsertar = new DatosClientes(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6),
                            cursor.getDouble(7), cursor.getString(8), cursor.getDouble(9), cursor.getInt(10));
                    datos[i]=datosAInsertar;
                    i++;
                    /*String linea = cursor.getInt(0) + " - " + cursor.getString(1) + "\n" + cursor.getString(2)
                            + "\n" + cursor.getString(3) + "\n" + cursor.getInt(4)
                            + "\n" + cursor.getString(5) + "\n"  + cursor.getString(6)
                            + "\n" + cursor.getDouble(7) + "\n" + cursor.getString(8)
                            + "\n" + cursor.getDouble(9)+ "\n" + cursor.getInt(10);
                    clientes[i] = linea;
                    i++;*/
                } while (cursor.moveToNext());
            }

            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, clientes);
            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, clientes);

            //lista.setAdapter(adapter);

            cursor.close();
            bd.close();
        }


    }


    class miAdaptador extends ArrayAdapter<Object> {
        Activity context;

        miAdaptador(Activity context) {

            super(context, com.example.joan.ejercicionavidad.R.layout.listadaptador, datos);
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(com.example.joan.ejercicionavidad.R.layout.listadaptador, null);
            LinearLayout linear = (LinearLayout) item.findViewById(com.example.joan.ejercicionavidad.R.id.campoLinear);
            TextView id = (TextView) item.findViewById(com.example.joan.ejercicionavidad.R.id.campoId);
            TextView nombre = (TextView) item.findViewById(com.example.joan.ejercicionavidad.R.id.campoNombre);
            TextView apellidos = (TextView) item.findViewById(com.example.joan.ejercicionavidad.R.id.campoApellidos);
            TextView email = (TextView) item.findViewById(com.example.joan.ejercicionavidad.R.id.campoEmail);
            TextView telefono = (TextView) item.findViewById(com.example.joan.ejercicionavidad.R.id.campoTelefono);
            TextView zona = (TextView) item.findViewById(com.example.joan.ejercicionavidad.R.id.campoZona);
            TextView tarifa = (TextView) item.findViewById(com.example.joan.ejercicionavidad.R.id.campoTarifa);
            TextView peso = (TextView) item.findViewById(com.example.joan.ejercicionavidad.R.id.campoPeso);
            TextView decoracion = (TextView) item.findViewById(com.example.joan.ejercicionavidad.R.id.campoDecoracion);
            TextView coste = (TextView) item.findViewById(com.example.joan.ejercicionavidad.R.id.campoCoste);
            //ImageView imagen = (ImageView) item.findViewById(R.id.campoImagen);

            id.setText(String.valueOf("ID: "+datos[position].getId()));
            nombre.setText("Nombre: "+datos[position].getNombre());
            apellidos.setText("Apellidos: "+datos[position].getApellidos());
            email.setText("Email: "+datos[position].getEmail());
            telefono.setText("Telefono: "+String.valueOf(datos[position].getTelefono()));
            zona.setText("Zona: "+datos[position].getZona());
            tarifa.setText("Tarifa: "+String.valueOf(datos[position].getTarifa()));
            peso.setText("Peso: "+String.valueOf(datos[position].getPeso())+" Kg");
            decoracion.setText("Decoración: "+datos[position].getDecoracion());
            coste.setText(String.valueOf("Precio: "+datos[position].getCoste())+" €");
            linear.setBackground(getDrawable(datos[position].getImagen()));
  //          imagen.setBackground(getDrawable(datos[position].getImagen()));

            return (item);
        }

    }






    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}


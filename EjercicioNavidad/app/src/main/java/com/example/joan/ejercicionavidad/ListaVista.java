package com.example.joan.ejercicionavidad;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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

    DatosClientes[] datos;



    protected void onUpdate (Bundle savedInstanceState) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.listavista);

        listar();
        ListView lista = (ListView) findViewById(com.example.joan.ejercicionavidad.R.id.listViewClientes);

        //Adaptador listView
        final miAdaptador adaptador = new miAdaptador(this);
        lista.setAdapter(adaptador);

        //Menu para eliminar al dejar pulsado una linea del ListView
        lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lista.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            private int idSeleccionado;

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                // Here you can do something when items are selected/de-selected,
                // such as update the title in the CAB
                //showToast("PULSADO ID: "+datos[position].getId());
                idSeleccionado = datos[position].getId();
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // Respond to clicks on the actions in the CAB
                switch (item.getItemId()) {
                    case R.id.eliminar:
                        eliminar(idSeleccionado);
                        recargar();
                        //showToast("ELIMINAR id: "+idSeleccionado);
                        mode.finish(); // Action picked, so close the CAB
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Inflate the menu for the CAB
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.menu, menu);
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // Here you can make any necessary updates to the activity when
                // the CAB is removed. By default, selected items are deselected/unchecked.
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // Here you can perform updates to the CAB due to
                // an invalidate() request
                return false;
            }
        });//final menu contextual listView

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

    public void eliminar(int id) {

        SQLiteHelper admin = new SQLiteHelper(this, "DBClientes.sqlite", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String[] idBorrar = new String[]{String.valueOf(id)};

        bd.delete("Clientes", "id=?", idBorrar);

        //bd.insert("Clientes", null, contentValues);

        bd.close();

        showToast("Cliente eliminado correctamente");


    }//fin eliminar


    class miAdaptador extends ArrayAdapter<Object> {
        Activity context;

        miAdaptador(Activity context) {

            super(context, com.example.joan.ejercicionavidad.R.layout.listadaptador, datos);
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.listadaptador, null);
            LinearLayout linear = (LinearLayout) item.findViewById(R.id.campoLinear);
            TextView id = (TextView) item.findViewById(R.id.campoId);
            TextView nombre = (TextView) item.findViewById(R.id.campoNombre);
            TextView apellidos = (TextView) item.findViewById(R.id.campoApellidos);
            TextView email = (TextView) item.findViewById(R.id.campoEmail);
            TextView telefono = (TextView) item.findViewById(R.id.campoTelefono);
            TextView zona = (TextView) item.findViewById(R.id.campoZona);
            TextView tarifa = (TextView) item.findViewById(R.id.campoTarifa);
            TextView peso = (TextView) item.findViewById(R.id.campoPeso);
            TextView decoracion = (TextView) item.findViewById(R.id.campoDecoracion);
            TextView coste = (TextView) item.findViewById(R.id.campoCoste);
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                linear.setBackground(getDrawable(datos[position].getImagen()));
            }

            return (item);
        }
    }
    public void recargar() {

        Intent home_intent = new Intent(getApplicationContext(), ListaVista.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(home_intent);
    }

    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}


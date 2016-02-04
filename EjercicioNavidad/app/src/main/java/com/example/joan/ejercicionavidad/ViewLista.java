package com.example.joan.ejercicionavidad;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joan on 15/1/16.
 */
public class ViewLista extends AppCompatActivity {

    //====================================================================================
    //                  Variables de clase
    //====================================================================================

    ClaseUsuario user = null;
    ClasePedido[] pedidos;

    protected void onUpdate (Bundle savedInstanceState) {
    }


    //====================================================================================
    //                  ONCREATE
    //====================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.listavista);


        if (user == null) {
            Bundle bundle = getIntent().getExtras();
            user = (ClaseUsuario) bundle.getSerializable("USER2");

        }

        rellenarPedidos(user.getDni());
        ListView lista = (ListView) findViewById(R.id.listViewClientes);


        //====================================================================================
        //                  LISTVIEW
        //====================================================================================
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
                idSeleccionado = pedidos[position].getId();
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

    //====================================================================================
    //                  METODOS
    //====================================================================================

    public boolean rellenarPedidos(String dni){
        SQLiteHelper2 sql = new SQLiteHelper2(getApplicationContext(), "DBClientes.sqlite", null, 1);

        pedidos = sql.getAllPedidosUsuario(dni);

        if (pedidos.length == 0){
            showToast("No ha hecho ningun pedido");
            return false;
        }
        return true;

    }//fin rellenarPedidos()


    public void eliminar(int id) {

        SQLiteHelper2 admin = new SQLiteHelper2(this, "DBClientes.sqlite", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String[] idBorrar = new String[]{String.valueOf(id)};

        bd.delete("pedidos", "id=?", idBorrar);


        bd.close();

        showToast("Pedido eliminado correctamente");


    }//fin eliminar()

    public void recargar() {

        Intent home_intent = new Intent(getApplicationContext(), ViewLista.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle miBundle = new Bundle();
        miBundle.putSerializable("USER2", user);

        home_intent.putExtras(miBundle);

        startActivity(home_intent);
    }//fin recargar()

    //====================================================================================
    //                  ADAPTADOR LISTVIEW
    //====================================================================================

    class miAdaptador extends ArrayAdapter<Object> {
        Activity context;

        miAdaptador(Activity context) {

            super(context, R.layout.listaadaptador2, pedidos);
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.listaadaptador2, null);
            LinearLayout linear = (LinearLayout) item.findViewById(R.id.campoLinear);
            TextView dni = (TextView) item.findViewById(R.id.campoDni);
            TextView zona = (TextView) item.findViewById(R.id.campoZona);
            TextView tarifa = (TextView) item.findViewById(R.id.campoTarifa);
            TextView peso = (TextView) item.findViewById(R.id.campoPeso);
            TextView decoracion = (TextView) item.findViewById(R.id.campoDecoracion);
            TextView precio = (TextView) item.findViewById(R.id.campoPrecio);
            ImageView imagen = (ImageView) item.findViewById(R.id.campoImagen);


            dni.setText(String.valueOf("   DNI: "+pedidos[position].getUsuarioDni()));
            zona.setText("   Zona: "+pedidos[position].getZonaId());
            tarifa.setText("   Tarifa: "+String.valueOf(pedidos[position].getTarifa()));
            peso.setText("   Peso: "+String.valueOf(pedidos[position].getPeso())+" Kg");
            decoracion.setText("   Decoración: "+pedidos[position].getDecoracion());
            precio.setText(String.valueOf("   Precio: "+pedidos[position].getPrecio())+" €");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                imagen.setBackground(getDrawable(pedidos[position].getImagen()));
            }

            return (item);
        }
    }

    //====================================================================================
    //                  SHOWTOAST
    //====================================================================================
    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}


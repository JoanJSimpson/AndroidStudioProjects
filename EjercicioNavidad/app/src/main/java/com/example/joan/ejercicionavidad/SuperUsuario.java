package com.example.joan.ejercicionavidad;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Joan on 20/1/16.
 */
public class SuperUsuario extends AppCompatActivity {

//====================================================================================
//                  Variables de clase
//====================================================================================

    private List<ClaseUsuario> usuarios = null;
    private ClaseUsuario[] usuarios2;
    private List<ClaseHistorial> historial = null;
    private List<ClasePedido> todosPedidos = null;


//====================================================================================
//                  MENú
//====================================================================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Alternativa 1
        getMenuInflater().inflate(R.menu.menu_salir, menu);
        return true;
    }

    //Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.crearHistorial:
                crearHistorial();
                return true;
            case R.id.verTodos:
                verTodos();
                return true;
            case R.id.salir:
                salir();
                return true;
            case R.id.acercaDe:
                acercaDe();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


//====================================================================================
//                  Elementos del menú
//====================================================================================

    //TODO crear un elemento del menú para ver todos los pedidos de todos los usuarios

    //Elementos del menu
    private void salir(){
        finish();
    }


    private void acercaDe(){
        new DialogoPersonalizado().show(getSupportFragmentManager(), "DialogoPersonalizado");
    }

    public void eliminar(String id) {
        SQLiteHelper2 admin = new SQLiteHelper2(this, "DBClientes.sqlite", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String[] idBorrar = new String[]{String.valueOf(id)};

        //SIN ESTO NO HACE EL ON DELETE CASCADE
        bd.execSQL("PRAGMA foreign_keys = ON");
        bd.delete("usuarios", "dni=?", idBorrar);
        bd.close();
        admin.close();

        showToast("Usuario eliminado correctamente");
    }//fin eliminar

    private void crearHistorial(){
        SQLiteHelper2 sql = new SQLiteHelper2(getApplicationContext(), "DBClientes.sqlite", null, 1);
        SQLiteDatabase bd = sql.getWritableDatabase();
        //Eliminamos todos los datos antes, para que no haya duplicados
        //todo podria poner que en lugar de eliminar, le añada la fecha de creación del historial
        bd.execSQL("delete from historial");
        historial = sql.getAllHistorial();
        for (int i=0;i<historial.size();i++) {
            //showToast(historial.get(i).toString());
            sql.crearHistorial(historial.get(i));
        }
        sql.close();;
    }

    private void verTodos(){

    }


    private void rellenarUsuarios(){
        SQLiteHelper2 sql = new SQLiteHelper2(getApplicationContext(), "DBClientes.sqlite", null, 1);
        usuarios = sql.getTodosUsuarios();
        sql.close();
    }


    public void recargar() {
        Intent home_intent = new Intent(getApplicationContext(), SuperUsuario.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle miBundle = new Bundle();
        miBundle.putSerializable("USERRECARGA", usuarios2);

        home_intent.putExtras(miBundle);
        startActivity(home_intent);
    }

//====================================================================================
//                  ON CREATE
//====================================================================================


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_usuarios);

        if (usuarios2 != null) {
            Bundle bundle = getIntent().getExtras();
            usuarios2 = (ClaseUsuario[]) bundle.getSerializable("USERRECARGA");

        }else {
            rellenarUsuarios();
        }
        ListView lista = (ListView) findViewById(R.id.listViewUsuarios);


        //Adaptador listView
        final miAdaptador adaptador = new miAdaptador(this);
        lista.setAdapter(adaptador);


        //Menu para eliminar al dejar pulsado una linea del ListView
        lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lista.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            private String idSeleccionado;

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                // Here you can do something when items are selected/de-selected,
                // such as update the title in the CAB
                idSeleccionado = usuarios.get(position).getDni();
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // Respond to clicks on the actions in the CAB
                switch (item.getItemId()) {
                    case R.id.eliminar:
                        eliminar(idSeleccionado);
                        recargar();
                        //showToast("ELIMINAR id: " + idSeleccionado);
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
//                  Metodos
//====================================================================================



//====================================================================================
//                  Adaptadores
//====================================================================================
    //Metodo miAdaptador para rellenar ListView
    class miAdaptador extends ArrayAdapter<ClaseUsuario> {
        Activity context;

        miAdaptador(Activity context) {
            super(context, R.layout.lista_adaptador_clientes, usuarios);
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.lista_adaptador_clientes, null);

            TextView user = (TextView) item.findViewById(R.id.userUsuario);
            TextView password = (TextView) item.findViewById(R.id.userPassword);
            TextView dni = (TextView) item.findViewById(R.id.userDni);
            TextView nombre = (TextView) item.findViewById(R.id.userNombre);
            TextView apellidos = (TextView) item.findViewById(R.id.userApellidos);
            TextView email = (TextView) item.findViewById(R.id.userEmail);
            TextView telefono = (TextView) item.findViewById(R.id.userTelefono);


            user.setText("   Usuario: "+ usuarios.get(position).getUser());
            password.setText("   Password: "+ usuarios.get(position).getPassword());
            dni.setText("   DNI: "+ usuarios.get(position).getDni());
            nombre.setText("   Nombre: " + usuarios.get(position).getNombre());
            apellidos.setText("   Apellidos: "+ usuarios.get(position).getApellidos());
            telefono.setText("   Telefono: "+String.valueOf(usuarios.get(position).getTelefono()));
            email.setText("   Email: "+ usuarios.get(position).getEmail());
            return (item);
        }
    }



    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}


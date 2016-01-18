package com.example.joan.ejercicionavidad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Joan on 18/1/16.
 */
public class FormNuevoUsuario extends AppCompatActivity{
    private String user, nombre, apellidos, email, password, dni, telefono_comprobar;
    private int telefono;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formnuevousuario);

        /*
        Creamos los Objetos del xml
         */

        final EditText lblUser = (EditText) findViewById(R.id.nuUser);
        final EditText lblNombre = (EditText) findViewById(R.id.nuNombre);
        final EditText lblApellidos = (EditText) findViewById(R.id.nuApellidos);
        final EditText lblTelefono = (EditText) findViewById(R.id.nuTelefono);
        final EditText lblEmail = (EditText) findViewById(R.id.nuEmail);
        final EditText lblDni = (EditText) findViewById(R.id.nuDni);
        final EditText lblPassword = (EditText) findViewById(R.id.nuContrasena);
        final Button botonValidar = (Button) findViewById(R.id.nuBotonValidar);


        //Cuando pulsamos el boton
        botonValidar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                user = lblUser.getText().toString();
                password = lblPassword.getText().toString();
                nombre = lblNombre.getText().toString();
                dni = lblDni.getText().toString();
                apellidos = lblApellidos.getText().toString();
                email = lblEmail.getText().toString();
                telefono_comprobar = lblTelefono.getText().toString();
                //Comprobamos que todos los campos se hayan rellenado
                int comprueba = comprobar(user, password, dni, nombre, apellidos, email, telefono_comprobar);
                switch (comprueba) {
                    case 0:
                        break;
                    case 1:
                        SQLiteHelper2 sql = new SQLiteHelper2( getApplicationContext(), "DBClientes.sqlite", null, 1);
                        ClaseUsuario compruebaUsuario = sql.getUsuario(dni);
                        if (compruebaUsuario != null){
                            if (compruebaUsuario.getDni().equals(dni)){
                                showToast("Ya existe el usuario");
                            }else{
                                ClaseUsuario usuario = new ClaseUsuario(Integer.parseInt(telefono_comprobar), user, password, dni, nombre, apellidos, email);
                                sql.crearUsuario(usuario);
                                Bundle miBundle = new Bundle();
                                miBundle.putSerializable("USER", usuario);
                                Intent miIntent = new Intent(FormNuevoUsuario.this, FormPedido.class);

                                miIntent.putExtras(miBundle);
                                startActivity(miIntent);
                            }

                        }else {
                            ClaseUsuario usuario = new ClaseUsuario(Integer.parseInt(telefono_comprobar), user, password, dni, nombre, apellidos, email);
                            sql.crearUsuario(usuario);
                            Bundle miBundle = new Bundle();
                            miBundle.putSerializable("USERFORM", usuario);
                            Intent miIntent = new Intent(FormNuevoUsuario.this, FormPedido.class);

                            miIntent.putExtras(miBundle);
                            startActivity(miIntent);
                        }
                }
            }
        });
    }


    public int comprobar(String user, String password, String dni, String nombre, String apellidos, String email, String telefono) {
        if (user.equals("")) {
            showToast("El usuario no puede estar vacío");
            return 0;
        } else if (password.equals("")){
            showToast("La contraseña no puede estar vacía");
            return 0;
        } else if (dni.equals("")){
            showToast("El DNI no puede estar vacía");
            return 0;
        } else if (nombre.equals("")) {
            showToast("El nombre no puede estar vacío");
            return 0;
        } else if (apellidos.equals("")) {
            showToast("Los apellidos no pueden estar vacíos");
            return 0;
        } else if (email.equals("")) {
            showToast("El email no puede estar vacío");
            return 0;
        } else if (telefono.equals("")) {
            showToast("El teléfono no puede estar vacío");
            return 0;
        }

        return 1;

    }
/*
    public void insertarDatos(Usuario usuario) {

        SQLiteHelper admin = new SQLiteHelper(this, "DBClientes.sqlite", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();


        ContentValues contentValues = new ContentValues();

        contentValues.put("user", usuario.getUsuario());
        contentValues.put("password", usuario.getPassword());
        contentValues.put("nombre", usuario.getNombre());
        contentValues.put("apellidos", usuario.getApellidos());
        contentValues.put("email", usuario.getEmail());
        contentValues.put("telefono", usuario.getTelefono());

        //TODO intentar comprobar que se inserta correctamente

        bd.insert("Usuarios", null, contentValues);

        bd.close();

        showToast("Usuario guardado correctamente");
    }

    public Usuario[] listar() {

        SQLiteHelper sqliteHelper = new SQLiteHelper(this, "DBClientes.sqlite", null, 1);
        SQLiteDatabase bd = sqliteHelper.getReadableDatabase();
        Usuario usuario[]=null;

        if (bd != null) {
            Cursor cursor = bd.rawQuery("SELECT * FROM Usuarios", null);
            int cantidad = cursor.getCount();
            int i = 0;
            //String[] clientes = new String[cantidad];
            String compruebaUsuario;
            usuario = new Usuario[cantidad];
            //datos = new DatosClientes[cantidad];

            if (cursor.moveToFirst()) {
                do {
                    usuario[i] = new Usuario(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5));
                    /*DatosClientes datosAInsertar = new DatosClientes(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6),
                            cursor.getDouble(7), cursor.getString(8), cursor.getDouble(9), cursor.getInt(10));
                    datos[i] = datosAInsertar;
                    i++;
                } while (cursor.moveToNext());
            }


            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, clientes);
            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, clientes);

            //lista.setAdapter(adapter);

            cursor.close();
            bd.close();
        }
        return usuario;
    }
    */

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}
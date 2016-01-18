package com.example.joan.ejercicionavidad;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Joan on 16/1/16.
 */
public class NuevoUsuario extends Activity{
    private String user, nombre, apellidos, email, password;
    private int telefono;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevousuario);


        final EditText lblUser = (EditText) findViewById(R.id.nuUser);
        final EditText lblNombre = (EditText) findViewById(R.id.nuNombre);
        final EditText lblApellidos = (EditText) findViewById(R.id.nuapellidos);
        final EditText lblTelefono = (EditText) findViewById(R.id.nutelefono);
        final EditText lblEmail = (EditText) findViewById(R.id.nuemail);
        final EditText lblPassword = (EditText) findViewById(R.id.nuContrasena_input);

        final Button botonValidar = (Button) findViewById(R.id.nuBotonValidar);


        botonValidar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int comprueba = comprobar(String.valueOf(lblUser.getText()), String.valueOf(lblPassword.getText()), String.valueOf(lblNombre.getText()), String.valueOf(lblApellidos.getText()),
                        String.valueOf(lblEmail.getText()), String.valueOf(lblTelefono.getText()));
                switch (comprueba) {
                    case 0:
                        break;
                    case 1:

                        boolean existe=false;
                        user = lblUser.getText().toString();
                        password = lblPassword.getText().toString();
                        nombre = lblNombre.getText().toString();
                        apellidos = lblApellidos.getText().toString();
                        email = lblEmail.getText().toString();
                        telefono = Integer.parseInt(String.valueOf(lblTelefono.getText()));
                        Usuario usuario = new Usuario(user, password, nombre, apellidos, email, telefono);
                        Usuario[] compruebaUser = listar();
                        if (compruebaUser != null){
                            for (int i=0; i<compruebaUser.length; i++){
                                if (compruebaUser[i].getUsuario().equals(usuario.getUsuario())){
                                    existe = true;
                                }
                            }
                            if (existe){
                                showToast("Ya existe el usuario!");
                            }else{
                                insertarDatos(usuario);
                                Bundle miBundle = new Bundle();
                                miBundle.putSerializable("USER", usuario);
                                Intent miIntent = new Intent(NuevoUsuario.this, PantallaPrincipal.class);

                                miIntent.putExtras(miBundle);
                                startActivity(miIntent);
                            }
                        }else{
                            showToast("No ha podido abrirse la Base de Datos");
                        }


                }
            }
        });
    }


    public int comprobar(String user, String password, String nombre, String apellidos, String email, String telefono) {
        if (user.equals("")) {
            showToast("El usuario no puede estar vacío");
            return 0;
        } else if (password.equals("")){
            showToast("La contraseña no puede estar vacía");
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
                    datos[i] = datosAInsertar;*/
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

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}

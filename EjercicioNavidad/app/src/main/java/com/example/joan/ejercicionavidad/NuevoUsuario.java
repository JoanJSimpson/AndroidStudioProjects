package com.example.joan.ejercicionavidad;

import android.app.Activity;
import android.content.ContentValues;
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
    private String user, nombre, apellidos, email;
    private int telefono;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevousuario);


        final EditText lblUser = (EditText) findViewById(R.id.nuUser);
        final EditText lblNombre = (EditText) findViewById(R.id.nuNombre);
        final EditText lblApellidos = (EditText) findViewById(R.id.nuapellidos);
        final EditText lblTelefono = (EditText) findViewById(R.id.nutelefono);
        final EditText lblEmail = (EditText) findViewById(R.id.nuemail);

        final Button botonValidar = (Button) findViewById(R.id.nuBotonValidar);

        botonValidar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int comprueba = comprobar(String.valueOf(lblUser.getText()), String.valueOf(lblNombre.getText()), String.valueOf(lblApellidos.getText()),
                        String.valueOf(lblEmail.getText()), String.valueOf(lblTelefono.getText()));
                switch (comprueba) {
                    case 0:
                        break;
                    case 1:
                        user = lblUser.getText().toString();
                        nombre = String.valueOf(lblNombre.getText());
                        apellidos = String.valueOf(lblApellidos.getText());
                        email = String.valueOf(lblEmail.getText());
                        telefono = Integer.parseInt(String.valueOf(lblTelefono.getText()));
                        Usuario usuario = new Usuario(1, user, nombre, apellidos, email, telefono);
                        insertarDatos(usuario);

                }
            }
        });
    }


    public int comprobar(String user, String nombre, String apellidos, String email, String telefono) {
        if (user.equals("")) {
            showToast("El usuario no puede estar vacío");
            return 0;
        } else if (nombre.equals("")) {
            showToast("El nombre no pueden estar vacíos");
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
        contentValues.put("nombre", usuario.getNombre());
        contentValues.put("apellidos", usuario.getApellidos());
        contentValues.put("email", usuario.getEmail());
        contentValues.put("telefono", usuario.getTelefono());

        //TODO intentar comprobar que se inserta correctamente

        bd.insert("Usuarios", null, contentValues);

        bd.close();

        showToast("Usuario guardado correctamente");
    }

    public void listar() {

        SQLiteHelper sqliteHelper = new SQLiteHelper(this, "DBClientes.sqlite", null, 1);
        SQLiteDatabase bd = sqliteHelper.getReadableDatabase();

        if (bd != null) {
            Cursor cursor = bd.rawQuery("SELECT * FROM Usuarios", null);
            int cantidad = cursor.getCount();
            int i = 0;
            //String[] clientes = new String[cantidad];
            String compruebaUsuario;
            //datos = new DatosClientes[cantidad];

            if (cursor.moveToFirst()) {
                do {
                    /*DatosClientes datosAInsertar = new DatosClientes(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6),
                            cursor.getDouble(7), cursor.getString(8), cursor.getDouble(9), cursor.getInt(10));
                    datos[i] = datosAInsertar;*/
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

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}

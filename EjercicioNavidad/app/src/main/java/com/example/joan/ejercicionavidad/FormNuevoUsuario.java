package com.example.joan.ejercicionavidad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by Joan on 18/1/16.
 */
public class FormNuevoUsuario extends AppCompatActivity{
    private String user, nombre, apellidos, email, password, dni, telefono_comprobar;
    private int telefono;
    public static int COD_RESPUESTA=1;

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
                                miBundle.putSerializable("USERFORM", usuario);
                                Intent miIntent = new Intent(FormNuevoUsuario.this, FormPedido.class);

                                miIntent.putExtras(miBundle);
                                startActivityForResult(miIntent, COD_RESPUESTA);
                                //startActivity(miIntent);
                            }

                        }else {
                            ClaseUsuario usuario = new ClaseUsuario(Integer.parseInt(telefono_comprobar), user, password, dni, nombre, apellidos, email);
                            sql.crearUsuario(usuario);
                            Bundle miBundle = new Bundle();
                            miBundle.putSerializable("USERFORM", usuario);
                            Intent miIntent = new Intent(FormNuevoUsuario.this, FormPedido.class);

                            miIntent.putExtras(miBundle);
                            miIntent.putExtra("COD", COD_RESPUESTA);
                            startActivityForResult(miIntent, COD_RESPUESTA);
                            //startActivity(miIntent);
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
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}
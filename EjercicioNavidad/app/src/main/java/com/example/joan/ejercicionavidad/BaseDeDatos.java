package com.example.joan.ejercicionavidad;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Joan on 15/1/16.
 */
public class BaseDeDatos extends AppCompatActivity {
    String nombre, apellidos, email, caja, tarifa, zonaSeleccionada;
    Double precioTarifa, precioZona, precioPeso, peso, precioTotal;
    int telefono, imagen;
    Zonas zona;
    Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basededatos);

        final Button botonValidar = (Button) findViewById(R.id.botonValidar);
        final Button botonVer = (Button) findViewById(R.id.botonVer);

        final EditText lblNombre = (EditText) findViewById(R.id.nombre);
        final EditText lblApellidos = (EditText) findViewById(R.id.apellidos);
        final EditText lblTelefono = (EditText) findViewById(R.id.telefono);
        final EditText lblEmail = (EditText) findViewById(R.id.email);

        Bundle bundle = getIntent().getExtras();


        user = (Usuario) bundle.getSerializable("USER2");
        lblNombre.setText(user.getNombre());
        lblApellidos.setText(user.getApellidos());
        lblTelefono.setText(String.valueOf(user.getTelefono()));
        lblEmail.setText(user.getEmail());
        //showToast(user.toString());


        zona = (Zonas) bundle.getSerializable("ZONA");

        precioTarifa = zona.getPrecio();
        imagen = zona.getImagen();
        zonaSeleccionada = zona.getZona();

        tarifa = bundle.getString("TARIFA");
        precioZona = bundle.getDouble("PRECIOZONA");
        precioPeso = bundle.getDouble("PRECIOPESO");
        peso = bundle.getDouble("PESO");
        precioTotal = bundle.getDouble("PRECIOTOTAL");
        caja = bundle.getString("CAJA");
        Double precioTotalSinUrgente = precioTarifa + (peso * precioPeso);


        botonValidar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /*int comprueba = comprobar(String.valueOf(lblNombre.getText()), String.valueOf(lblApellidos.getText()),
                        String.valueOf(lblEmail.getText()), String.valueOf(lblTelefono.getText()));
                switch (comprueba) {
                    case 0:
                        break;
                    case 1:
                        nombre = String.valueOf(lblNombre.getText());
                        apellidos = String.valueOf(lblApellidos.getText());
                        email = String.valueOf(lblEmail.getText());
                        telefono = Integer.parseInt(String.valueOf(lblTelefono.getText()));
                        insertarDatos();

                }*/
                nombre = user.getNombre();
                apellidos = user.getApellidos();
                email = user.getEmail();
                telefono = user.getTelefono();
                insertarDatos();

            }
        });


        botonVer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent miIntent = new Intent(BaseDeDatos.this, ListaVista.class);
                startActivity(miIntent);

            }

        });

    }//fin onCreate

    public int comprobar(String nombre, String apellidos, String email, String telefono) {
        if (nombre.equals("")) {
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

    public void insertarDatos() {

        SQLiteHelper admin = new SQLiteHelper(this, "DBClientes.sqlite", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("nombre", nombre);
        contentValues.put("apellidos", apellidos);
        contentValues.put("email", email);
        contentValues.put("telefono", telefono);
        contentValues.put("imagen", imagen);
        contentValues.put("zona", zonaSeleccionada);
        contentValues.put("tarifa", tarifa);
        contentValues.put("peso", peso);
        contentValues.put("decoracion", caja);
        contentValues.put("coste", precioTotal);

        //TODO intentar comprobar que se inserta correctamente

        bd.insert("Clientes", null, contentValues);

        bd.close();

        showToast("Cliente guardado correctamente");
    }


    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}

package com.example.joan.examenjoan;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

/**
 * Created by Joan on 15/1/16.
 */
public class BaseDeDatos extends Activity {
    String nombre, apellidos, email, caja, tarifa, zonaSeleccionada;
    Double precioTarifa, precioZona, precioPeso, peso, precioTotal;
    int telefono, imagen;
    boolean pulsado = false;
    Zonas zona;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basededatos);

        final Button botonValidar= (Button) findViewById(R.id.botonValidar);
        final Button botonAceptar = (Button) findViewById(R.id.botonSiguiente);
        final Button botonVer = (Button) findViewById(R.id.botonVer);

        final EditText lblNombre = (EditText) findViewById(R.id.nombre);
        final EditText lblApellidos = (EditText) findViewById(R.id.apellidos);
        final EditText lblTelefono = (EditText) findViewById(R.id.telefono);
        final EditText lblEmail = (EditText) findViewById(R.id.email);

        Bundle  bundle = getIntent().getExtras();


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
        Double precioTotalSinUrgente = precioTarifa+(peso*precioPeso);



        botonValidar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                nombre = String.valueOf(lblNombre.getText());
                apellidos = String.valueOf(lblApellidos.getText());
                email = String.valueOf(lblEmail.getText());
                telefono = Integer.parseInt(String.valueOf(lblTelefono.getText()));

                String todos="Nombre: "+nombre+". Apellidos: "+apellidos+". Email: "+email+". Telefono: "+telefono+"" +
                        ". Zona: "+zonaSeleccionada+". Tarifa: "+tarifa+". Peso: "+peso+". Decoración: "+caja+". Precio Total: "+precioTotal;

                showToast(todos);
                insertarDatos();
                pulsado();
            }
        });

        botonAceptar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (pulsado == true) {
                    Intent miIntent = new Intent(BaseDeDatos.this, ListaVista.class);
                    startActivity(miIntent);
                }else{
                    showToast("Tienes que añadir primero al usuario");
                }
            }

        });

        botonVer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    Intent miIntent = new Intent(BaseDeDatos.this, ListaVista.class);
                    startActivity(miIntent);

            }

        });

    }//fin onCreate

    public void pulsado(){
        pulsado = true;
    }

    public void insertarDatos(){

        SQLiteHelper admin = new SQLiteHelper(this, "DBClientes.sqlite", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("nombre"    , nombre);
        contentValues.put("apellidos"    , apellidos);
        contentValues.put("email" , email);
        contentValues.put("telefono"  , telefono);
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



    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}

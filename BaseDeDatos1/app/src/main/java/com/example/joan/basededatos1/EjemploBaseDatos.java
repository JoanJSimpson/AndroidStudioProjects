package com.example.joan.basededatos1;

/**
 * Created by Joan on 11/1/16.
 */

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Spinner;


public class EjemploBaseDatos extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		Spinner miSpinner;


        //Abrimos la base de datos en modo escritura
        SQLiteHelper cliBDh = new SQLiteHelper(this, "DBClientes", null, 1);

        //Obtenemos referencia a la base de datos para poder modificarla.
        SQLiteDatabase bd = cliBDh.getWritableDatabase();

        //En caso de abrir de forma correcta la base de datos
        if (bd!=null) {
            //Introducimos 3 clientes de ejemplo
            for (int cont=1; cont<=3; cont++) {
                //Creamos los datos
                int codigo = cont;
                String nombre = "Cliente" + cont;
                String telefono = cont + "2345678";

                //Introducimos los datos en la tabla Clientes
                bd.execSQL("INSERT INTO Clientes (codigo, nombre, telefono) " +
                        "VALUES (" + codigo + ", '" + nombre + "', '" + telefono + "')");
            }

        /*		//Insertar un registro
        		bd.execSQL("INSERT INTO Clientes (nombre, telefono) VALUES ('cli1','11111') ");
        		//Actualizar un registro
        		bd.execSQL("UPDATE Clientes SET telefono='00000' WHERE nombre='cli1' ");
        		//Eliminar un registro
        //		bd.execSQL("DELETE FROM Clientes WHERE nombre='cli1' ");

        		//Ejemplo de utilización del método insert()
        		//Creamos el registro que queremos insertar utilizando un objeto ContentValues
        		ContentValues nuevoRegistro = new ContentValues();
        		nuevoRegistro.put("nombre","cli10");
        		nuevoRegistro.put("telefono", "101010");
        		//Insertamos el registro en la base de datos
        		//El primer parámetro es el nombre de la tabla donde insertaremos.
        		//El segundo parámetro solo sirve en caso de introducir un registro vacio
        		//El tercer paráemtro es el objeto ContentValues que contiene el registro a insertar
        		bd.insert("Clientes", null, nuevoRegistro);

       		//Ejemplo de utilización del método update()
        		//Establecemos los campos-valores que vamos a actualizar
        		ContentValues valores = new ContentValues();
        		valores.put("telefono", "101010XX");
        		//Actualizamos el registro en la base de datos
        		//El tercer argumento es la condición del UPDATE tal como lo haríamos en la cláusula
        		//WHERE en una sentencia SQL normal.
        		//El cuarto argumento son partes variables de la sentencia SQL que aportamos en un
        		//vector de valores aparte
        		String[] args = new String[]{"cli1", "cli2"};
        		bd.update("Clientes", valores, "nombre=? OR nombre=?", args);

        		//Ejemplo de utilización del método delete()
        		//Eliminamos el registro del cliente 'cli2'
        		String[] args2 = new String[]{"cli2"};
        		bd.delete("Clientes", "nombre=?", args2);

        		//Ejemplo Select
        		String[] args3 = new String[]{"cli1"};
        		Cursor c = bd.rawQuery("SELECT nombre,telefono FROM Clientes WHERE nombre=? ", args3);*/

        		//Ejemplo Select2
        		String[] campos = new String[] {"nombre", "telefono"};
        		String[] args4 = new String[] {"cli1"};
        		Cursor c = bd.query("Clientes", campos, null, null, null, null, null);
        		//Nos aseguramos de que exista al menos un registro
        		if (c.moveToFirst()) {
        			//Recorremos el cursor hasta que no haya más registros
        			do {
        				String nombreCli = c.getString(0);
        				String telefonoCli = c.getString(1);
						String datosCliente = nombreCli + "..." + telefonoCli;

						//Toast.makeText(this, datosCliente, Toast.LENGTH_SHORT).show();

						/*//SPINNER
						//Creamos el spiner
						miSpinner = (Spinner) findViewById(R.id.spinner);
						AdaptadorSpinner miAdaptador= new AdaptadorSpinner (this);
						miSpinner.setAdapter(miAdaptador);
						miSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
							public void onItemSelected(AdapterView arg0, View arg1, int position, long id) {

								Toast.makeText(this, datos, Toast.LENGTH_SHORT).show();

							}

							@Override
							public void onNothingSelected(AdapterView<?> adapterView) {
							}
						});//final miSpinner*/


					} while (c.moveToNext());
        		}


            //Cerramos la base de datos
            bd.close();
        } //del if
    }
	//Clase necesaria para el adaptador para Spinner
/*
	class AdaptadorSpinner extends ArrayAdapter<Clientes> {
		public Activity miActividad;

		public AdaptadorBebidas(Activity laActividad){
			super (laActividad, R.layout.desmilista, bebidas);
			this.miActividad = laActividad;

		}
		// Vista para el desplegable del spinner
		public View getDropDownView(int position, View convertView, ViewGroup parent){
			View ListaDesplegada = getView(position, convertView, parent);
			return ListaDesplegada;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = miActividad.getLayoutInflater();
			View item = inflater.inflate(R.layout.desmilista, null);

			TextView lblBebida = (TextView) item.findViewById(R.id.campoBebida);
			lblBebida.setText(bebidas[position].getBebida());

			TextView lblPrecio = (TextView) item.findViewById(R.id.campoPrecio);
			lblPrecio.setText(String.valueOf(bebidas[position].getPrecio())+" €");


			return (item);
		}
	}//Fin AdaptadorBebidas*/

}

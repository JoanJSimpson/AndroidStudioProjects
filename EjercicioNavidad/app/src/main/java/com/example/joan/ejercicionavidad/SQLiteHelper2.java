package com.example.joan.ejercicionavidad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joan on 18/1/16.
 */

public class SQLiteHelper2 extends SQLiteOpenHelper {

    // Table Names
    private static final String TABLA_USUARIOS = "usuarios";
    private static final String TABLA_PEDIDOS = "envios";
    private static final String TABLA_HISTORIAL = "historial";



    // usuarios - columnas

    private static final String USUARIO_LOGIN = "user";
    private static final String USUARIO_PASSWORD = "password";
    private static final String USUARIO_DNI = "dni";
    private static final String USUARIO_NOMBRE = "nombre";
    private static final String USUARIO_APELLIDOS = "apellidos";
    private static final String USUARIO_EMAIL = "email";
    private static final String USUARIO_TELEFONO = "telefono";

    // pedidos - columnas

    private static final String PEDIDO_USUARIODNI = "usuarioDNI";
    private static final String PEDIDO_ZONAID = "zonaId";
    private static final String PEDIDO_TARIFA = "tarifa";
    private static final String PEDIDO_PESO = "peso";
    private static final String PEDIDO_PRECIO = "precio";
    private static final String PEDIDO_DECORACION = "decoracion";
    private static final String PEDIDO_IMAGEN = "imagen";

    // historial - columnas

    private static final String HISTORIAL_NOMBRE = "nombre";
    private static final String HISTORIAL_USUARIODNI = "usuarioDNI";
    private static final String HISTORIAL_ZONAID = "zonaId";
    private static final String HISTORIAL_TARIFA = "tarifa";
    private static final String HISTORIAL_PESO = "peso";
    private static final String HISTORIAL_PRECIO = "precio";
    private static final String HISTORIAL_DECORACION = "decoracion";
    private static final String HISTORIAL_IMAGEN = "imagen";

    //Sentencia SQL para crear la tabla de Usuarios
    String sqlCreateUsuarios = "CREATE TABLE IF NOT EXISTS 'usuarios' (" +
            "  'user' TEXT NOT NULL UNIQUE," +
            "  'password' TEXT NOT NULL," +
            "  'dni' TEXT NOT NULL PRIMARY KEY," +
            "  'nombre' TEXT NOT NULL," +
            "  'apellidos' TEXT NOT NULL," +
            "  'email' TEXT NOT NULL," +
            "  'telefono' INTEGER NOT NULL" +
            "  );";

    //Sentencia SQL para crear la tabla de Envios
    String sqlCreatePedidos = "CREATE TABLE IF NOT EXISTS 'envios' (" +
            "  'id' INTEGER NOT NULL PRIMARY KEY," +
            "  'usuarioDNI' TEXT NOT NULL," +
            "  'zonaId' TEXT NOT NULL," +
            "  'tarifa' TEXT NOT NULL," +
            "  'peso' DOUBLE NOT NULL," +
            "  'decoracion' TEXT NOT NULL," +
            "  'precio' DOUBLE NOT NULL," +
            "  'imagen' INTEGER NOT NULL," +
            "   FOREIGN KEY('usuarioDNI') REFERENCES usuarios('dni') ON DELETE CASCADE" + 
            "  );";

    String sqlCreateHistorial = "CREATE TABLE IF NOT EXISTS 'historial' (" +
            "  'id' INTEGER NOT NULL PRIMARY KEY," +
            "  'nombre' TEXT NOT NULL," +
            "  'usuarioDNI' TEXT NOT NULL," +
            "  'zonaId' TEXT NOT NULL," +
            "  'tarifa' TEXT NOT NULL," +
            "  'peso' DOUBLE NOT NULL," +
            "  'decoracion' TEXT NOT NULL," +
            "  'precio' DOUBLE NOT NULL," +
            "  'imagen' INTEGER NOT NULL" +
            "  );";

    public SQLiteHelper2(Context contexto, String nombre, SQLiteDatabase.CursorFactory basededatos, int version){
        super(contexto, nombre, basededatos, version);
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sqlCreateUsuarios);
        db.execSQL(sqlCreatePedidos);
        db.execSQL(sqlCreateHistorial);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    /*
    * ==========================================================================
    * ==========================================================================
    *
    *                           USUARIO
    *
    * ==========================================================================
    * ==========================================================================
    * */

    /**
     *
     * @param usuario
     * @return
     *
     * Crear un usuario en la tabla Usuarios
     *
     */


    public void crearUsuario(ClaseUsuario usuario) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(USUARIO_CODIGO, usuario.getCodigo());
        values.put("user", usuario.getUser());
        values.put("password", usuario.getPassword());
        values.put("dni", usuario.getDni());
        values.put("nombre", usuario.getNombre());
        values.put("apellidos", usuario.getApellidos());
        values.put("email", usuario.getEmail());
        values.put("telefono", usuario.getTelefono());

        db.insert(TABLA_USUARIOS, null, values);

        // insert row
        //long usuario_id = db.insert(TABLA_USUARIOS, null, values);

        //return usuario_id;
    }

    /**
     *
     * @param usuario_dni
     * @return
     *
     * Obtener los datos de un usuario
     *
     */
    public ClaseUsuario getUsuario (String usuario_dni) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLA_USUARIOS + " WHERE " + USUARIO_DNI + " = '" + usuario_dni + "'";

        Cursor c = db.rawQuery(selectQuery, null);
        int cantidad = c.getCount();

        ClaseUsuario usuario = null;

        if (cantidad>0) {
            if (c != null) {
                c.moveToFirst();
                usuario = new ClaseUsuario();
                usuario.setUser((c.getString(c.getColumnIndex(USUARIO_LOGIN))));
                usuario.setPassword((c.getString(c.getColumnIndex(USUARIO_PASSWORD))));
                usuario.setNombre((c.getString(c.getColumnIndex(USUARIO_NOMBRE))));
                usuario.setDni(c.getString(c.getColumnIndex(USUARIO_DNI)));
                usuario.setApellidos(c.getString(c.getColumnIndex(USUARIO_APELLIDOS)));
                usuario.setEmail(c.getString(c.getColumnIndex(USUARIO_EMAIL)));
            }

        }
        return usuario;
    }


    /*
     * getting all usuarios
     * */
    public List<ClaseUsuario> getTodosUsuarios() {
        List<ClaseUsuario> usuarios = new ArrayList<ClaseUsuario>();
        String selectQuery = "SELECT  * FROM " + TABLA_USUARIOS;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ClaseUsuario td = new ClaseUsuario();

                td.setUser((c.getString(c.getColumnIndex(USUARIO_LOGIN))));
                td.setPassword((c.getString(c.getColumnIndex(USUARIO_PASSWORD))));
                td.setNombre((c.getString(c.getColumnIndex(USUARIO_NOMBRE))));
                td.setDni(c.getString(c.getColumnIndex(USUARIO_DNI)));
                td.setApellidos(c.getString(c.getColumnIndex(USUARIO_APELLIDOS)));
                td.setEmail(c.getString(c.getColumnIndex(USUARIO_EMAIL)));
                td.setTelefono(Integer.parseInt(c.getString(c.getColumnIndex(USUARIO_TELEFONO))));

                // adding to Usuario list
                usuarios.add(td);
            } while (c.moveToNext());
        }

        return usuarios;
    }

    /*
     * coger todos los logins de los usuarios
     * */
    public List<String> getLoginUsuarios() {
        List<String> usuarios = new ArrayList<String>();
        String selectQuery = "SELECT user FROM " + TABLA_USUARIOS;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                String td;

                td = c.getString(c.getColumnIndex(USUARIO_LOGIN));

                // adding to Usuario list
                usuarios.add(td);
            } while (c.moveToNext());
        }

        return usuarios;
    }


    /*
     * Updating a Usuario
     */
    public int updateUsuario(ClaseUsuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("user", usuario.getUser());
        values.put("dni", usuario.getDni());
        values.put("nombre", usuario.getNombre());
        values.put("apellidos", usuario.getApellidos());
        values.put("email", usuario.getEmail());
        values.put("telefono", usuario.getTelefono());

        // updating row
        return db.update(TABLA_USUARIOS, values, USUARIO_DNI + " = ?",
                new String[] { String.valueOf(usuario.getDni()) });
    }

    /*
     * Deleting a Usuario
     */
    public void deleteToDo(String usuario_dni) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLA_USUARIOS, USUARIO_DNI + " = ?",
                new String[] { String.valueOf(usuario_dni) });
    }



    /*
    * ==========================================================================
    * ==========================================================================
    *
    *                           PEDIDOS
    *
    * ==========================================================================
    * ==========================================================================
    * */


    public void crearPedido(ClasePedido pedido) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PEDIDO_USUARIODNI, pedido.getUsuarioDni());
        values.put(PEDIDO_ZONAID, pedido.getZonaId());
        values.put(PEDIDO_TARIFA, pedido.getTarifa());
        values.put(PEDIDO_PESO, pedido.getPeso());
        values.put(PEDIDO_DECORACION, pedido.getDecoracion());
        values.put(PEDIDO_PRECIO, pedido.getPrecio());
        values.put(PEDIDO_IMAGEN, pedido.getImagen());

        // insert row
        db.insert(TABLA_PEDIDOS, null, values);
        //long usuario_id = db.insert(TABLA_PEDIDOS, null, values);

        //return usuario_id;
    }


    /*
     * getting all pedidos
     * */
    public List<ClasePedido> getAllPedidos() {
        List<ClasePedido> pedidos = new ArrayList<ClasePedido>();
        String selectQuery = "SELECT  * FROM " + TABLA_PEDIDOS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);



        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ClasePedido td = new ClasePedido();
                td.setUsuarioDni((c.getString(c.getColumnIndex("usuarioDNI"))));
                td.setZonaId(c.getString(c.getColumnIndex("zonaId")));
                td.setTarifa(c.getString(c.getColumnIndex("tarifa")));
                td.setPeso(Double.parseDouble(c.getString(c.getColumnIndex("peso"))));
                td.setDecoracion(c.getString(c.getColumnIndex("decoracion")));
                td.setPrecio(Double.parseDouble(c.getString(c.getColumnIndex("precio"))));
                td.setImagen(Integer.parseInt(c.getString(c.getColumnIndex("imagen"))));

                // adding to Usuario list
                pedidos.add(td);
            } while (c.moveToNext());
        }

        return pedidos;
    }


    /*
     * getting all pedidos usuario
     * */
    public ClasePedido[] getAllPedidosUsuario(String dniUsuario) {
        ClasePedido[] pedidos;
        //String selectQuery = "SELECT  * FROM " + TABLA_PEDIDOS + " WHERE usuarioDNI LIKE '" + dniUsuario + "'";
        String selectQuery = "SELECT * " +
                "FROM "+TABLA_PEDIDOS+" p, "+TABLA_USUARIOS+" u " +
                "WHERE u."+USUARIO_DNI+" = p."+PEDIDO_USUARIODNI+" AND u."+USUARIO_DNI+" = '" + dniUsuario + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);


        int tamano = c.getCount();
        pedidos = new ClasePedido[tamano];

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            //for (int i=0;i<tamano;i++) {
            int i=0;
                do {


                    ClasePedido td = new ClasePedido();
                    td.setId((c.getInt(c.getColumnIndex("id"))));
                    td.setUsuarioDni((c.getString(c.getColumnIndex("usuarioDNI"))));
                    td.setZonaId(c.getString(c.getColumnIndex("zonaId")));
                    td.setTarifa(c.getString(c.getColumnIndex("tarifa")));
                    td.setPeso(Double.parseDouble(c.getString(c.getColumnIndex("peso"))));
                    td.setDecoracion(c.getString(c.getColumnIndex("decoracion")));
                    td.setPrecio(Double.parseDouble(c.getString(c.getColumnIndex("precio"))));
                    td.setImagen(Integer.parseInt(c.getString(c.getColumnIndex("imagen"))));

                    // adding to Usuario list
                    pedidos[i] = td;
                    i++;

                } while (c.moveToNext());
            //}
        }

        return pedidos;
    }



    /*
    * ==========================================================================
    * ==========================================================================
    *
    *                           HISTORIAL
    *
    * ==========================================================================
    * ==========================================================================
    * */


    public void crearHistorial(ClaseHistorial historial) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HISTORIAL_NOMBRE, historial.getNombre());
        values.put(HISTORIAL_USUARIODNI, historial.getUsuarioDni());
        values.put(HISTORIAL_ZONAID, historial.getZonaId());
        values.put(HISTORIAL_TARIFA, historial.getTarifa());
        values.put(HISTORIAL_PESO, historial.getPeso());
        values.put(HISTORIAL_DECORACION, historial.getDecoracion());
        values.put(HISTORIAL_PRECIO, historial.getPrecio());
        values.put(HISTORIAL_IMAGEN, historial.getImagen());

        // insert row
        db.insert(TABLA_HISTORIAL, null, values);
        //long usuario_id = db.insert(TABLA_PEDIDOS, null, values);

        //return usuario_id;
    }


    /*
     * getting all pedidos
     * */
    public List<ClaseHistorial> getAllHistorial() {
        List<ClaseHistorial> pedidos = new ArrayList<ClaseHistorial>();
        String selectQuery = "SELECT  * FROM " + TABLA_PEDIDOS;
        // String selectQuery = "SELECT * " +
        //"FROM "+TABLA_PEDIDOS+" p, "+TABLA_USUARIOS+" u " +
        //        "WHERE u."+USUARIO_DNI+" = p."+PEDIDO_USUARIODNI+" AND u."+USUARIO_DNI+" = '" + dniUsuario + "'";

        /*
        select *    from ALUMNOS_CURSOS AC right join CURSOS C
     on AC.ID_CURSO = C.ID_CURSO
group by C.TITULO
         */

        /*
        String selectQuery = "SELECT * " +
                "FROM "+TABLA_PEDIDOS+" p, "+TABLA_USUARIOS+" u " +
                "WHERE u."+USUARIO_DNI+" = p."+PEDIDO_USUARIODNI+" AND u."+USUARIO_DNI+" = '" + dniUsuario + "'";
         */

        String selectQuery2 = "SELECT * FROM "+TABLA_PEDIDOS+" p, "+TABLA_USUARIOS+" u "+
                "WHERE u."+USUARIO_DNI+" = p."+PEDIDO_USUARIODNI+ " ORDER BY u."+USUARIO_DNI;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery2, null);



        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ClaseHistorial td = new ClaseHistorial();

                td.setUsuarioDni((c.getString(c.getColumnIndex("usuarioDNI"))));
                td.setNombre(c.getString(c.getColumnIndex("nombre")));
                td.setZonaId(c.getString(c.getColumnIndex("zonaId")));
                td.setTarifa(c.getString(c.getColumnIndex("tarifa")));
                td.setPeso(Double.parseDouble(c.getString(c.getColumnIndex("peso"))));
                td.setDecoracion(c.getString(c.getColumnIndex("decoracion")));
                td.setPrecio(Double.parseDouble(c.getString(c.getColumnIndex("precio"))));
                td.setImagen(Integer.parseInt(c.getString(c.getColumnIndex("imagen"))));

                // adding to Usuario list
                pedidos.add(td);
            } while (c.moveToNext());
        }

        return pedidos;
    }


}
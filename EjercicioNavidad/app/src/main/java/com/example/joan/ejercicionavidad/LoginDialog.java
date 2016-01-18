package com.example.joan.ejercicionavidad;

/**
 * Created by Joan on 17/1/16.
 */
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Fragmento con un diálogo personalizado
 */
public class LoginDialog extends DialogFragment {
    private static final String TAG = LoginDialog.class.getSimpleName();

    public LoginDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createLoginDialogo();
    }

    /**
     * Crea un diálogo con personalizado para comportarse
     * como formulario de login
     *
     * @return Diálogo
     */
    public AlertDialog createLoginDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_usuario, null);

        builder.setView(v);

        Button signup = (Button) v.findViewById(R.id.crear_boton);
        Button signin = (Button) v.findViewById(R.id.entrar_boton);
        final EditText user = (EditText) v.findViewById(R.id.nombre_input);
        final EditText contrasena = (EditText) v.findViewById(R.id.contrasena_input);

        signup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //showToast("Pulsado boton Crear usuario");
                        crear();
                        // Crear Cuenta...
                        dismiss();
                    }
                }
        );

        signin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //showToast("Pulsado boton entrar usuario");
                        // Loguear...
                        String usuario = user.getText().toString();
                        String password = contrasena.getText().toString();
                        login(usuario, password);
                        //dismiss();
                    }
                }

        );

        return builder.create();


    }
    public void crear(){
        Intent miIntent = new Intent(getActivity(), FormNuevoUsuario.class);
        startActivity(miIntent);

    }

    public void login(String usuario, String password){

        List<ClaseUsuario> user = compruebaUser();
        Boolean existe = false;
        int j=0;
        for (int i = 0; i< user.size(); i++){
            if (user.get(i).getUser().equals(usuario) && user.get(i).getPassword().equals(password)){
                //if (user[i].getUsuario().equals(usuario)){
                existe = true;
                j=i;
            }
        }
        if (existe){
            Intent miIntent = new Intent(this.getContext(), FormPedido.class);
            Bundle miBundle = new Bundle();
            miBundle.putSerializable("USERLOGIN", user.get(j));
            miIntent.putExtras(miBundle);

            startActivity(miIntent);
            dismiss();
        } else{
            showToast("Error en el User o Contraseña");
        }

    }

    public List<ClaseUsuario> compruebaUser(){

        SQLiteHelper2 sql = new SQLiteHelper2(this.getContext(), "DBClientes.sqlite", null, 1);
        List<ClaseUsuario> usuarios = new ArrayList<ClaseUsuario>();
        usuarios = sql.getTodosUsuarios();
        return usuarios;
    }

/*
    public void login(String usuario, String password){

        Usuario[] user = compruebaUser();
        Boolean existe = false;
        int j=0;
        for (int i = 0; i< user.length; i++){
            if (user[i].getUsuario().equals(usuario) && user[i].getPassword().equals(password)){
            //if (user[i].getUsuario().equals(usuario)){
                existe = true;
                j=i;
            }
        }
        if (existe){
            Intent miIntent = new Intent(this.getContext(), FormPedido.class);
            Bundle miBundle = new Bundle();
            miBundle.putSerializable("USER", user[j]);
            miIntent.putExtras(miBundle);

            startActivity(miIntent);
            dismiss();
        } else{
            showToast("Error en el User o Contraseña");
        }

    }

    public Usuario[] compruebaUser(){

        SQLiteHelper sqliteHelper = new SQLiteHelper(this.getContext(), "DBClientes.sqlite", null, 1);
        SQLiteDatabase bd = sqliteHelper.getReadableDatabase();
        Usuario usuario[]=null;

        if (bd != null) {
            Cursor cursor = bd.rawQuery("SELECT * FROM Usuarios", null);
            int cantidad = cursor.getCount();
            int i = 0;
            usuario = new Usuario[cantidad];

            if (cursor.moveToFirst()) {
                do {
                    usuario[i] = new Usuario(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5));

                    i++;
                } while (cursor.moveToNext());
            }


            cursor.close();
            bd.close();

        }
        return usuario;
    }
*/

    public void showToast(String text){Toast.makeText(this.getContext(), text, Toast.LENGTH_SHORT).show();
    }




}

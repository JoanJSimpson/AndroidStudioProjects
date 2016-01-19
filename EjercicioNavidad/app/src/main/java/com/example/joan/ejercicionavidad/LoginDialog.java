package com.example.joan.ejercicionavidad;

/**
 * Created by Joan on 17/1/16.
 */
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Fragmento con un diálogo personalizado
 */
public class LoginDialog extends DialogFragment {
    private static final String TAG = LoginDialog.class.getSimpleName();
    List<String> usuarios;
    String usuarioSeleccionado;


    public static int COD_RESPUESTA;
    private static int COD_SPINNER = 0;
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
        Spinner miSpinner = (Spinner) v.findViewById(R.id.spinnerUsuarios);

        //Datos para rellenar el spinner con los usuarios
        SQLiteHelper2 sql = new SQLiteHelper2(this.getContext(), "DBClientes.sqlite", null, 1);
        usuarios = sql.getLoginUsuarios();


        AdaptadorUsuarios miAdaptador= new AdaptadorUsuarios(this.getActivity());
        miSpinner.setAdapter(miAdaptador);
        miSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView arg0, View arg1, int position, long id) {
                usuarioSeleccionado = usuarios.get(position);
                user.setText(usuarioSeleccionado);

                /*if(COD_SPINNER==0) {
                    usuarios.add(0, "Seleccione un Usuario");
                    user.setHint(R.string.nombre_input);
                }else if(COD_SPINNER==1){
                    user.setHint(R.string.nombre_input);
                }else if(COD_SPINNER==2){
                    usuarios.remove(0);
                }else{
                    user.setText(usuarioSeleccionado);
                }

                showToast(arg0.getItemAtPosition(position).toString());
                showToast(String.valueOf(COD_SPINNER));
                COD_SPINNER++;
                */

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });//final miSpinner


        signup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Crear Cuenta...
                        crear();
                        dismiss();
                    }
                }
        );

        signin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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

                existe = true;
                j=i;
            }
        }
        if (existe){
            COD_RESPUESTA=0;
            Intent miIntent = new Intent(this.getContext(), FormPedido.class);
            Bundle miBundle = new Bundle();
            miBundle.putSerializable("USERLOGIN", user.get(j));
            miIntent.putExtras(miBundle);
            miIntent.putExtra("COD", COD_RESPUESTA);

            startActivityForResult(miIntent, COD_RESPUESTA);
            //startActivity(miIntent);
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


    public void showToast(String text){Toast.makeText(this.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    class AdaptadorUsuarios extends ArrayAdapter<String> {
        public Activity miActividad;

        public AdaptadorUsuarios(Activity laActividad){
            super (laActividad, R.layout.spinnerusuarios, usuarios);
            this.miActividad = laActividad;

        }
        // Vista para el desplegable del spinner
        public View getDropDownView(int position, View convertView, ViewGroup parent){
            View ListaDesplegada = getView(position, convertView, parent);
            return ListaDesplegada;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = miActividad.getLayoutInflater();
            View item = inflater.inflate(R.layout.spinnerusuarios, null);

            TextView nombre= (TextView) item.findViewById(R.id.spinnerUsuarioNombre);
            nombre.setText(usuarios.get(position));

            return (item);
        }
    }//Fin AdaptadorZonas




}

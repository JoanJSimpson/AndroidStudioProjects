package com.example.joan.ejercicionavidad;

/**
 * Created by Joan on 17/1/16.
 */
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    //====================================================================================
    //                  Variables de clase
    //====================================================================================

    private static final String TAG = LoginDialog.class.getSimpleName();
    List<String> usuarios;
    String usuarioSeleccionado;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String usuarioResume;
    String passwordResume;

    public LoginDialog() {
    }

    //====================================================================================
    //                  ONCREATE
    //====================================================================================
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        preferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = preferences.edit();
        //SharedPreferences.Editor editor = preferences.edit();
        usuarioResume = preferences.getString("user", "vacio");
        passwordResume = preferences.getString("password", "vacio");
        return createLoginDialogo();
    }

    /**
     * Crea un diálogo con personalizado para comportarse
     * como formulario de login
     *
     * @return Diálogo
     */

    public AlertDialog createLoginDialogo() {


        if (!usuarioResume.equals("vacio") && !passwordResume.equals("vacio")) {
            login(usuarioResume, passwordResume);
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_usuario, null);
        builder.setView(v);

        Button signup = (Button) v.findViewById(R.id.crear_boton);
        final Button signin = (Button) v.findViewById(R.id.entrar_boton);
        final Button crearUsers = (Button) v.findViewById(R.id.crearUsers_boton);
        final EditText user = (EditText) v.findViewById(R.id.nombre_input);
        final EditText contrasena = (EditText) v.findViewById(R.id.contrasena_input);
        final Spinner miSpinner = (Spinner) v.findViewById(R.id.spinnerUsuarios);
        final TextView info = (TextView) v.findViewById(R.id.info_spinner);
        crearUsers.setVisibility(View.INVISIBLE);
        signin.setVisibility(View.INVISIBLE);
        info.setVisibility(View.INVISIBLE);
        miSpinner.setVisibility(View.INVISIBLE);

        //Datos para rellenar el spinner con los usuarios
        try {
            //====================================================================================
            //                  SPINNER
            //====================================================================================
            SQLiteHelper2 sql = new SQLiteHelper2(this.getContext(), "DBClientes.sqlite", null, 1);
            usuarios = sql.getLoginUsuarios();
            if (usuarios.size()>0) {
                AdaptadorUsuarios miAdaptador = new AdaptadorUsuarios(this.getActivity());
                miSpinner.setAdapter(miAdaptador);
                miSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView arg0, View arg1, int position, long id) {
                        usuarioSeleccionado = usuarios.get(position);
                        user.setText(usuarioSeleccionado);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });//final miSpinner
                info.setVisibility(View.VISIBLE);
                miSpinner.setVisibility(View.VISIBLE);
                signin.setVisibility(View.VISIBLE);
            }
            else{
                crearUsers.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            usuarios =null;
        }


        //====================================================================================
        //                  EVENTOS DE LOS BOTONES
        //====================================================================================
        signup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Crear Cuenta...
                        crear();
                        getActivity().finish();
                        //dismiss();
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
                        //getActivity().finish();
                        //dismiss();
                    }
                }

        );

        crearUsers.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            SQLiteHelper2 sql = new SQLiteHelper2(getContext(), "DBClientes.sqlite", null, 1);
                            sql.crearUsuariosDemo();
                            sql.close();
                            showToast("Base de datos Demo creada correctamente");
                            usuarios = sql.getLoginUsuarios();
                            AdaptadorUsuarios miAdaptador = new AdaptadorUsuarios(getActivity());
                            miSpinner.setAdapter(miAdaptador);
                            miSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(AdapterView arg0, View arg1, int position, long id) {
                                    usuarioSeleccionado = usuarios.get(position);
                                    user.setText(usuarioSeleccionado);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });//final miSpinner
                            miSpinner.refreshDrawableState();
                            miSpinner.setVisibility(View.VISIBLE);
                            info.setVisibility(View.VISIBLE);
                            crearUsers.setVisibility(View.INVISIBLE);
                            signin.setVisibility(View.VISIBLE);



                        }catch (Exception e){
                            showToast("Error al crear BD Demo: "+e.toString());
                        }

                    }
                }

        );

        return builder.create();

    }

    //====================================================================================
    //                  METODOS
    //====================================================================================


    public void crear(){
        Intent miIntent = new Intent(getActivity(), FormNuevoUsuario.class);
        startActivity(miIntent);

    }//fin metodo crear()

    public void login(String usuario, String password){

        //creo la clase SuperUsuario con una vista de todos los usuarios
        //si es SuperUser
        if (usuario.equals("admin") && password.equals("admin")){

            editor.putString("user", usuario);
            editor.putString("password", password);
            editor.commit();
            Intent miIntent = new Intent(this.getContext(), SuperUsuario.class);
            startActivity(miIntent);

        //si no es el SuperUser comprueba que el usuario existe
        }else{

            List<ClaseUsuario> user = compruebaUser();
            Boolean existe = false;
            int j=0;
            //comprobamos que los datos del usuario introducido coincide con algun usuario ya registrado
            for (int i = 0; i< user.size(); i++){
                if (user.get(i).getUser().equals(usuario) && user.get(i).getPassword().equals(password)){
                    existe = true;
                    j=i;
                }
            }
            //si el usuario existe y las credenciales son correctas va directo a FormPedido
            if (existe){
                //COD_RESPUESTA=0;
                Intent miIntent = new Intent(this.getContext(), FormPedido.class);
                Bundle miBundle = new Bundle();
                miBundle.putSerializable("USER", user.get(j));
                //miBundle.putSerializable("USERLOGIN", user.get(j));
                miIntent.putExtras(miBundle);
                //miIntent.putExtra("COD", COD_RESPUESTA);
                editor.putString("user", usuario);
                editor.putString("password", password);
                editor.commit();

                //startActivityForResult(miIntent, COD_RESPUESTA);
                startActivity(miIntent);
                this.getActivity().finish();
                //dismiss();

            //si no son correctas salta un error
            } else{
                showToast("Error en el User o Contraseña");
            }

        }

    }//fin metodo login()

    public List<ClaseUsuario> compruebaUser(){

        SQLiteHelper2 sql = new SQLiteHelper2(this.getContext(), "DBClientes.sqlite", null, 1);
        List<ClaseUsuario> usuarios = new ArrayList<ClaseUsuario>();
        usuarios = sql.getTodosUsuarios();
        return usuarios;
    }//fin metodo compruebaUser()


    //====================================================================================
    //                 SHOWTOAST
    //====================================================================================


    public void showToast(String text){Toast.makeText(this.getContext(), text, Toast.LENGTH_SHORT).show();
    }


    //====================================================================================
    //                  ADAPTADOR SPINNER
    //====================================================================================


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

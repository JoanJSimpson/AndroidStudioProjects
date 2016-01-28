package com.example.joan.ejercicionavidad;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FormPedido extends AppCompatActivity {



    //====================================================================================
    //                  DATOS DE ZONAS POSIBLES
    //====================================================================================

    //metemos datos de Zonas en la clase
    private Zonas[] zonas = new Zonas[]{
            new Zonas("A", "Asia y Oceania", 30 , R.drawable.asia_oceania),
            new Zonas("B", "America y Africa", 20 , R.drawable.america_africa),
            new Zonas("C", "Europa", 10 , R.drawable.europa),
    }; //fin Zonas

    //====================================================================================
    //                  VARIABLES DE CLASE
    //====================================================================================

    public static int COD_RESPUESTA=0;
    Spinner miSpinner;
    String tarifaSeleccionada, cajaSeleccionada;
    Double precioTotal, precioCaja, precioTarifa;
    String marcados;
    CheckBox ch1;
    CheckBox ch2;
    ImageView miImagen;
    Button miBoton;
    RadioGroup rg;
    RadioButton r1;
    RadioButton r2;
    EditText peso;
    Double precioPeso;
    Zonas zona;
    ClaseUsuario user;
    SharedPreferences preferences;


//====================================================================================
//                  MENú
//====================================================================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Alternativa 1
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    //Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salir:
                salir();
                return true;
            case R.id.verBaseDatos:
                verBaseDatos();
                return true;
            case R.id.acercaDe:
                acercaDe();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


//====================================================================================
//                  Elementos del menú
//====================================================================================

    private void verBaseDatos(){
        Intent miIntent = new Intent(FormPedido.this, ViewLista.class);
        Bundle miBundle = new Bundle();
        miBundle.putSerializable("USER2", user);
        miIntent.putExtras(miBundle);

        startActivity(miIntent);

    }

    private void acercaDe(){
        new DialogoPersonalizado().show(getSupportFragmentManager(), "DialogoPersonalizado");
    }

    private void salir(){
        //Borramos las preferencias de login
        preferences.edit().clear().commit();
        Intent miIntent = new Intent(FormPedido.this, MainActivity.class);
        startActivity(miIntent);
        finish();
    }


//====================================================================================
//                  ON CREATE
//====================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String userP = preferences.getString("user", "vacio");
        String passP = preferences.getString("password", "vacio");
        setContentView(R.layout.pantallaprincipal);
        initialUISetup();
    }//Fin onCreate

    public void initialUISetup(){
        final TextView tit = (TextView) findViewById(R.id.TituloAgencia);
        precioCaja = 0.0;
        precioTotal=0.0;
        precioTarifa =0.0;
        ch1 = (CheckBox) findViewById(R.id.ch1);
        ch2 = (CheckBox) findViewById(R.id.ch2);
        miImagen = (ImageView) findViewById(R.id.imagen);
        miBoton = (Button) findViewById(R.id.miTotal);
        rg = (RadioGroup) findViewById(R.id.rg);
        r1= (RadioButton) findViewById(R.id.radioButton1);
        r2= (RadioButton) findViewById(R.id.radioButton2);
        peso = (EditText) findViewById(R.id.peso);
        Bundle recoger = getIntent().getExtras();
        /*int COD = recoger.getInt("COD");
        if (COD==0){
            user = (ClaseUsuario) recoger.getSerializable("USER");
        }else if(COD==1) {
            user = (ClaseUsuario) recoger.getSerializable("USER");
        }*/
        user = (ClaseUsuario) recoger.getSerializable("USER");
        //Creamos señal para el boton de siguiente pantalla
        //Boton para pasar a la siguiente pantalla
        miBoton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String parsearPeso = peso.getText().toString();
                Double pesoSeleccionado, pesoMult;
                if (parsearPeso.equals("")){
                    pesoSeleccionado = 0.0;
                    pesoMult=0.0;
                    precioPeso=0.0;
                }else{
                    pesoSeleccionado = Double.parseDouble(peso.getText().toString());
                    if (pesoSeleccionado<=5.0){
                        pesoMult=1.0;
                    }else if (pesoSeleccionado<=10.0){
                        pesoMult=1.5;
                    }else {
                        pesoMult = 2.0;
                    }
                    //precioTotal en funcion del peso seleccionado
                    precioPeso = pesoSeleccionado*pesoMult;
                }

                //String de los productos marcados
                marcados= getDecoracionClick(v);
                //precioTotal = (precioZona + precioCaja + (Peso*precio))+((precioZona + precioCaja + (Peso*precio))*precioTarifa)
                //precio total a pagar
                precioTotal= (precioCaja + zona.getPrecio() + precioPeso) + ((precioCaja + zona.getPrecio() + precioPeso)*precioTarifa);

                //Insertamos el pedido en la base de datos

                SQLiteHelper2 sql = new SQLiteHelper2( getApplicationContext(), "DBClientes.sqlite", null, 1);
                SQLiteDatabase db = sql.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("usuarioDni", user.getDni());
                values.put("zonaId", zona.getZona());
                values.put("tarifa", tarifaSeleccionada);
                values.put("peso", pesoSeleccionado);
                values.put("precio", precioTotal);
                values.put("imagen", zona.getImagen());
                values.put("decoracion", marcados);

                db.insert("envios", null, values);
                db.close();


                /*ClasePedido pedido = new ClasePedido(1, user.getDni(), zona.getZona(), tarifaSeleccionada, marcados, pesoSeleccionado,
                        precioTotal, zona.getImagen());*/
                Intent miIntent = new Intent(FormPedido.this, ViewLista.class);
                Bundle miBundle = new Bundle();
                miBundle.putSerializable("USER2", user);

                miIntent.putExtras(miBundle);
                startActivityForResult(miIntent, COD_RESPUESTA);
                //startActivity(miIntent);

            }
        });//miBoton


    //====================================================================================
    //                  SPINNER
    //====================================================================================
        //Creamos el spinner
        miSpinner = (Spinner) findViewById(R.id.spinner1);
        AdaptadorZonas miAdaptador= new AdaptadorZonas(this);
        miSpinner.setAdapter(miAdaptador);
        miSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView arg0, View arg1, int position, long id) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    miImagen.setBackground(getDrawable(zonas[position].getImagen()));
                } else {
                    miImagen.setBackgroundDrawable(getResources().getDrawable(zonas[position].getImagen()));
                }
                zona = zonas[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });//final miSpinner

    //====================================================================================
    //                  RADIOBUTTONS
    //====================================================================================
        //tratamos los radioButtons para ver cual esta pulsado
        rg.clearCheck();
        r1.setChecked(true);
        tarifaSeleccionada=String.valueOf(r1.getText());


        rg.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (r1.isChecked() == true) {
                            tarifaSeleccionada = String.valueOf(r1.getText());
                            precioTarifa = 0.0;

                        } else if (r2.isChecked() == true) {
                            tarifaSeleccionada = String.valueOf(r2.getText());
                            precioTarifa = (30.0/100.0);

                        }
                        showToast(tarifaSeleccionada);
                    }
                });

    }//final initialUISetup

//====================================================================================
//                  Tratar los CHECKS
//====================================================================================
// Tratar los checkeds seleccionados
    public String getDecoracionClick(View v){
        String strMessage = "";
        precioCaja =0.0;

        if(ch1.isChecked()){
            strMessage+=ch1.getText()+" ";
            precioCaja +=0;
        }
        if(ch2.isChecked()){
            strMessage+= ch2.getText();
            precioCaja +=0;
        }

        if(strMessage.length()==0){
            strMessage+="Sin Decoración";
        }
        return strMessage;
    }

//====================================================================================
//                  SHOWTOAST
//====================================================================================

    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


//====================================================================================
//                  ADAPTADOR SPINNER
//====================================================================================

    class AdaptadorZonas extends ArrayAdapter<Zonas> {
        public Activity miActividad;

        public AdaptadorZonas(Activity laActividad){
            super (laActividad, R.layout.desmilista, zonas);
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

            TextView lblZona = (TextView) item.findViewById(R.id.campoZona);
            lblZona.setText("ZONA "+zonas[position].getZona());

            TextView lblContinente = (TextView) item.findViewById(R.id.campoContinente);
            lblContinente.setText(zonas[position].getContinente());

            TextView lblPrecio = (TextView) item.findViewById(R.id.campoPrecio);
            lblPrecio.setText(String.valueOf(zonas[position].getPrecio())+" €");

            return (item);
        }
    }//Fin AdaptadorZonas

// ====================================================================================
//                  ONRESUME
//====================================================================================
    //para que cuando vuelva de la pantalla2 deje los precios a 0
    @Override
    protected void onResume() {
        super.onResume();
        precioTotal=0.0;
    }

}


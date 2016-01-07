package com.example.joan.posibleexamenjoan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    public static int COD_RESPUESTA=0;

    //metemos datos de bebidas en la clase
    private Bebidas[] bebidas = new Bebidas[]{
            new Bebidas("Cocacola", 2.5 , R.drawable.cocacola),
            new Bebidas("Fanta", 2.0 , R.drawable.fanta),
            new Bebidas("Whisky", 5.0 , R.drawable.whisky),
            new Bebidas("Ron", 4.5 , R.drawable.ron),
    }; //fin Bebidas

    //Creamos variables de clase

    Spinner miSpinner;
    String bebidaSeleccionada, vasoSeleccionado;
    Integer imagenSeleccionada;
    Double precioTotal, precioAperitivo, precioVaso, precioBebida;
    String marcados;
    CheckBox ch1;
    CheckBox ch2;
    CheckBox ch3;
    CheckBox ch4;
    ImageView miImagen;
    Button miBoton;
    RadioGroup rg;
    RadioButton r1;
    RadioButton r2;
    RadioButton r3;
    RadioButton r4;
    Bebidas bebidaPasar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialUISetup();
    }//Fin onCreate

    public void initialUISetup(){
        final TextView tit = (TextView) findViewById(R.id.TituloRestaurantePrincipal);
        precioAperitivo = 0.0;
        precioTotal=0.0;
        precioVaso=0.0;
        precioBebida=0.0;
        ch1 = (CheckBox) findViewById(R.id.ch1);
        ch2 = (CheckBox) findViewById(R.id.ch2);
        ch3 = (CheckBox) findViewById(R.id.ch3);
        ch4 = (CheckBox) findViewById(R.id.ch4);
        miImagen = (ImageView) findViewById(R.id.imagen);
        miBoton = (Button) findViewById(R.id.miTotal);
        rg = (RadioGroup) findViewById(R.id.gruporb);
        r1= (RadioButton) findViewById(R.id.radio1);
        r2= (RadioButton) findViewById(R.id.radio2);
        r3= (RadioButton) findViewById(R.id.radio3);
        r4= (RadioButton) findViewById(R.id.radio4);

        //Modificamos el tipo de fuente para que sea la de los simpson
        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/akbar.ttf");
        tit.setTypeface(face);

        //Creamos señal para el boton de siguiente pantalla
        //Boton para pasar a la siguiente pantalla
        miBoton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                marcados= getAperitivoClick(v);
                precioTotal= precioAperitivo +precioBebida+precioVaso;
                Intent miIntent = new Intent(MainActivity.this, Pantalla2.class);
                Bundle miBundle = new Bundle();
                miBundle.putSerializable("OBJETO", bebidaPasar);
                miBundle.putString("BEBIDA", bebidaSeleccionada);
                miBundle.putDouble("BEBIDAPRECIO", precioBebida);
                miBundle.putInt("IMAGEN", imagenSeleccionada);
                miBundle.putDouble("PRECIO", precioTotal);
                miBundle.putString("APERITIVOS", marcados);
                miBundle.putDouble("APERITIVOSPRECIO", precioAperitivo);
                miBundle.putString("VASO", vasoSeleccionado);
                miBundle.putDouble("VASOPRECIO",precioVaso);
                miIntent.putExtras(miBundle);
                startActivityForResult(miIntent, COD_RESPUESTA);
                //startActivity(miIntent);


            }
        });//miBoton


        //Creamos el spiner
        miSpinner = (Spinner) findViewById(R.id.spinner1);
        AdaptadorBebidas miAdaptador= new AdaptadorBebidas (this);
        miSpinner.setAdapter(miAdaptador);
        miSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView arg0, View arg1, int position, long id) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    miImagen.setBackground(getDrawable(bebidas[position].getImagen()));
                } else {
                    miImagen.setBackgroundDrawable(getResources().getDrawable(bebidas[position].getImagen()));
                }
                bebidaSeleccionada = bebidas[position].getBebida();
                imagenSeleccionada = bebidas[position].getImagen();
                precioBebida = bebidas[position].getPrecio();
                bebidaPasar=bebidas[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });//final miSpinner

        //tratamos los radioButtons para ver cual esta pulsado
        rg.clearCheck();
        r1.setChecked(true);
        vasoSeleccionado=String.valueOf(r1.getText());


        rg.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    //vasoSeleccionado = "";
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (r1.isChecked() == true) {
                            vasoSeleccionado=String.valueOf(r1.getText());
                            precioVaso=0.0;

                        } else if (r2.isChecked() == true) {
                            vasoSeleccionado=String.valueOf(r2.getText());
                            precioVaso=0.5;

                        } else if (r3.isChecked() == true) {
                            vasoSeleccionado=String.valueOf(r3.getText());
                            precioVaso=1.0;

                        } else if (r4.isChecked() == true) {
                            vasoSeleccionado=String.valueOf(r4.getText());
                            precioVaso=1.5;
                        }
                        showToast(vasoSeleccionado);

                    }
                });

    }//final initialUISetup

    //Tratar los checkeds seleccionados
    public String getAperitivoClick(View v){
        String strMessage = "";
        precioAperitivo=0.0;

        if(ch1.isChecked()){
            strMessage+=ch1.getText()+" ";
            precioAperitivo +=0.5;
        }
        if(ch2.isChecked()){
            strMessage+="Papas ";
            precioAperitivo +=1.0;
        }
        if(ch3.isChecked()){
            strMessage+="Aceitunas ";
            precioAperitivo +=1.0;
        }
        if(ch4.isChecked()){
            strMessage+="Bravas ";
            precioAperitivo +=3.0;
        }
        if(strMessage.length()==0){
            strMessage+="Sin Aperitivos";
        }
        return strMessage;
    }


    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    //Clase necesaria para el adaptador para Spinner

    class AdaptadorBebidas extends ArrayAdapter<Bebidas> {
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
    }//Fin AdaptadorBebidas

    //para que cuando vuelva de la pantalla2 deje los precios a 0
    @Override
    protected void onResume() {
        super.onResume();
        precioTotal=0.0;
    }


}


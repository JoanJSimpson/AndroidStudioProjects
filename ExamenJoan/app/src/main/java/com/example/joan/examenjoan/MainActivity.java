package com.example.joan.examenjoan;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joan.examenjoan.Pantalla2;
import com.example.joan.examenjoan.R;
import com.example.joan.examenjoan.Zonas;

public class MainActivity extends AppCompatActivity {


    public static int COD_RESPUESTA=0;

    //metemos datos de Zonas en la clase
    private Zonas[] zonas = new Zonas[]{
            new Zonas("A", "Asia y Oceania", 30 , R.drawable.asia_oceania),
            new Zonas("B", "America y Africa", 20 , R.drawable.america_africa),
            new Zonas("C", "Europa", 10 , R.drawable.europa),
    }; //fin Zonas

    //Creamos variables de clase

    Spinner miSpinner;
    String zonaSeleccionada, tarifaSeleccionada, cajaSeleccionada;
    Integer imagenSeleccionada;
    Double precioTotal, precioCaja, precioTarifa, precioZona;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialUISetup();
    }//Fin onCreate

    public void initialUISetup(){
        final TextView tit = (TextView) findViewById(R.id.TituloAgencia);
        precioCaja = 0.0;
        precioTotal=0.0;
        precioTarifa =0.0;
        precioZona =0.0;
        ch1 = (CheckBox) findViewById(R.id.ch1);
        ch2 = (CheckBox) findViewById(R.id.ch2);
        miImagen = (ImageView) findViewById(R.id.imagen);
        miBoton = (Button) findViewById(R.id.miTotal);
        rg = (RadioGroup) findViewById(R.id.rg);
        r1= (RadioButton) findViewById(R.id.radioButton1);
        r2= (RadioButton) findViewById(R.id.radioButton2);
        peso = (EditText) findViewById(R.id.peso);




        //Creamos señal para el boton de siguiente pantalla
        //Boton para pasar a la siguiente pantalla
        miBoton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String parsearPeso = String.valueOf(peso.getText());
                Double pesoSeleccionado, pesoMult;
                if (parsearPeso.equals("Introduce el peso del paquete")){
                    pesoSeleccionado = 0.0;
                    pesoMult=0.0;
                    precioPeso=0.0;
                }else{
                    pesoSeleccionado = Double.parseDouble(String.valueOf(peso.getText()));
                    if (pesoSeleccionado<=5.0){
                        pesoMult=1.0;
                    }else if (pesoSeleccionado<=10.0){
                        pesoMult=1.5;
                    }else {
                        pesoMult = 2.0;
                    }
                    precioPeso = pesoSeleccionado*pesoMult;
                }

                marcados= getDecoracionClick(v);
                //precioTotal = (precioZona + precioCaja + (Peso*precio))+((precioZona + precioCaja + (Peso*precio))*precioTarifa)
                precioTotal= (precioCaja + precioZona + precioPeso) + ((precioCaja + precioZona + precioPeso)*precioTarifa) ;
                Intent miIntent = new Intent(MainActivity.this, Pantalla2.class);
                Bundle miBundle = new Bundle();
                miBundle.putDouble("PRECIOCAJA", precioCaja);
                miBundle.putDouble("PRECIOZONA", precioZona);
                miBundle.putDouble("PRECIOPESO", pesoMult);
                miBundle.putDouble("PRECIOTARIFA", precioTarifa);
                miBundle.putString("ZONA", zonaSeleccionada);
                miBundle.putString("TARIFA", tarifaSeleccionada);
                miBundle.putInt("IMAGEN", imagenSeleccionada);
                miBundle.putDouble("PRECIOTOTAL", precioTotal);
                miBundle.putString("CAJA", marcados);
                miBundle.putDouble("PESO", pesoSeleccionado);
                miIntent.putExtras(miBundle);
                startActivityForResult(miIntent, COD_RESPUESTA);
                //startActivity(miIntent);


            }
        });//miBoton


        //Creamos el spiner
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
                zonaSeleccionada = zonas[position].getZona();
                imagenSeleccionada = zonas[position].getImagen();
                precioZona = zonas[position].getPrecio();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });//final miSpinner

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

    //Tratar los checkeds seleccionados
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


    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    //Clase necesaria para el adaptador para Spinner

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

    //para que cuando vuelva de la pantalla2 deje los precios a 0
    @Override
    protected void onResume() {
        super.onResume();
        precioTotal=0.0;
    }


}


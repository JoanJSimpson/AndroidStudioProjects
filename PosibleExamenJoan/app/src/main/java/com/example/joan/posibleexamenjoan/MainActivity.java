package com.example.joan.posibleexamenjoan;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.ListView;
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

    Spinner miSpinner;
    String bebidaSeleccionada, vasoSeleccionado, aperSeleccionado;
    Integer imagenSeleccionada;
    Double precioTotal;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declaracion de variables
        //final Button miBoton = (Button) findViewById(R.id.miTotal);
        //final ImageView miImagen = (ImageView) findViewById(R.id.imagen);
        //final RadioGroup rg = (RadioGroup) findViewById(R.id.gruporb);
        //final RadioButton r1= (RadioButton) findViewById(R.id.radio1);
        //final RadioButton r2= (RadioButton) findViewById(R.id.radio2);
        //final RadioButton r3= (RadioButton) findViewById(R.id.radio3);
        //final RadioButton r4= (RadioButton) findViewById(R.id.radio4);
        /*final CheckBox ch1 = (CheckBox) findViewById(R.id.ch1);
        final CheckBox ch2 = (CheckBox) findViewById(R.id.ch2);
        final CheckBox ch3 = (CheckBox) findViewById(R.id.ch3);
        final CheckBox ch4 = (CheckBox) findViewById(R.id.ch4);*/

        initialUISetup();


    }//Fin onCreate

    //Miramos que chekbox esta marcado

    public void initialUISetup(){
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
        //txtHobby = (TextView)findViewById(R.id.txtHobby);

        //Creamos señal para el boton de siguiente pantalla
        //Boton para pasar a la siguiente pantalla
        miBoton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                marcados= getHobbyClick(v);
                Intent miIntent = new Intent(MainActivity.this, Pantalla2.class);
                Bundle miBundle = new Bundle();
                //String mensajePaso = String.valueOf(datos[1].getEdad());
                miBundle.putString("BEBIDA", bebidaSeleccionada);
                miBundle.putInt("IMAGEN", imagenSeleccionada);
                miBundle.putDouble("PRECIO", precioTotal);
                miBundle.putString("APERITIVOS", marcados);
                miBundle.putString("VASO", vasoSeleccionado);
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
                String mensaje = "";
                mensaje = "Ha seleccionado: " + bebidas[position].getBebida();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    miImagen.setBackground(getDrawable(bebidas[position].getImagen()));
                } else {
                    miImagen.setBackgroundDrawable(getResources().getDrawable(bebidas[position].getImagen()));
                }
                bebidaSeleccionada = bebidas[position].getBebida();
                imagenSeleccionada = bebidas[position].getImagen();
                precioTotal = bebidas[position].getPrecio();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });//final miSpinner

        //tratamos los radioButtons
        rg.clearCheck();
        //rg.check(R.id.radio1);
        //int idSeleccionado = rg.getCheckedRadioButtonId();

        rg.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    //vasoSeleccionado = "";
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (r1.isChecked() == true) {
                            vasoSeleccionado=String.valueOf(r1.getText());

                        } else if (r2.isChecked() == true) {
                            vasoSeleccionado=String.valueOf(r2.getText());;

                        } else if (r3.isChecked() == true) {
                            vasoSeleccionado=String.valueOf(r3.getText());

                        } else if (r4.isChecked() == true) {
                            vasoSeleccionado=String.valueOf(r4.getText());
                        }
                        showToast(vasoSeleccionado);
                        //lblMensaje.setText("ID opcion seleccionada: " + checkedid);

                    }
                });




    }//final initialUISetup

    public String getHobbyClick(View v){
        String strMessage = "";

        if(ch1.isChecked()){
            strMessage+=ch1.getText()+" ";
        }
        if(ch2.isChecked()){
            strMessage+="Papas ";
        }
        if(ch3.isChecked()){
            strMessage+="Aceitunas ";
        }
        if(ch4.isChecked()){
            strMessage+="Bravas ";
        }
        return strMessage;
        //showToast(strMessage);
    }


    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    //Miramos que checkBox está seleccionado

    /*public String getChecked(View v){
        strMessage = "";
        //String coje="";
        if(ch1.isChecked()){
            //coje=String.valueOf(ch1.getText());
            strMessage+= "Hielo ";
        }
        if(ch2.isChecked()){
            strMessage+="Papas ";
        }
        if(ch3.isChecked()){
            strMessage+="Aceitunas ";
        }
        if(ch4.isChecked()){
            strMessage+="Bravas ";
        }
        return strMessage;
    }*/



    class AdaptadorBebidas extends ArrayAdapter<Bebidas> {
            public Activity miActividad;

            public AdaptadorBebidas(Activity laActividad){
                super (laActividad, R.layout.desmilista, bebidas);
                this.miActividad = laActividad;

            }
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
                lblPrecio.setText(String.valueOf(bebidas[position].getPrecio()));


                return (item);
            }
        }//Fin AdaptadorBebidas




    }


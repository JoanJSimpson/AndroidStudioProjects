package com.example.joan.examenjpiera;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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


public class MainActivity extends AppCompatActivity {


    public static int COD_RESPUESTA=0;

    //metemos datos de Pizzas en la clase
    private Pizzas[] pizza = new Pizzas[]{
            new Pizzas("MARGARITA", "jamon/tomate", 12 , R.drawable.pizza1),
            new Pizzas("TRES QUESOS", "queso1/queso2", 15 , R.drawable.pizza2),
            new Pizzas("BARBACOA", "carne/tomate", 18 , R.drawable.pizza3)
    }; //fin Pizzas

    //Creamos variables de clase

    Spinner miSpinner;
    String pizzaSeleccionada, modoSeleccionado;
    Integer imagenSeleccionada;
    Double precioTotal, precioIngredientes, precioModo, precioPizza;
    CheckBox ch1, ch2, ch3;
    ImageView miImagen, imagenSpinner;
    Button miBoton;
    RadioGroup rg;
    RadioButton r1;
    RadioButton r2;
    EditText unidades;
    TextView precio, lblMensaje;
    int numeroUnidades;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //Alternativa 1
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_menu_inicial, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MnuOpc1:
                lblMensaje.setText("Opcion 1 pulsada!");
                return true;
            case R.id.MnuOpc2:
                lblMensaje.setText("Opcion 2 pulsada!");
                return true;
            //case R.id.MnuOpc3:
              //  lblMensaje.setText("Opcion 3 pulsada!");
                //return true;
            case R.id.SubMnuOpc1:
                lblMensaje.setText("Submenu: Opcion 1!");
                return true;
            case R.id.SubMnuOpc2:
                lblMensaje.setText("Submenu: Acerca de..!");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialUISetup();
    }//Fin onCreate

    public void initialUISetup(){
        precioIngredientes = 0.0;
        precioTotal=0.0;
        precioModo =0.0;
        numeroUnidades=0;
        precioPizza=0.0;
        lblMensaje = (TextView) findViewById(R.id.lblMensaje);
        ch1 = (CheckBox) findViewById(R.id.ch1);
        ch2 = (CheckBox) findViewById(R.id.ch2);
        ch3 = (CheckBox) findViewById(R.id.ch3);
        miImagen = (ImageView) findViewById(R.id.imagen);
        imagenSpinner = (ImageView) findViewById(R.id.imagenSpinner);
        miBoton = (Button) findViewById(R.id.miTotal);
        rg = (RadioGroup) findViewById(R.id.rg);
        r1= (RadioButton) findViewById(R.id.radioButton1);
        r2= (RadioButton) findViewById(R.id.radioButton2);
        unidades = (EditText) findViewById(R.id.unidades);




        //Creamos señal para el boton de siguiente pantalla
        //Boton para pasar a la siguiente pantalla
        miBoton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String parsearUnidades = String.valueOf(unidades.getText());
                if (parsearUnidades.equals("")) {
                    //showToast("Ha elegido 1 unidad");
                    numeroUnidades = 1;
                } else {
                    numeroUnidades = Integer.parseInt(String.valueOf(unidades.getText()));

                }

                getIngredientesClick(v);
                //precioTotal = ((precioPizza + precioIngredientes)*Unidades)+(todoanterior*enviodomicilio))

                /////******MODIFICAR EL RESULTADO!!!!
                double precio1=((precioPizza+precioIngredientes)*numeroUnidades);
                precioTotal = precio1+(precio1*precioModo);
                //precioTotal = (precioIngredientes + precioPizza + numeroUnidades) + ((precioIngredientes + precioPizza + numeroUnidades) * precioModo);
                Intent miIntent = new Intent(MainActivity.this, Pantalla2.class);
                Bundle miBundle = new Bundle();
                miBundle.putDouble("PRECIOINGREDIENTES", precioIngredientes);
                miBundle.putDouble("PRECIOPIZZA", precioPizza);
                miBundle.putDouble("PRECIOMODO", precioModo);
                miBundle.putString("PIZZA", pizzaSeleccionada);
                miBundle.putString("MODO", modoSeleccionado);
                miBundle.putInt("IMAGEN", imagenSeleccionada);
                miBundle.putDouble("PRECIOTOTAL", precioTotal);
                miBundle.putInt("UNIDADES", numeroUnidades);
                miIntent.putExtras(miBundle);
                startActivityForResult(miIntent, COD_RESPUESTA);
                //startActivity(miIntent);


            }
        });//miBoton
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            miImagen.setBackground(getDrawable(R.drawable.logopizza));
        } else {
            miImagen.setBackgroundDrawable(getResources().getDrawable(R.drawable.logopizza));
        }

        //Creamos el spiner
        miSpinner = (Spinner) findViewById(R.id.spinner1);
        AdaptadorPizzas miAdaptador= new AdaptadorPizzas(this);
        miSpinner.setAdapter(miAdaptador);
        miSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView arg0, View arg1, int position, long id) {


                pizzaSeleccionada = pizza[position].getNombre();
                imagenSeleccionada = pizza[position].getImagen();
                precioPizza = pizza[position].getPrecio();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });//final miSpinner

        //tratamos los radioButtons para ver cual esta pulsado
        rg.clearCheck();
        r1.setChecked(true);
        modoSeleccionado =String.valueOf(r1.getText());


        rg.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (r1.isChecked() == true) {
                            modoSeleccionado = String.valueOf(r1.getText());
                            precioModo = 0.0;

                        } else if (r2.isChecked() == true) {
                            modoSeleccionado = String.valueOf(r2.getText());
                            precioModo = (10.0/100.0);

                        }
                        showToast(modoSeleccionado);
                    }
                });

    }//final initialUISetup

    //Tratar los checkeds seleccionados
    public void getIngredientesClick(View v){
        precioIngredientes =0.0;

        if(ch1.isChecked()){
            precioIngredientes +=1.0;
        }
        if(ch2.isChecked()){
            precioIngredientes +=1.0;
        }
        if(ch3.isChecked()){
            precioIngredientes +=1.0;
        }


    }


    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    //Clase necesaria para el adaptador para Spinner

    class AdaptadorPizzas extends ArrayAdapter<Pizzas> {
        public Activity miActividad;

        public AdaptadorPizzas(Activity laActividad){
            super (laActividad, R.layout.desmilista, pizza);
            this.miActividad = laActividad;

        }
        // Vista para el desplegable del spinner
        public View getDropDownView(int position, View convertView, ViewGroup parent){
            View ListaDesplegada = getView(position, convertView, parent);
            return ListaDesplegada;
        }
        //metodo mejorado para consumo de memoria en un spinner
        public View getView(int position, View convertView, ViewGroup parent) {
            View item = convertView;
            ViewHolder holder;
            if (item == null) {
                LayoutInflater inflater = miActividad.getLayoutInflater();
                item = inflater.inflate(R.layout.desmilista, null);
                holder = new ViewHolder();
                holder.lblNombre = (TextView) item.findViewById(R.id.campoNombre);
                holder.lblIngredientes = (TextView) item.findViewById(R.id.campoIngredientes);
                holder.lblPrecio = (TextView) item.findViewById(R.id.campoPrecio);
                holder.lblImagen = (ImageView) item.findViewById(R.id.imagenSpinner);
                item.setTag(holder);
            }else{
                holder = (ViewHolder)item.getTag();
            }

            holder.lblNombre.setText(pizza[position].getNombre());
            holder.lblIngredientes.setText(pizza[position].getIngredientes());
            holder.lblPrecio.setText(String.valueOf(pizza[position].getPrecio()) + " €");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.lblImagen.setBackground(getDrawable(pizza[position].getImagen()));
            } else {
                holder.lblImagen.setBackgroundDrawable(getResources().getDrawable(pizza[position].getImagen()));
            }

            return(item);
        }
    }//Fin AdaptadorPizzas

    //para que cuando vuelva de la pantalla2 deje los precios a 0
    @Override
    protected void onResume() {
        super.onResume();
        precioTotal=0.0;
        //unidades.setHint("Numero de unidades: ");
        unidades.setText("");
        r1.setChecked(true);
        ch1.setChecked(false);
        ch2.setChecked(false);
        ch3.setChecked(false);
    }

    static class ViewHolder {
        TextView lblNombre;
        TextView lblIngredientes;
        TextView lblPrecio;
        ImageView lblImagen;
    }


}


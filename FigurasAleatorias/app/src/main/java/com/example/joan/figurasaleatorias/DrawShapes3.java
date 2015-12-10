package com.example.joan.figurasaleatorias;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

/**
 * Created by Joan on 3/12/15.
 */
public class DrawShapes3 extends Activity {
    String figuraSeleccionada;
    TextView label1, label2;
    EditText lbl1, lbl2;
    Button btnDibujar;
    static Circulo circulo = new Circulo();
    static Cuadrado cuadrado = new Cuadrado();
    static Rectangulo rectangulo = new Rectangulo();
    public static int COD_RESPUESTA=0;

    private FigSpinner[] figura = new FigSpinner[]{
            new FigSpinner("Circulo", R.drawable.circulo, circulo),
            new FigSpinner("Cuadrado", R.drawable.cuadrado, cuadrado),
            new FigSpinner("Rectangulo", R.drawable.rectangulo, rectangulo)

    };




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_shape3);

        label1 = (TextView) findViewById(R.id.label1);
        label2 = (TextView) findViewById(R.id.label2);
        lbl1 = (EditText) findViewById(R.id.lbl1);
        lbl2 = (EditText) findViewById(R.id.lbl2);
        btnDibujar = (Button) findViewById(R.id.btnDibujar);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int ancho = size.x;
        final int alto = size.y;


        //Boton para calcular y dibujar:
        btnDibujar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                float lado1, lado2;
                if (String.valueOf(lbl1.getText()).equals("")){
                    lado1 = Float.valueOf(10);
                }else {
                    lado1 = Float.valueOf(String.valueOf(lbl1.getText()));
                }
                if (String.valueOf(lbl2.getText()).equals("")){
                    lado2 = Float.valueOf(10);
                }else {
                    lado2 = Float.valueOf(String.valueOf(lbl2.getText()));
                }
                Intent miIntent = new Intent(DrawShapes3.this , PantallaFinal.class);
                Bundle miBundle = new Bundle();


                //Si esta en vertical y es un circulo
                if(lado1>((ancho-10)/2) && figuraSeleccionada.equals("Circulo")){
                    showToast("El radio no puede ser mayor a: " + ((ancho-10) / 2));

                //si esta en horizontal y es un circulo
                }else if(lado1>(((alto-10)/2)-100)&&figuraSeleccionada.equals("Circulo")){
                    showToast("El radio no puede ser mayor a: " + (((alto-10) / 2)-100));

                //Si esta en vertical y es un cuadrado o rectangulo
                }else if (lado1>(ancho-10)){
                    switch (figuraSeleccionada){
                        case "Cuadrado":
                            showToast("El lado no puede ser mayor a: "+(ancho-10));
                            break;
                        case "Rectangulo":
                            showToast("La base no puede ser mayor a: "+(ancho-10));
                            break;
                    }

                //si esta en horizontal y es un cuadrado
                }else if ((lado1>(alto-200)) && figuraSeleccionada.equals("Cuadrado")){
                    showToast("El lado no puede ser mayor a: "+(alto-200));

                 //si es un rectangulo y supera la altura
                }else if (figuraSeleccionada.equals("Rectangulo") && lado2>(alto-200)){
                    showToast("La altura no puede ser mayor a: "+(alto-200));
                //Si está correcto
                }else{

                    //miBundle.putFloat("LADO1", lado1);
                    //miBundle.putFloat("LADO2", lado2);
                    miBundle.putString("FIGURA", figuraSeleccionada);
                    if (figuraSeleccionada.equals("Circulo")) {
                        circulo.setRadio(lado1);
                        miBundle.putDouble("AREA", circulo.area());
                        //miBundle.putSerializable("TIPO", (Serializable) circulo);
                    }
                    if (figuraSeleccionada.equals("Cuadrado")) {
                        cuadrado.setLado(lado1);
                        miBundle.putDouble("AREA", cuadrado.area());
                        //miBundle.putSerializable("TIPO", (Serializable) cuadrado);
                    }
                    if (figuraSeleccionada.equals("Rectangulo")) {
                        rectangulo.setBase(lado1);
                        rectangulo.setAltura(lado2);
                        miBundle.putDouble("AREA", rectangulo.area());
                        //miBundle.putSerializable("TIPO", (Serializable) rectangulo);
                    }
                    miIntent.putExtras(miBundle);
                    startActivityForResult(miIntent, COD_RESPUESTA);
                }

            }
        });//miBoton


        /************
         * SPINNER
         */

        Spinner miSpinner = (Spinner) findViewById(R.id.spinner1);
        AdaptadorSpinner miAdaptador = new AdaptadorSpinner(this);
        miSpinner.setAdapter(miAdaptador);
        miSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView arg0, View arg1, int position, long id) {
                figuraSeleccionada = figura[position].getNombre();
                Figura figuras = figura[position].getFigura();
                //showToast(figuras.toString());

                if (figuraSeleccionada.equals("Circulo")) {
                    label1.setText("Radio: ");
                    label1.setVisibility(EditText.VISIBLE);
                    label2.setVisibility(EditText.INVISIBLE);
                    lbl1.setVisibility(EditText.VISIBLE);
                    lbl2.setVisibility(EditText.INVISIBLE);

                }
                if (figuraSeleccionada.equals("Cuadrado")){
                    label1.setText("Lado: ");
                    label1.setVisibility(TextView.VISIBLE);
                    lbl1.setVisibility(EditText.VISIBLE);
                    label2.setText("Lado 2: ");
                    label2.setVisibility(TextView.INVISIBLE);
                    lbl2.setVisibility(EditText.INVISIBLE);
                }
                if (figuraSeleccionada.equals("Rectangulo")){
                    label1.setText("Base: ");
                    label1.setVisibility(TextView.VISIBLE);
                    lbl1.setVisibility(EditText.VISIBLE);
                    label2.setText("Altura: ");
                    label2.setVisibility(TextView.VISIBLE);
                    lbl2.setVisibility(EditText.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });//final miSpinner

    }//Final onCreate


    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    class AdaptadorSpinner extends ArrayAdapter<FigSpinner> {
        public Activity miActividad;

        public AdaptadorSpinner(Activity laActividad){
            super (laActividad, R.layout.desmilista, figura);
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
                holder.lblImagen = (ImageView) item.findViewById(R.id.imagenSpinner);

                item.setTag(holder);
            }else{
                holder = (ViewHolder)item.getTag();
            }

            holder.lblNombre.setText(figura[position].getNombre());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.lblImagen.setBackground(getDrawable(figura[position].getImagen()));
            } else {
                holder.lblImagen.setBackgroundDrawable(getResources().getDrawable(figura[position].getImagen()));
            }



            return(item);
        }
    }//Fin AdaptadorSpinner



    static class ViewHolder {
        TextView lblNombre;
        ImageView lblImagen;
    }

    @Override
    protected void onResume() {
        super.onResume();
        lbl1.setText("");
        lbl2.setText("");
    }

}



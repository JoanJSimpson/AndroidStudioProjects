package com.example.joan.figurasaleatorias;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;



/**
 * Created by Joan on 6/12/15.
 */
public class PantallaFinal extends Activity {

    static String figura;
    static TextView txt1, txt2, txt3, txt4, txt5, txt6;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallafinal);

        /****
         * Recogemos el BUNDLE
         */
        txt1 = (TextView) findViewById(R.id.txtFinal1);
        txt2 = (TextView) findViewById(R.id.txtFinal2);
        txt3 = (TextView) findViewById(R.id.txtFinal3);
        txt4 = (TextView) findViewById(R.id.txtFinal4);
        txt5 = (TextView) findViewById(R.id.txtFinal5);
        txt6 = (TextView) findViewById(R.id.txtFinal6);

        Bundle miBundleRecoger = getIntent().getExtras();
        figura = miBundleRecoger.getString("FIGURA");

    }//final onCreate


    public static class VistaFigura extends View {
        public VistaFigura(Context contexto, AttributeSet attr) {
            super(contexto, attr);


        }

        @Override
        protected void onDraw(Canvas canvas) {
            //Dentro de este método utilizamos los métodos para dibujar BitmapDrawable

            Paint miPincel= new Paint();
            miPincel.setColor(Color.CYAN);
            float x = canvas.getWidth()/2;
            float y = canvas.getHeight()/2;
            miPincel.setStrokeWidth(10);
            miPincel.setStyle(Paint.Style.STROKE);
            RectF rectF;
            switch (figura){
                case "Circulo":

                    float radio = (float)DrawShapes3.circulo.getRadio();
                    txt3.setText("Radio: ");
                    txt4.setText(String.valueOf(radio));
                    txt1.setVisibility(TextView.INVISIBLE);
                    txt2.setVisibility(TextView.INVISIBLE);
                    txt5.setText("Area: ");
                    txt6.setText(String.valueOf(DrawShapes3.circulo.area()));

                    canvas.drawCircle(x, y, radio, miPincel);
                    break;
                case "Cuadrado":
                    float lado = (float)(DrawShapes3.cuadrado.getLado());
                    txt3.setText("Lados: ");
                    txt4.setText(String.valueOf(lado) + " x " + String.valueOf(lado));
                    txt1.setVisibility(TextView.INVISIBLE);
                    txt2.setVisibility(TextView.INVISIBLE);
                    txt5.setText("Area: ");
                    txt6.setText(String.valueOf(DrawShapes3.cuadrado.area()));

                    rectF = new RectF((x-(lado/2)), (y-(lado/2)), (x+(lado/2)), (y+(lado/2)));
                    canvas.drawRect(rectF, miPincel);
                    break;
                case "Rectangulo":
                    float base = (float)(DrawShapes3.rectangulo.getBase());
                    float altura = (float)(DrawShapes3.rectangulo.getAltura());
                    txt1.setText("Base: ");
                    txt2.setText(String.valueOf(base));
                    txt3.setText("Altura: ");
                    txt4.setText(String.valueOf(altura));
                    txt5.setText("Area: ");
                    txt6.setText(String.valueOf(DrawShapes3.rectangulo.area()));


                    rectF = new RectF((x-(base/2)), (y-(altura/2)), (x+(base/2)), (y+(altura/2)));
                    canvas.drawRect(rectF, miPincel);
                    break;
            }

        }
    }
    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}//final clase

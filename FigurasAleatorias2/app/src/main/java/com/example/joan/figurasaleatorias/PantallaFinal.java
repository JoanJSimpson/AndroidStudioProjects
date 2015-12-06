package com.example.joan.figurasaleatorias;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Joan on 6/12/15.
 */
public class PantallaFinal extends Activity {
    static Float lado1, lado2;
    static Figura tipo;
    static Double area;
    static String figura;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallafinal);

        /****
         * Recogemos el BUNDLE
         */

        Bundle miBundleRecoger = getIntent().getExtras();
        lado1 = miBundleRecoger.getFloat("LADO1");
        lado2 = miBundleRecoger.getFloat("LADO2");
        figura = miBundleRecoger.getString("FIGURA");
        tipo = (Figura) miBundleRecoger.getSerializable("TIPO");
        area = miBundleRecoger.getDouble("AREA");
        showToast("Lado1: "+lado1+". Lado 2: "+lado2+ ". Figura: "+figura+". Tipo: "+tipo.toString()+". Area: "+area);



    }//final onCreate


    public static class VistaFigura extends View {
        public VistaFigura(Context contexto) {
            super(contexto);
            //Resources res = contexto.getResources();

        }

        @Override
        protected void onDraw(Canvas canvas) {
            //Dentro de este método utilizamos los métodos para dibujar BitmapDrawable
            Paint miPincel= new Paint();
            miPincel.setColor(Color.RED);

            miPincel.setStrokeWidth(10);
            miPincel.setStyle(Paint.Style.STROKE);
            if (PantallaFinal.figura.equals("Circulo")){
                canvas.drawCircle(300,300,lado1,miPincel);
            }
        }
    }
    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}//final clase

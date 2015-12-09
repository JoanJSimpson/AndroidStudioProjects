package com.example.joan.figurasaleatorias;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Joan on 6/12/15.
 */
public class PantallaFinal extends Activity {
    static float lado1, lado2;
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
        //tipo = (Figura) miBundleRecoger.getSerializable("TIPO");
        area = miBundleRecoger.getDouble("AREA");
        showToast("Lado1: "+lado1+". Lado 2: "+lado2+ ". Figura: "+figura+". Area: "+area);



    }//final onCreate


    public static class VistaFigura extends View {
        public VistaFigura(Context contexto, AttributeSet attr) {
            super(contexto, attr);
            //Resources res = contexto.getResources();

        }

        @Override
        protected void onDraw(Canvas canvas) {
            //Dentro de este método utilizamos los métodos para dibujar BitmapDrawable
            Paint miPincel= new Paint();
            miPincel.setColor(Color.RED);
            float x = canvas.getWidth()/2;
            float y = canvas.getHeight()/2;
            miPincel.setStrokeWidth(10);
            miPincel.setStyle(Paint.Style.STROKE);
            if (PantallaFinal.figura.equals("Circulo")){
                canvas.drawCircle((x-(lado1/2)),(y-(lado1/2)),lado1, miPincel);
            }else if(PantallaFinal.figura.equals("Cuadrado")) {
                RectF rectF = new RectF(150, 150, 150+lado1,150+lado1);
                canvas.drawRect(rectF, miPincel);
            }else{
                RectF rectF = new RectF(150, 150, 150+lado1, 150+lado2);
                canvas.drawRect(rectF, miPincel);
            }

        }
    }
    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}//final clase

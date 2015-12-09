package com.example.joan.ejemploshapedrawable;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class EjemploShapeDrawable extends Activity {

    public boolean onCreateOptionsMenu(Menu menu) { //Alternativa 1
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ejemplo_shape_drawable, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MnuOpc1:
                //lblMensaje.setText("Opcion 1 pulsada!");
                return true;
            case R.id.MnuOpc2:
                //lblMensaje.setText("Opcion 2 pulsada!");
                return true;
            //case R.id.MnuOpc3:
            //  lblMensaje.setText("Opcion 3 pulsada!");
            //return true;
            case R.id.SubMnuOpc1:
                //lblMensaje.setText("Submenu: Opcion 1!");
                return true;
            case R.id.SubMnuOpc2:
                //lblMensaje.setText("Submenu: Acerca de..!");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new VistaAMedida(this));
        setContentView(R.layout.activity_ejemplo_shape_drawable);
    }

    public static class VistaAMedida extends View {
        private ShapeDrawable miDrawable;

        public VistaAMedida(Context context, AttributeSet attrs) {
            super(context, attrs);
            int x=10, y=10;
            int ancho=300, alto=50;
            miDrawable = new ShapeDrawable(new OvalShape());
            miDrawable.getPaint().setColor(0xff0000ff);
            miDrawable.setBounds(x, y, x + ancho, y + alto);
        }
        /*public VistaAMedida(Context contexto) {
            super(contexto);
            int x=10, y=10;
            int ancho=300, alto=50;
            miDrawable = new ShapeDrawable(new OvalShape());
            miDrawable.getPaint().setColor(0xff0000ff);
            miDrawable.setBounds(x, y, x + ancho, y + alto);

        }*/

        protected void onDraw(Canvas canvas) {
            int alto = canvas.getHeight();
            int ancho = canvas.getWidth();

            miDrawable.draw(canvas);

            Paint miPincel= new Paint();
            Paint miPincel2 = new Paint();
            //miPincel.setColor(Color.RED);
            //miPincel.setTextSize(50);

            miPincel.setStrokeWidth(10);
            miPincel.setStyle(Paint.Style.STROKE);
            //canvas.drawText("Alto: "+alto,1,50,miPincel);
            //canvas.drawText("Ancho: "+ancho,1,110,miPincel);
            for(int i=100; i<=360; i+=120){
                if (i==100){
                    miPincel.setColor(Color.BLUE);
                }else if(i==220){
                    miPincel.setColor(Color.BLACK);
                }else{
                    miPincel.setColor(Color.RED);
                }
                canvas.drawCircle(100 + i, 300, 100, miPincel);
                //canvas.drawCircle(500, 950, 5+i, miPincel2);
            }
            for (int i=100; i<=220; i+=120){
                if (i==100){
                    miPincel.setColor(Color.YELLOW);
                }else{
                    miPincel.setColor(Color.GREEN);
                }
                canvas.drawCircle(150 + i, 400, 100, miPincel);
            }
            //canvas.drawPoint(500,300,miPincel2);

            miPincel.setTextSize(30);
            String titulo = "OLIMPIC GAMES";
            miPincel.setStrokeWidth(2);
            miPincel.setColor(Color.BLACK);
            canvas.drawText(titulo, (ancho/2)-(titulo.length()*10), alto/2, miPincel);
            RectF rectF = new RectF(275,700,350,900);
            miPincel.setStyle(Paint.Style.FILL);

        }
    }


}
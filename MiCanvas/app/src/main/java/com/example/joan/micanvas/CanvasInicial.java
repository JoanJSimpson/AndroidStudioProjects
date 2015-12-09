package com.example.joan.micanvas;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class CanvasInicial extends Activity {
    private BitmapDrawable miImagen;

    public boolean onCreateOptionsMenu(Menu menu) { //Alternativa 1
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_menu_inicial, menu);
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public class EjemploView extends View {
        public EjemploView(Context contexto) {
            super(contexto);
            Resources res = contexto.getResources();
            miImagen = (BitmapDrawable) res.getDrawable(R.drawable.logo_cefire);
            miImagen.setBounds(new Rect(300,300,500,500));
        }
        @Override
        protected void onDraw(Canvas canvas) {
            //Dentro de este método utilizamos los métodos para dibujar BitmapDrawable
            miImagen.draw(canvas);

            Paint miPincel= new Paint();
            Paint miPincel2 = new Paint();
            miPincel.setColor(Color.RED);

            miPincel.setStrokeWidth(10);
            miPincel.setStyle(Style.STROKE);
            for(int i=100; i<=360; i+=120){
                if (i==100){
                    miPincel.setColor(Color.BLUE);
                }else if(i==220){
                    miPincel.setColor(Color.BLACK);
                }else{
                    miPincel.setColor(Color.RED);
                }
                canvas.drawCircle(300 + i, 300, 100, miPincel);
                //lienzo.drawCircle(500, 950, 5+i, miPincel2);
            }
            for (int i=100; i<=220; i+=120){
                if (i==100){
                    miPincel.setColor(Color.YELLOW);
                }else{
                    miPincel.setColor(Color.GREEN);
                }
                canvas.drawCircle(350 + i, 400, 100, miPincel);
            }
            //canvas.drawPoint(500,300,miPincel2);

            miPincel.setTextSize(30);
            miPincel.setStrokeWidth(2);
            miPincel.setColor(Color.BLACK);
            canvas.drawText("OLIMPIC GAMES", 400, 600, miPincel);
            RectF rectF = new RectF(475,900,550,1100);
            miPincel.setStyle(Style.FILL);
        }
    }

}

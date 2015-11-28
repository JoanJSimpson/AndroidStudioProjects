package com.example.joan.dibujar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;



public class DibujoInicial extends AppCompatActivity {

    class Punto {
        float x, y;
        public Punto(float coordx, float coordy) {

            this.x=coordx;
            this.y=coordy;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MiDibujo(this));

    }

    class MiDibujo extends View {

        public MiDibujo (Context c){
            super(c);
        }

        protected void onDraw(Canvas lienzo){
            //Dentro de este metodo utilizamos los metodos para dibujar
            Paint miPincel= new Paint();
            Paint miPincel2 = new Paint();
            miPincel.setColor(Color.RED);

            miPincel.setStrokeWidth(10);
            miPincel.setStyle(Paint.Style.STROKE);
            for(int i=100; i<=360; i+=120){
                if (i==100){
                    miPincel.setColor(Color.BLUE);
                }else if(i==220){
                    miPincel.setColor(Color.BLACK);
                }else{
                    miPincel.setColor(Color.RED);
                }
                lienzo.drawCircle(300 + i, 300, 100, miPincel);
                //lienzo.drawCircle(500, 950, 5+i, miPincel2);
            }
            for (int i=100; i<=220; i+=120){
                if (i==100){
                    miPincel.setColor(Color.YELLOW);
                }else{
                    miPincel.setColor(Color.GREEN);
                }
                lienzo.drawCircle(350 + i, 400, 100, miPincel);
            }
            //lienzo.drawPoint(500,300,miPincel2);

            miPincel.setTextSize(30);
            miPincel.setStrokeWidth(2);
            miPincel.setColor(Color.BLACK);
            lienzo.drawText("OLIMPIC GAMES", 400, 600, miPincel);
            RectF rectF = new RectF(475,900,550,1100);
            miPincel.setStyle(Paint.Style.FILL);

            lienzo.drawRect(rectF, miPincel);
            miPincel.setColor(Color.rgb(255, 117, 020));
            miPincel.setStrokeWidth(10);
            //miPincel.setColor(Color.BLACK);
            Path miPath = new Path();
            Punto punto1= new Punto(475,990);
            Punto punto2 = new Punto(465,980);
            miPath.moveTo(punto1.x, punto1.y);
            lienzo.drawPath(miPath, miPincel);
            miPath.moveTo(punto2.x, punto2.y);
            lienzo.drawPath(miPath , miPincel);

            //lienzo.drawArc();


        }
    }
}

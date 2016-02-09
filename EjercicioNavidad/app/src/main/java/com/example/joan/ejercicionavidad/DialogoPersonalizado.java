package com.example.joan.ejercicionavidad;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Joan on 19/1/16.
 * Dialogo personalizado
 */
public class DialogoPersonalizado extends DialogFragment {
    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = new MiDibujo(getActivity());


                //builder.setView(inflater.inflate(R.layout.dialog_acerca_de, null))
                 builder.setView(v)
                        .setTitle("Acerca De...")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }

    class MiDibujo extends View {
        public MiDibujo(Context c){

            super(c);
        }

        protected void onDraw(Canvas lienzo){
            Paint miPincel= new Paint();
            miPincel.setColor(Color.BLUE);
            miPincel.setStyle(Paint.Style.STROKE);
            miPincel.setStrokeWidth(5);
            miPincel.setTextSize(30);
            int width = this.getWidth();
            int ancho = width/5;
            int height = this.getHeight();
            int alto = height / 20;


            lienzo.drawRect(ancho, alto * 5, ancho * 4, alto * 11, miPincel);

            lienzo.drawLine(ancho, alto * 5, ancho / 3, alto * 3, miPincel);
            lienzo.drawLine(ancho, alto * 11, ancho / 3, alto * 9, miPincel);
            lienzo.drawLine(ancho/3, alto * 3, ancho / 3, alto * 9, miPincel);
            lienzo.drawLine((ancho*4)-(2*ancho/3),alto*3,(ancho*4)-(2*ancho/3), alto*5, miPincel);

            lienzo.drawLine(ancho*4, alto*5, (ancho*4)-(2*ancho/3), alto*3, miPincel);
            lienzo.drawLine((ancho*4)-(2*ancho/3), alto*3, ancho, alto*3, miPincel);
            //*,*,ancho/3,*
            lienzo.drawLine(ancho/3, alto*3, ancho/3, alto, miPincel);
            lienzo.drawLine(ancho, alto*5, ancho, alto*3, miPincel);
            lienzo.drawLine(ancho/3, alto, ancho, alto*3, miPincel);

            lienzo.drawLine((ancho*4)-(2*ancho/3), alto*3, ancho*4, alto*2, miPincel);
            lienzo.drawLine(ancho*4, alto*5,(ancho*4)+(2*ancho/3), alto*4, miPincel);
            lienzo.drawLine(ancho*4, alto*2, (ancho*4)+(2*ancho/3),alto*4,miPincel);

            miPincel.setStrokeWidth(2);
            miPincel.setTextSize(30);
            lienzo.drawText("Agencia de Transportes", (ancho + (ancho / 5)), alto * 7, miPincel);


            miPincel.setStrokeWidth(1);
            miPincel.setTextSize(20);
            lienzo.drawText("Version 0.9.", (ancho + (ancho / 5)), alto * 15, miPincel);
            lienzo.drawText("Copyright © 2016 Joan Piera Simó.",(ancho +(ancho/5)), alto*16, miPincel);
            lienzo.drawText("Todos los derechos reservados.",(ancho +(ancho/5)), alto*17, miPincel);

            //lienzo.drawText("Agencia de Transportes", ancho, altura*18, miPincel);
            //lienzo.drawText("®Joan", ancho, altura* 19, miPincel);

        }
    }
}

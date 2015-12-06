package com.example.joan.figurasaleatorias;

import android.graphics.Color;

/**
 * Created by Joan on 3/12/15.
 */
public class Circulo extends Figura
{
    protected double radio;

    /** Constructor por defecto: crea un círculo de color negro (Color.black) y radio 10.0  */
    public Circulo() {
        super(Color.BLACK, "circulo");
        radio = 10.0;
    }
    public Circulo(int color, double radio) {
        super(color, "circulo");
        this.radio = radio;
    }

    public double getRadio() {
        return this.radio;
    }

    public void setRadio(double radio) {
        this.radio = radio;
    }

    public double area() {
        return Math.PI * radio * radio;
    }

    public String toString(){
        return "Circulo " + color + " de radio " + radio;
    }
}

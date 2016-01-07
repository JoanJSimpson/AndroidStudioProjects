package com.example.joan.posibleexamenjoan;

import java.io.Serializable;

/**
 * Created by Joan on 6/11/15.
 */
public class Bebidas implements Serializable {

        private String bebida, aperitivo;
        private double precio;
        private int imagen;
        public Bebidas (String beb, double pre, int image){
            bebida = beb;
            precio  = pre;
            imagen = image;
        }
        public String getBebida (){return bebida;}
        public double getPrecio () { return precio; }
        public int getImagen(){ return imagen;}

        public String toString(){
            return ("Bebida seleccionada: "+bebida+". Precio: "+precio);
        }


}

package com.example.joan.examenjoan;

/**
 * Created by Joan on 9/11/15.
 */
public class Zonas {

        private String zona, continente;
        private double precio;
        private int imagen;
        public Zonas(String zon, String cont, double pre, int image){
            zona = zon;
            continente = cont;
            precio  = pre;
            imagen = image;
        }
        public String getZona(){return zona;}
        public String getContinente(){return continente;}
        public double getPrecio () { return precio; }
        public int getImagen(){ return imagen;}

        public String toString(){
            return (zona +". "+continente+". Precio: "+precio);
        }


}

package com.example.joan.examenjpiera;

/**
 * Created by Joan on 9/11/15.
 */
public class Pizzas {

        private String nombre, ingredientes;
        private double precio;
        private int imagen;
        public Pizzas(String nom, String ing, double pre, int image){
            nombre = nom;
            ingredientes = ing;
            precio  = pre;
            imagen = image;
        }
        public String getNombre(){return nombre;}
        public String getIngredientes(){return ingredientes;}
        public double getPrecio () { return precio; }
        public int getImagen(){ return imagen;}

        public String toString(){
            return (nombre +". "+ingredientes+". Precio: "+precio);
        }


}

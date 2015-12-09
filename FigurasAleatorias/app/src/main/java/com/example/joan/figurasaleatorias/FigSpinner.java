package com.example.joan.figurasaleatorias;

/**
 * Created by Joan on 9/11/15.
 */
public class FigSpinner {

        private String nombre;
        private int imagen;
        private Figura figura;
        public FigSpinner(String nom, int image, Figura fig){
            nombre = nom;
            imagen = image;
            figura = fig;
        }
        public String getNombre(){return nombre;}

        public int getImagen(){ return imagen;}

        public Figura getFigura(){ return figura;}

        public String toString(){
            return (nombre +". ");
        }


}

package com.example.joan.clasetitulares;

/**
 * Created by Joan on 2/11/15.
 */
public class Titular {

        private String titulo, subtitulo;
        private int edad, imagen;
        public Titular (String s, String s1, int e, int image){
            titulo = s;
            subtitulo = s1;
            edad  = e;
            imagen = image;
        }
        public String getTitulo (){return titulo;}
        public String getSubtitulo (){
           return subtitulo;
        }
        public int getEdad () { return edad; }
        public int getImagen(){ return imagen;}

        public String toString(){
            return ("Titulo: "+titulo+". Subtitulo: "+subtitulo+". Edad: "+edad);
        }
}

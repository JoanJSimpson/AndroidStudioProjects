package com.example.joan.clasetitulares;

/**
 * Created by Joan on 2/11/15.
 */
public class Titular {

        private String titulo, subtitulo;
        public Titular (String s, String s1){
            titulo = s;
            subtitulo = s1;
        }
        public String getTitulo (){
            return titulo;
        }
        public String getSubtitulo (){
           return subtitulo;
        }

        public String toString(){
            return ("Titulo: "+titulo+". Subtitulo: "+subtitulo);
        }
}

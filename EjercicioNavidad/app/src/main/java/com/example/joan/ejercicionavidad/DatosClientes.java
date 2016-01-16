package com.example.joan.ejercicionavidad;

/**
 * Created by Joan on 15/1/16.
 */
public class DatosClientes {

    private String nombre, apellidos, email, zona, tarifa, decoracion;
    private double peso, coste;
    private int id, telefono, imagen;

    public DatosClientes(int id, String nombre, String apellidos, String email, int telefono, String zona,
                         String tarifa, Double peso, String decoracion, Double coste, int imagen){
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.zona = zona;
        this.tarifa = tarifa;
        this.peso = peso;
        this.decoracion = decoracion;
        this.coste = coste;
        this.imagen = imagen;
    }


    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getZona(){
        return zona;
    }

    public String getEmail() {
        return email;
    }

    public String getTarifa() {
        return tarifa;
    }

    public String getDecoracion() {
        return decoracion;
    }

    public double getPeso() {
        return peso;
    }

    public double getCoste() {
        return coste;
    }

    public int getId() {
        return id;
    }

    public int getTelefono() {
        return telefono;
    }

    public int getImagen() {
        return imagen;
    }

    @Override
    public String toString() {
        return "DatosClientes{" +
                "Id =" + id + '\'' +
                ", Nombre='" + nombre + '\'' +
                ", Apellidos='" + apellidos + '\'' +
                ", Email='" + email + '\'' +
                ", telefono=" + telefono +
                ", Zona='" + zona + '\'' +
                ", Tarifa='" + tarifa + '\'' +
                ", Decoracion='" + decoracion + '\'' +
                ", Peso=" + peso +
                ", Coste=" + coste +
                '}';
    }


}

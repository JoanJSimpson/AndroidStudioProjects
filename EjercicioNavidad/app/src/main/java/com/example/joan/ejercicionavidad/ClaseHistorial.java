package com.example.joan.ejercicionavidad;

import java.io.Serializable;

/**
 * Created by Joan on 18/1/16.
 */
public class ClaseHistorial implements Serializable {


    private String usuarioDni, zonaId, tarifa, decoracion, nombre;
    private Double precio, peso;
    private int imagen, id;

    public ClaseHistorial(){

    }

    public ClaseHistorial(int id, String nombre, String usuarioDni, String zonaId, String tarifa, String decoracion, Double peso, Double precio, int imagen) {
        this.id = id;
        this.nombre = nombre;
        this.usuarioDni = usuarioDni;
        this.decoracion = decoracion;
        this.zonaId = zonaId;
        this.peso = peso;
        this.tarifa = tarifa;
        this.precio = precio;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuarioDni() {
        return usuarioDni;
    }

    public void setUsuarioDni(String usuarioDni) {
        this.usuarioDni = usuarioDni;
    }

    public String getDecoracion() {
        return decoracion;
    }

    public void setDecoracion(String decoracion) {
        this.decoracion = decoracion;
    }

    public String getZonaId() {
        return zonaId;
    }

    public void setZonaId(String zonaId) {
        this.zonaId = zonaId;
    }

    public String getTarifa() {
        return tarifa;
    }

    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "ClasePedido{" +
                "usuarioDni='" + usuarioDni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", zonaId='" + zonaId + '\'' +
                ", tarifa='" + tarifa + '\'' +
                ", decoracion='" + decoracion + '\'' +
                ", precio=" + precio +
                ", peso=" + peso +
                ", imagen=" + imagen +
                '}';
    }
}

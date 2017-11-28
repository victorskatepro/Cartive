package com.victorsaico.cartive.Models;

/**
 * Created by JARVIS on 24/11/2017.
 */

public class Viaje {


    private String destino;
    private String imagen;
    private String horapartida;
    private int numerotickets;
    private int numeroticketsdisponibles;
    private int numerohoras;
    private String tipo;
    private int precio;
    private int bus_id;
    private String fechapartida;
    private int id;
    private String nombre;

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getHorapartida() {
        return horapartida;
    }

    public void setHorapartida(String horapartida) {
        this.horapartida = horapartida;
    }

    public int getNumerotickets() {
        return numerotickets;
    }

    public void setNumerotickets(int numerotickets) {
        this.numerotickets = numerotickets;
    }

    public int getNumeroticketsdisponibles() {
        return numeroticketsdisponibles;
    }

    public void setNumeroticketsdisponibles(int numeroticketsdisponibles) {
        this.numeroticketsdisponibles = numeroticketsdisponibles;
    }

    public int getNumerohoras() {
        return numerohoras;
    }

    public void setNumerohoras(int numerohoras) {
        this.numerohoras = numerohoras;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getBus_id() {
        return bus_id;
    }

    public void setBus_id(int bus_id) {
        this.bus_id = bus_id;
    }

    public String getFechapartida() {
        return fechapartida;
    }

    public void setFechapartida(String fechapartida) {
        this.fechapartida = fechapartida;
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

    @Override
    public String toString() {
        return "Viaje{" +
                "destino='" + destino + '\'' +
                ", imagen='" + imagen + '\'' +
                ", horapartida='" + horapartida + '\'' +
                ", numerotickets=" + numerotickets +
                ", numeroticketsdisponibles=" + numeroticketsdisponibles +
                ", numerohoras=" + numerohoras +
                ", tipo='" + tipo + '\'' +
                ", precio=" + precio +
                ", bus_id=" + bus_id +
                ", fechapartida='" + fechapartida + '\'' +
                ", id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}

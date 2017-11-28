package com.victorsaico.cartive.Models;

/**
 * Created by JARVIS on 26/11/2017.
 */

public class Ticket {

    private String destino;
    private String horapartida;
    private String fechapartida;
    private int num_asiento;
    private String nombres;
    private String apellidos;
    private int dni;

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getHorapartida() {
        return horapartida;
    }

    public void setHorapartida(String horapartida) {
        this.horapartida = horapartida;
    }

    public String getFechapartida() {
        return fechapartida;
    }

    public void setFechapartida(String fechapartida) {
        this.fechapartida = fechapartida;
    }

    public int getNum_asiento() {
        return num_asiento;
    }

    public void setNum_asiento(int num_asiento) {
        this.num_asiento = num_asiento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "destino='" + destino + '\'' +
                ", horapartida='" + horapartida + '\'' +
                ", fechapartida='" + fechapartida + '\'' +
                ", num_asiento=" + num_asiento +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", dni=" + dni +
                '}';
    }

}

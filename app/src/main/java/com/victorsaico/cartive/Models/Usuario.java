package com.victorsaico.cartive.Models;

/**
 * Created by JARVIS on 22/11/2017.
 */

public class Usuario {

    private int id;
    private String username;
    private String nombres;
    private String apellidos;
    private String correo;
    private String imagen;
    private int dni;

    public Usuario(int id, String username, String nombres, String apellidos, String correo, String imagen, int dni) {
        this.id = id;
        this.username = username;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.imagen = imagen;
        this.dni = dni;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", correo='" + correo + '\'' +
                ", imagen='" + imagen + '\'' +
                ", dni=" + dni +
                '}';
    }
}


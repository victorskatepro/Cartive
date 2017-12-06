package com.victorsaico.cartive.Models;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by JARVIS on 26/11/2017.
 */

public class Asiento implements Serializable {


    private int id;
    private int num_asiento;
    private String estado;
    private int bus_id;
    public Bitmap image;
    public boolean isSelected;


    public Asiento(String estado, Bitmap image) {
        this.id = id;
        this.estado = estado;
        this.num_asiento = num_asiento;
        this.bus_id = bus_id;
        this.image = image;
        this.isSelected = isSelected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum_asiento() {
        return num_asiento;
    }

    public void setNum_asiento(int num_asiento) {
        this.num_asiento = num_asiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getBus_id() {
        return bus_id;
    }

    public void setBus_id(int bus_id) {
        this.bus_id = bus_id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }



    @Override
    public String toString() {
        return "Asiento{" +
                "id=" + id +
                ", num_asiento=" + num_asiento +
                ", estado='" + estado + '\'' +
                ", bus_id=" + bus_id +
                ", image=" + image +
                ", isSelected=" + isSelected +
                '}';
    }
}

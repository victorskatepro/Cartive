package com.victorsaico.cartive.Models;

import java.util.ArrayList;

/**
 * Created by JARVIS on 2/12/2017.
 */

public class CreateTicket {

    private String usuario_id;
    private String viaje_id;
    private ArrayList<Integer> num_asiento;


    public CreateTicket(String usuario_id, String viaje_id, ArrayList<Integer> num_asiento) {
        this.viaje_id = viaje_id;
        this.usuario_id = usuario_id;
        this.num_asiento = num_asiento;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getViaje_id() {
        return viaje_id;
    }

    public void setViaje_id(String viaje_id) {
        this.viaje_id = viaje_id;
    }

    public ArrayList<Integer> getNum_asiento() {
        return num_asiento;
    }

    public void setNum_asiento(ArrayList<Integer> num_asiento) {
        this.num_asiento = num_asiento;
    }

    @Override
    public String toString() {
        return "CreateTicket{" +
                "usuario_id=" + usuario_id +
                ", viaje_id=" + viaje_id +
                ", num_asiento=" + num_asiento +
                '}';
    }
}

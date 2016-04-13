package com.yamil.tourguide;

/**
 * Created by yamil on 4/5/16.
 */
public class ListBlueTooth {
    private String nombre;
    private String macAddress;
    private Boolean activo;
    private String estado = "Inactivo";

    public ListBlueTooth(String nombre, String macAddress, Boolean activo) {
        this.nombre = nombre;
        this.macAddress = macAddress;
        this.activo = activo;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

package com.angcar.model;

import lombok.Builder;


import java.sql.Date;

@Builder
public class Repositorio {
    private String uuid_repositorio;
    private String nombre;
    private Date fecha_creacion;
    private String proyecto;

    public Repositorio(String UUID_repositorio, String nombre, Date fecha_creacion, String proyecto) {
        this.uuid_repositorio = UUID_repositorio;
        this.nombre = nombre;
        this.fecha_creacion = fecha_creacion;
        this.proyecto = proyecto;
    }

    public String getUuid_repositorio() {
        return uuid_repositorio;
    }

    public void setUuid_repositorio(String uuid_repositorio) {
        this.uuid_repositorio = uuid_repositorio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

}


package com.angcar.model;

import lombok.Builder;

import java.sql.Date;

@Builder
public class Proyecto {
    private String uuid_proyecto;
    private String jefe_proyecto;
    private String nombre;
    private double presupuesto;
    private Date fecha_inicio;
    private Date fecha_fin;
    private String tecnologias_usadas;
    private String repositorio;
    private boolean es_acabado;
    private String uuid_departamento;

    public Proyecto(String uuid_proyecto, String jefe_proyecto, String nombre, double presupuesto,
                    Date fecha_inicio, Date fecha_fin, String tecnologias_usadas,
                    String repositorio, boolean es_acabado, String uuid_departamento) {
        this.uuid_proyecto = uuid_proyecto;
        this.jefe_proyecto = jefe_proyecto;
        this.nombre = nombre;
        this.presupuesto = presupuesto;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.tecnologias_usadas = tecnologias_usadas;
        this.repositorio = repositorio;
        this.es_acabado = es_acabado;
        this.uuid_departamento = uuid_departamento;
    }

    public String getUuid_proyecto() {
        return uuid_proyecto;
    }

    public void setUuid_proyecto(String uuid_proyecto) {
        this.uuid_proyecto = uuid_proyecto;
    }

    public String getJefe_proyecto() {
        return jefe_proyecto;
    }

    public void setJefe_proyecto(String jefe_proyecto) {
        this.jefe_proyecto = jefe_proyecto;
    }

    public String getUuid_departamento() {
        return uuid_departamento;
    }

    public void setUuid_departamento(String uuid_departamento) {this.uuid_departamento = uuid_departamento;}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getTecnologias_usadas() {
        return tecnologias_usadas;
    }

    public void setTecnologias_usadas(String tecnologias_usadas) {
        this.tecnologias_usadas = tecnologias_usadas;
    }

    public String getRepositorio() {
        return repositorio;
    }

    public void setRepositorio(String repositorio) {
        this.repositorio = repositorio;
    }

    public boolean isEs_acabado() {
        return es_acabado;
    }

    public void setEs_acabado(boolean es_acabado) {
        this.es_acabado = es_acabado;
    }
}

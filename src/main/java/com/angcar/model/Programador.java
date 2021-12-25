package com.angcar.model;

import lombok.Builder;

import java.sql.Date;

@Builder
public class Programador {
    private String uuid_programador;
    private String nombre;
    private Date fecha_alta;
    private String departamento;
    private String tecnologias_dominadas;
    private double salario;
    private boolean es_jefe_departamento;
    private boolean es_jefe_proyecto;
    private boolean es_jefe_activo;
    private String password;

    public Programador(String uuid_programador, String nombre, Date fecha_alta, String departamento,
                       String tecnologias_dominadas, double salario, boolean esJefeDepartamento, boolean esJefeProyecto,
                       boolean esJefeActivo, String password) {
        this.uuid_programador = uuid_programador;
        this.nombre = nombre;
        this.fecha_alta = fecha_alta;
        this.departamento = departamento;
        this.tecnologias_dominadas = tecnologias_dominadas;
        this.salario = salario;
        this.es_jefe_departamento = esJefeDepartamento;
        this.es_jefe_proyecto = esJefeProyecto;
        this.es_jefe_activo = esJefeActivo;
        this.password = password;
    }

    public String getUuid_programador() {
        return uuid_programador;
    }

    public void setUuid_programador(String uuid_programador) {
        this.uuid_programador = uuid_programador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(Date fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getTecnologias_dominadas() {
        return tecnologias_dominadas;
    }

    public void setTecnologias_dominadas(String tecnologias_dominadas) {
        this.tecnologias_dominadas = tecnologias_dominadas;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public boolean isEs_jefe_departamento() {
        return es_jefe_departamento;
    }

    public void setEs_jefe_departamento(boolean es_jefe_departamento) {
        this.es_jefe_departamento = es_jefe_departamento;
    }

    public boolean isEs_jefe_proyecto() {
        return es_jefe_proyecto;
    }

    public void setEs_jefe_proyecto(boolean es_jefe_proyecto) {
        this.es_jefe_proyecto = es_jefe_proyecto;
    }

    public boolean isEs_jefe_activo() {
        return es_jefe_activo;
    }

    public void setEs_jefe_activo(boolean es_jefe_activo) {
        this.es_jefe_activo = es_jefe_activo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

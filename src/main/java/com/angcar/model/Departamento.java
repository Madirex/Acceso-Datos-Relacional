package com.angcar.model;

import lombok.Builder;

@Builder
public class Departamento {
    private String uuid_departamento;
    private String nombre;
    private String jefe_departamento;
    private double presupuesto;
    private double presupuesto_anual;

    public Departamento(String uuid_departamento, String nombre, String jefe_departamento,
                        double presupuesto, double presupuesto_anual) {
        this.uuid_departamento = uuid_departamento;
        this.nombre = nombre;
        this.jefe_departamento = jefe_departamento;
        this.presupuesto = presupuesto;
        this.presupuesto_anual = presupuesto_anual;
    }

    public String getUuid_departamento() {
        return uuid_departamento;
    }

    public void setUuid_departamento(String uuid_departamento) {
        this.uuid_departamento = uuid_departamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getJefe_departamento() {
        return jefe_departamento;
    }

    public void setJefe_departamento(String jefe_departamento) {
        this.jefe_departamento = jefe_departamento;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public double getPresupuesto_anual() {
        return presupuesto_anual;
    }

    public void setPresupuesto_anual(double presupuesto_anual) {
        this.presupuesto_anual = presupuesto_anual;
    }
}


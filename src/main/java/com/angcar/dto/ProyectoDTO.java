package com.angcar.dto;

import com.angcar.model.Departamento;
import com.angcar.model.Ficha;
import com.angcar.model.Programador;
import com.angcar.model.Repositorio;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Builder;
import lombok.Data;

import  java.sql.Date;


@Data
@Builder
public class ProyectoDTO extends BaseDTO{
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

    //ADDS
    private Programador jefe_proyectoTo;
    private Departamento departamentoTo;
    private Repositorio repositorioTo;

    @Override
    public String toString() {
        return "ProyectoDTO{" +
                "uuid_proyecto='" + uuid_proyecto + '\'' +
                ", jefe_proyecto='" + jefe_proyecto + '\'' +
                ", nombre='" + nombre + '\'' +
                ", presupuesto=" + presupuesto +
                ", fecha_inicio=" + fecha_inicio +
                ", fecha_fin=" + fecha_fin +
                ", tecnologias_usadas='" + tecnologias_usadas + '\'' +
                ", repositorio='" + repositorio + '\'' +
                ", es_acabado=" + es_acabado +
                ", uuid_departamento='" + uuid_departamento + '\'' +
                ", jefe_proyectoTo=" + jefe_proyectoTo.getUuid_programador() +
                ", departamentoTo=" + departamentoTo.getUuid_departamento() +
                ", repositorioTo=" + repositorioTo.getUuid_repositorio() +
                '}';
    }
}

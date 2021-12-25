package com.angcar.dto;


import com.angcar.model.Departamento;
import com.angcar.model.Ficha;
import com.angcar.model.Tarea;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class ProgramadorDTO extends BaseDTO{
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

    //ADDS
    private Departamento departamentoTo;
    private Ficha fichaTo;
    private Tarea tareaTo;

    @Override
    public String toString() {
        return "ProgramadorDTO{" +
                "uuid_programador='" + uuid_programador + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fecha_alta=" + fecha_alta +
                ", departamento='" + departamento + '\'' +
                ", tecnologias_dominadas='" + tecnologias_dominadas + '\'' +
                ", salario=" + salario +
                ", es_jefe_departamento=" + es_jefe_departamento +
                ", es_jefe_proyecto=" + es_jefe_proyecto +
                ", es_jefe_activo=" + es_jefe_activo +
                ", password='" + password + '\'' +
                ", departamentoTo=" + departamentoTo.getUuid_departamento() +
                ", fichaTo=" + fichaTo.toString() +
                ", tareaTo=" + tareaTo.toString() +
                '}';
    }
}

package com.angcar.dto;

import com.angcar.model.Programador;
import com.angcar.model.Proyecto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class DepartamentoDTO extends BaseDTO{
    private String uuid_departamento;
    private String nombre;
    private String jefe_departamento;
    private double presupuesto;
    private double presupuesto_anual;

    //ADDS
    private Programador jefe_departamentoTo;

    @Override
    public String toString() {
        return "DepartamentoDTO{" +
                "uuid_departamento='" + uuid_departamento + '\'' +
                ", nombre='" + nombre + '\'' +
                ", presupuesto=" + presupuesto +
                ", presupuesto_anual=" + presupuesto_anual +
                ", jefe_departamento=" + jefe_departamentoTo.getUuid_programador() +
                '}';
    }
}

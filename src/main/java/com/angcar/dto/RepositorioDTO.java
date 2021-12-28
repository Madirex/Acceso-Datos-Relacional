package com.angcar.dto;

import com.angcar.model.Proyecto;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class RepositorioDTO extends BaseDTO{
    private String uuid_repositorio;
    private String nombre;
    private Date fecha_creacion;
    private String proyecto;

    //ADDS
    private Proyecto proyectoTo;

    @Override
    public String toString() {
        return "RepositorioDTO{" +
                "uuid_repositorio='" + uuid_repositorio + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fecha_creacion=" + fecha_creacion +
                ", proyectoTo=" + proyectoTo.getUuid_proyecto() +
                '}';
    }
}

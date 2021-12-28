package com.angcar.dto;

import com.angcar.model.Proyecto;
import com.angcar.model.Repositorio;
import com.angcar.model.Tarea;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class IssueDTO extends BaseDTO{
    private String uuid_issue;
    private String titulo;
    private String texto;
    private Date fecha;
    private String proyecto;
    private String repositorio_asignado;
    private boolean es_acabado;

    //ADDS
    private Proyecto proyectoTo;
    private Repositorio repositorioTo;

    @Override
    public String toString() {
        return "IssueDTO{" +
                "uuid_issue='" + uuid_issue + '\'' +
                ", titulo='" + titulo + '\'' +
                ", texto='" + texto + '\'' +
                ", fecha=" + fecha +
                ", es_acabado=" + es_acabado +
                ", proyectoTo=" + proyectoTo.getUuid_proyecto() +
                ", repositorioTo=" + repositorioTo.getUuid_repositorio() +
                '}';
    }
}

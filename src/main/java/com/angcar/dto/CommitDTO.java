package com.angcar.dto;

import com.angcar.model.Issue;
import com.angcar.model.Programador;
import com.angcar.model.Proyecto;
import com.angcar.model.Repositorio;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CommitDTO extends BaseDTO{
    private String uuid_commit;
    private String titulo;
    private String texto;
    private Date fecha;
    private String repositorio;
    private String proyecto;
    private String autor_commit;
    private String issue_procedente;

    //ADDS
    private Repositorio repositorioTo;
    private Proyecto proyectoTo;
    private Programador autor_commitTo;
    private Issue issue_procedenteTo;

    @Override
    public String toString() {
        return "CommitDTO{" +
                "uuid_commit='" + uuid_commit + '\'' +
                ", titulo='" + titulo + '\'' +
                ", texto='" + texto + '\'' +
                ", fecha=" + fecha +
                ", repositorio=" + repositorioTo.getUuid_repositorio() +
                ", proyecto=" + proyectoTo.getUuid_proyecto() +
                ", autor_commit=" + autor_commitTo.getUuid_programador() +
                ", issue_procedente=" + issue_procedenteTo.getUuid_issue() +
                '}';
    }
}

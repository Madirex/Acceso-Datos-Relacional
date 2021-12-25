package com.angcar.dto;

import com.angcar.model.Departamento;
import com.angcar.model.Programador;
import com.angcar.model.Proyecto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class HistoricoJefesDTO extends BaseDTO{
    private String uuid_historico;
    private String uuid_programador;
    private String uuid_departamento;
    private Date fecha_alta;
    private Date fecha_baja;

    //ADDS
    private Programador programadorTo;
    private Departamento departamentoTo;

    @Override
    public String toString() {
        return "HistoricoJefesDTO{" +
                "uuid_historico='" + uuid_historico + '\'' +
                ", uuid_programador='" + uuid_programador + '\'' +
                ", uuid_departamento='" + uuid_departamento + '\'' +
                ", fecha_alta=" + fecha_alta +
                ", fecha_baja=" + fecha_baja +
                '}';
    }
}

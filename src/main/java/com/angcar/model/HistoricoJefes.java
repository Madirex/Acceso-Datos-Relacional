package com.angcar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Builder
@AllArgsConstructor
@Data
public class HistoricoJefes {
    private String uuid_historico;
    private String uuid_programador;
    private String uuid_departamento;
    private Date fecha_alta;
    private Date fecha_baja;
}


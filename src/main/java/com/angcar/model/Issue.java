package com.angcar.model;

import lombok.Builder;

import java.sql.Date;

@Builder
public class Issue {
    private String uuid_issue;
    private String titulo;
    private String texto;
    private Date fecha;
    private String proyecto;
    private String repositorio_asignado;
    private boolean es_acabado;

    public Issue(String uuid_issue, String titulo, String texto, Date fecha,
                 String proyecto, String repositorio_asignado, boolean esAcabado) {
        this.uuid_issue = uuid_issue;
        this.titulo = titulo;
        this.texto = texto;
        this.fecha = fecha;
        this.proyecto = proyecto;
        this.repositorio_asignado = repositorio_asignado;
        this.es_acabado = esAcabado;
    }

    public String getUuid_issue() {
        return uuid_issue;
    }

    public void setUuid_issue(String uuid_issue) {
        this.uuid_issue = uuid_issue;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getRepositorio_asignado() {
        return repositorio_asignado;
    }

    public void setRepositorio_asignado(String repositorio_asignado) {
        this.repositorio_asignado = repositorio_asignado;
    }

    public boolean isEsAcabado() {
        return es_acabado;
    }

    public void setEsAcabado(boolean esAcabado) {
        this.es_acabado = esAcabado;
    }
}


package com.angcar.model;

import lombok.Builder;

import java.util.Date;

@Builder
public class Commit {
    private String uuid_commit;
    private String titulo;
    private String texto;
    private Date fecha;
    private String repositorio;
    private String proyecto;
    private String autor_commit;
    private String issue_procedente;

    public Commit(String uuid_commit, String titulo, String texto, Date fecha, String repositorio, String proyecto,
                  String autor_commit, String issue_procedente) {
        this.uuid_commit = uuid_commit;
        this.titulo = titulo;
        this.texto = texto;
        this.fecha = fecha;
        this.repositorio = repositorio;
        this.proyecto = proyecto;
        this.autor_commit = autor_commit;
        this.issue_procedente = issue_procedente;
    }

    public String getUuid_commit() {
        return uuid_commit;
    }

    public void setUuid_commit(String uuid_commit) {
        this.uuid_commit = uuid_commit;
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

    public String getRepositorio() {
        return repositorio;
    }

    public void setRepositorio(String repositorio) {
        this.repositorio = repositorio;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getAutor_commit() {
        return autor_commit;
    }

    public void setAutor_commit(String autor_commit) {
        this.autor_commit = autor_commit;
    }

    public String getIssue_procedente() {
        return issue_procedente;
    }

    public void setIssue_procedente(String issue_procedente) {
        this.issue_procedente = issue_procedente;
    }
}


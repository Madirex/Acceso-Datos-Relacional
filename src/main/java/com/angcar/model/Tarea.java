package com.angcar.model;

import lombok.Builder;

@Builder
public class Tarea {
    private String uuid_tarea;
    private String uuid_programador;
    private String uuid_issue;

    public Tarea(String UUID_tarea, String UUID_programador, String UUID_issue) {
        this.uuid_tarea = UUID_tarea;
        this.uuid_programador = UUID_programador;
        this.uuid_issue = UUID_issue;
    }

    public String getUuid_tarea() {
        return uuid_tarea;
    }

    public void setUuid_tarea(String uuid_tarea) {
        this.uuid_tarea = uuid_tarea;
    }

    public String getUuid_programador() {
        return uuid_programador;
    }

    public void setUuid_programador(String uuid_programador) {
        this.uuid_programador = uuid_programador;
    }

    public String getUuid_issue() {
        return uuid_issue;
    }

    public void setUuid_issue(String uuid_issue) {
        this.uuid_issue = uuid_issue;
    }
}


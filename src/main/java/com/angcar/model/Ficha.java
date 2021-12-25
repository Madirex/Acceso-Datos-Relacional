package com.angcar.model;

import lombok.Builder;

@Builder
public class Ficha {
    private String uuid_ficha;
    private String uuid_programador;
    private String uuid_proyecto;

    public Ficha(String UUID_ficha, String UUID_programador, String UUID_proyecto) {
        this.uuid_ficha = UUID_ficha;
        this.uuid_programador = UUID_programador;
        this.uuid_proyecto = UUID_proyecto;
    }

    public String getUuid_ficha() {
        return uuid_ficha;
    }

    public void setUuid_ficha(String uuid_ficha) {
        this.uuid_ficha = uuid_ficha;
    }

    public String getUuid_programador() {
        return uuid_programador;
    }

    public void setUuid_programador(String uuid_programador) {
        this.uuid_programador = uuid_programador;
    }

    public String getUuid_proyecto() {
        return uuid_proyecto;
    }

    public void setUuid_proyecto(String uuid_proyecto) {
        this.uuid_proyecto = uuid_proyecto;
    }
}


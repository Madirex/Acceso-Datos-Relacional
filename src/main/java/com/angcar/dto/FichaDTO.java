package com.angcar.dto;

import com.angcar.model.Programador;
import com.angcar.model.Proyecto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FichaDTO extends BaseDTO{
    private String uuid_ficha;
    private String uuid_programador;
    private String uuid_proyecto;

    //ADDS
    private Programador programadorTo;
    private Proyecto proyectoTo;

    @Override
    public String toString() {
        return "FichaDTO{" +
                "uuid_ficha='" + uuid_ficha + '\'' +
                ", programadorTo=" + programadorTo.getUuid_programador() +
                ", proyectoTo=" + proyectoTo.getUuid_proyecto() +
                '}';
    }
}

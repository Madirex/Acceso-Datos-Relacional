package com.angcar.mapper;

import com.angcar.dto.RepositorioDTO;
import com.angcar.model.Repositorio;

public class RepositorioMapper extends BaseMapper<Repositorio, RepositorioDTO> {
    @Override
    public Repositorio fromDTO(RepositorioDTO item) {
        return Repositorio.builder()
                .uuid_repositorio(item.getUuid_repositorio())
                .nombre(item.getNombre())
                .fecha_creacion(item.getFecha_creacion())
                .proyecto(item.getProyecto())
                .build();
    }

    @Override
    public RepositorioDTO toDTO(Repositorio item) {
        return RepositorioDTO.builder()
                .uuid_repositorio(item.getUuid_repositorio())
                .nombre(item.getNombre())
                .fecha_creacion(item.getFecha_creacion())
                .proyecto(item.getProyecto())
                .build();
    }
}


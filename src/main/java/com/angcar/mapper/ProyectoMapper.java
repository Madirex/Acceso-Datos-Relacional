package com.angcar.mapper;

import com.angcar.dto.ProyectoDTO;
import com.angcar.model.Proyecto;

public class ProyectoMapper extends BaseMapper<Proyecto, ProyectoDTO>{
    @Override
    public Proyecto fromDTO(ProyectoDTO item) {
        return Proyecto.builder()
                .uuid_proyecto(item.getUuid_proyecto())
                .jefe_proyecto(item.getJefe_proyecto())
                .nombre(item.getNombre())
                .presupuesto(item.getPresupuesto())
                .fecha_inicio(item.getFecha_inicio())
                .fecha_fin(item.getFecha_fin())
                .tecnologias_usadas(item.getTecnologias_usadas())
                .repositorio(item.getRepositorio())
                .es_acabado(item.isEs_acabado())
                .uuid_departamento(item.getUuid_departamento())
                .build();
    }

    @Override
    public ProyectoDTO toDTO(Proyecto item) {
        return ProyectoDTO.builder()
                .uuid_proyecto(item.getUuid_proyecto())
                .jefe_proyecto(item.getJefe_proyecto())
                .nombre(item.getNombre())
                .presupuesto(item.getPresupuesto())
                .fecha_inicio(item.getFecha_inicio())
                .fecha_fin(item.getFecha_fin())
                .tecnologias_usadas(item.getTecnologias_usadas())
                .repositorio(item.getRepositorio())
                .es_acabado(item.isEs_acabado())
                .uuid_departamento(item.getUuid_departamento())
                .build();
    }
}
package com.angcar.mapper;

import com.angcar.dto.ProgramadorDTO;
import com.angcar.model.Programador;

public class ProgramadorMapper extends BaseMapper<Programador, ProgramadorDTO>{
    @Override
    public Programador fromDTO(ProgramadorDTO item) {
        return Programador.builder()
                .uuid_programador(item.getUuid_programador())
                .nombre(item.getNombre())
                .fecha_alta(item.getFecha_alta())
                .departamento(item.getDepartamento())
                .tecnologias_dominadas(item.getTecnologias_dominadas())
                .salario(item.getSalario())
                .es_jefe_departamento(item.isEs_jefe_departamento())
                .es_jefe_proyecto(item.isEs_jefe_proyecto())
                .es_jefe_activo(item.isEs_jefe_activo())
                .password(item.getPassword())
                .build();
    }

    @Override
    public ProgramadorDTO toDTO(Programador item) {
        return ProgramadorDTO.builder()
                .uuid_programador(item.getUuid_programador())
                .nombre(item.getNombre())
                .fecha_alta(item.getFecha_alta())
                .departamento(item.getDepartamento())
                .salario(item.getSalario())
                .es_jefe_departamento(item.isEs_jefe_departamento())
                .es_jefe_proyecto(item.isEs_jefe_proyecto())
                .es_jefe_activo(item.isEs_jefe_activo())
                .password(item.getPassword())
                .build();
    }
}

package com.angcar.mapper;

import com.angcar.dto.DepartamentoDTO;
import com.angcar.model.Departamento;

public class DepartamentoMapper extends BaseMapper<Departamento, DepartamentoDTO>{
    @Override
    public Departamento fromDTO(DepartamentoDTO item) {
        return Departamento.builder()
                .uuid_departamento(item.getUuid_departamento())
                .nombre(item.getNombre())
                .jefe_departamento(item.getJefe_departamento())
                .presupuesto(item.getPresupuesto())
                .presupuesto_anual(item.getPresupuesto_anual())
                .build();
    }

    @Override
    public DepartamentoDTO toDTO(Departamento item) {
        return DepartamentoDTO.builder()
                .uuid_departamento(item.getUuid_departamento())
                .nombre(item.getNombre())
                .jefe_departamento(item.getJefe_departamento())
                .presupuesto(item.getPresupuesto())
                .presupuesto_anual(item.getPresupuesto_anual())
                .build();
    }
}


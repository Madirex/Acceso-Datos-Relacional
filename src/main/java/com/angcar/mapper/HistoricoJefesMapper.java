package com.angcar.mapper;

import com.angcar.dto.HistoricoJefesDTO;
import com.angcar.model.HistoricoJefes;

public class HistoricoJefesMapper extends BaseMapper<HistoricoJefes, HistoricoJefesDTO>{
    @Override
    public HistoricoJefes fromDTO(HistoricoJefesDTO item) {
        return HistoricoJefes.builder()
                .uuid_historico(item.getUuid_historico())
                .uuid_programador(item.getUuid_programador())
                .uuid_departamento(item.getUuid_departamento())
                .fecha_alta(item.getFecha_alta())
                .fecha_baja(item.getFecha_baja())
                .build();
    }

    @Override
    public HistoricoJefesDTO toDTO(HistoricoJefes item) {
        return HistoricoJefesDTO.builder()
                .uuid_historico(item.getUuid_historico())
                .uuid_programador(item.getUuid_programador())
                .uuid_departamento(item.getUuid_departamento())
                .fecha_alta(item.getFecha_alta())
                .fecha_baja(item.getFecha_baja())
                .build();
    }
}


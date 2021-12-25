package com.angcar.mapper;

import com.angcar.dto.FichaDTO;
import com.angcar.model.Ficha;

public class FichaMapper extends BaseMapper<Ficha, FichaDTO>{
    @Override
    public Ficha fromDTO(FichaDTO item) {
        return Ficha.builder()
                .uuid_ficha(item.getUuid_ficha())
                .uuid_programador(item.getUuid_programador())
                .uuid_proyecto(item.getUuid_proyecto())
                .build();
    }

    @Override
    public FichaDTO toDTO(Ficha item) {
        return FichaDTO.builder()
                .uuid_ficha(item.getUuid_ficha())
                .uuid_programador(item.getUuid_programador())
                .uuid_proyecto(item.getUuid_proyecto())
                .build();
    }
}

